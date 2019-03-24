package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.OrderDTO;
import ge.idealab.kedi.model.CARTU.PaymentRequest;
import ge.idealab.kedi.model.order.Order;
import ge.idealab.kedi.payload.request.InitPaymentRequest;

import java.util.List;

public interface OrderService {
    Order placeOrder(OrderDTO orderDTO);
    void approveOrder(Order order);
    PaymentRequest initPayment(InitPaymentRequest initPaymentRequest);

    List<Order> getOrderHistory();
}
