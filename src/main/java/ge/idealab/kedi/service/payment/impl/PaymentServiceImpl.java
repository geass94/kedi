package ge.idealab.kedi.service.payment.impl;

import ge.idealab.kedi.model.CARTU.ConfirmRequest;
import ge.idealab.kedi.model.CARTU.ConfirmResponse;
import ge.idealab.kedi.model.CARTU.PaymentRequest;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.order.Order;
import ge.idealab.kedi.model.order.Transaction;
import ge.idealab.kedi.repository.OrderRepository;
import ge.idealab.kedi.repository.TransactionRepository;
import ge.idealab.kedi.service.OrderService;
import ge.idealab.kedi.service.TransactionService;
import ge.idealab.kedi.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private OrderService orderService;


    @Override
    public ConfirmResponse checkPayment(ConfirmRequest confirmRequest) {
        switch (confirmRequest.getStatus()){
            case "C":
                return this.check(confirmRequest);
            case "Y":
                return this.approve(confirmRequest);
            case "N":
                return this.fail(confirmRequest);
            case "U":
                return this.decline(confirmRequest);
            default:
                return this.fail(confirmRequest);
        }
    }

    ConfirmResponse check(ConfirmRequest confirmRequest) {
        ConfirmResponse confirmResponse = new ConfirmResponse(confirmRequest.getTransactionId(), confirmRequest.getPaymentId(), "DECLINED");
        Set<Status> statuses = new HashSet<>();
        statuses.add(Status.ORDERED);
        statuses.add(Status.FAILED);
        Order order = orderRepository.findByUuidAndStatusIn(confirmRequest.getTransactionId(), statuses);
        if (order != null) {
            Set<Transaction> transactions = transactionRepository.findAllByOrderAndStatusIn(order, statuses);

            BigDecimal total = this.transactionsTotal(transactions);

            if (total.compareTo( confirmRequest.getAmount().divide(new BigDecimal(100)).setScale(2)) == 0) {
                order.setStatus(Status.VALIDATING);
                orderRepository.save(order);

                for (Transaction t : transactions) {
                    t.setStatus(Status.VALIDATING);
                    t.setPaymentId(confirmRequest.getPaymentId());
                    t.setTransactionId(order.getUuid());
                    transactionRepository.save(t);
                }
                confirmResponse.setStatus("ACCEPTED");
            }
        }

        return confirmResponse;
    }

    ConfirmResponse approve(ConfirmRequest confirmRequest) {
        ConfirmResponse confirmResponse = new ConfirmResponse(confirmRequest.getTransactionId(), confirmRequest.getPaymentId(), "DECLINED");

        Order order = orderRepository.findByUuidAndStatus(confirmRequest.getTransactionId(), Status.VALIDATING);
        if (order != null) {
            Set<Transaction> transactions = transactionRepository.findAllByOrderAndStatus(order, Status.VALIDATING);

            order.setStatus(Status.PAID);
            orderRepository.save(order);
            orderService.finalizeOrder(order);
            for (Transaction transaction : transactions) {
                Transaction t = new Transaction();
                t.setOrder(order);
                t.setAmount(transaction.getAmount());
                t.setStatus(Status.PAID);
                String uuid = UUID.randomUUID().toString();
                t.setUuid(uuid);
                t.setTransactionId(order.getUuid());
                t.setPaymentDate(confirmRequest.getPaymentDate());
                t.setPaymentId(confirmRequest.getPaymentId());
                transactionRepository.save(t);
            }
            confirmResponse.setStatus("ACCEPTED");
        }
        return confirmResponse;
    }

    ConfirmResponse decline(ConfirmRequest confirmRequest) {
        ConfirmResponse confirmResponse = new ConfirmResponse(confirmRequest.getTransactionId(), confirmRequest.getPaymentId(), "DECLINED");

        Order order = orderRepository.findByUuidAndStatus(confirmRequest.getTransactionId(), Status.VALIDATING);
        if (order != null) {
            Set<Transaction> transactions = transactionRepository.findAllByOrderAndStatus(order, Status.VALIDATING);

            order.setStatus(Status.DECLINED);
            orderRepository.save(order);

            for (Transaction transaction : transactions) {
                String uuid = UUID.randomUUID().toString();
                Transaction t = new Transaction();
                t.setOrder(order);
                t.setAmount(transaction.getAmount());
                t.setStatus(Status.DECLINED);
                t.setUuid(uuid);
                t.setTransactionId(order.getUuid());
                t.setPaymentId(confirmRequest.getPaymentId());
                t.setPaymentDate(confirmRequest.getPaymentDate());
                transactionRepository.save(t);
            }
        }
        return confirmResponse;
    }

    ConfirmResponse fail(ConfirmRequest confirmRequest) {
        ConfirmResponse confirmResponse = new ConfirmResponse(confirmRequest.getTransactionId(), confirmRequest.getPaymentId(), "DECLINED");

        Order order = orderRepository.findByUuidAndStatus(confirmRequest.getTransactionId(), Status.VALIDATING);
        if (order != null) {
            Set<Transaction> transactions = transactionRepository.findAllByOrderAndStatus(order, Status.VALIDATING);

            order.setStatus(Status.FAILED);
            orderRepository.save(order);

            for(Transaction t : transactions) {
                t.setStatus(Status.FAILED);
                transactionRepository.save(t);
            }
        }
        return confirmResponse;
    }

    private BigDecimal transactionsTotal(Set<Transaction> transactions) {
        BigDecimal total = new BigDecimal(0);
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() != null) {
                total = total.add(transaction.getAmount());
            }
        }
        return total;
    }
}
