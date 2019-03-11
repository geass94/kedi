package ge.idealab.kedi.service.payment;

import ge.idealab.kedi.model.CARTU.PaymentRequest;

public interface PaymentService {
    PaymentRequest initatePayment(Long orderId);
}
