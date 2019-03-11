package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.dto.OrderDTO;
import ge.idealab.kedi.dto.ProductDTO;
import ge.idealab.kedi.model.CARTU.PaymentRequest;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.order.Order;
import ge.idealab.kedi.model.order.Transaction;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.payload.request.InitPaymentRequest;
import ge.idealab.kedi.repository.OrderRepository;
import ge.idealab.kedi.repository.TransactionRepository;
import ge.idealab.kedi.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Order placeOrder(OrderDTO orderDTO) {
        Order order = new Order();
        BigDecimal subTotal = BigDecimal.ZERO;
        String uuid = UUID.randomUUID().toString();
        for (ProductDTO p : orderDTO.getProducts()) {
            subTotal.add(p.getPrice());
        }
        order.setSubTotal(subTotal);
        order.setUuid(uuid);
        order.setProducts(this.mapProductDTOs(orderDTO.getProducts()));
        order.setStatus(Status.ORDERED);
        order = orderRepository.save(order);
        return order;
    }

    @Override
    public PaymentRequest initPayment(InitPaymentRequest initPaymentRequest) {
        Order order = orderRepository.getOne(initPaymentRequest.getId());
        PaymentRequest paymentRequest = new PaymentRequest(
                order.getUuid(),
                order.getSubTotal(),
                268L,
                981L,
                "Kedi!www.kedi.ge",
                "https://kedi.ge",
                "Tbilisi",
                "000000008001387-00000001"
        );
        Transaction transaction = new Transaction();
        String uuid = UUID.randomUUID().toString();
        transaction.setOrder(order);
        transaction.setStatus(Status.ORDERED);
        transaction.setUuid(uuid);
        transaction = transactionRepository.save(transaction);
        order.setTransactions(Collections.singleton(transaction));
        orderRepository.save(order);
        return paymentRequest;
    }

    Set<Product> mapProductDTOs(Set<ProductDTO> productDTOS) {
        ModelMapper modelMapper = new ModelMapper();
        Set<Product> products = new HashSet<>();
        for (ProductDTO p : productDTOS) {
            products.add(modelMapper.map(p, Product.class));
        }
        return products;
    }
}
