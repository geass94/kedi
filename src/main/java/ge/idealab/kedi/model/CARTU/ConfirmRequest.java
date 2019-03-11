package ge.idealab.kedi.model.CARTU;

import java.math.BigDecimal;
import java.util.Date;

public class ConfirmRequest {
    private String TransactionId;
    private String PaymentId;
    private Date PaymentDate;
    private BigDecimal Amount;
    private String Status;
    private String Reason;
}
