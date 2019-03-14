package ge.idealab.kedi.service.payment;

import ge.idealab.kedi.model.CARTU.ConfirmRequest;
import ge.idealab.kedi.model.CARTU.ConfirmResponse;
import ge.idealab.kedi.model.CARTU.PaymentRequest;

public interface PaymentService {
    ConfirmResponse checkPayment(ConfirmRequest confirmRequest);
}
