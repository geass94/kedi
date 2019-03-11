package ge.idealab.kedi.service.payment.impl;

import ge.idealab.kedi.model.CARTU.PaymentRequest;
import ge.idealab.kedi.service.OrderService;
import ge.idealab.kedi.service.TransactionService;
import ge.idealab.kedi.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private TransactionService transactionService;

    @Override
    public PaymentRequest initatePayment(Long orderId) {
        return null;
    }
}
