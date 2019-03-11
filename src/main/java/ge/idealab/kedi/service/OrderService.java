package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.OrderDTO;
import ge.idealab.kedi.model.CARTU.PaymentRequest;
import ge.idealab.kedi.model.order.Order;
import ge.idealab.kedi.payload.request.InitPaymentRequest;

public interface OrderService {
    Order placeOrder(OrderDTO orderDTO);
    PaymentRequest initPayment(InitPaymentRequest initPaymentRequest);
}
