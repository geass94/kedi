package ge.idealab.kedi.model.CARTU;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfirmResponse {
    @JacksonXmlProperty(localName = "TransactionId")
    private String TransactionId;
    @JacksonXmlProperty(localName = "PaymentId")
    private String PaymentId;
    @JacksonXmlProperty(localName = "Status")
    private String Status;

    public ConfirmResponse() {
    }

    public ConfirmResponse(String transactionId, String paymentId, String status) {
        TransactionId = transactionId;
        PaymentId = paymentId;
        Status = status;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(String paymentId) {
        PaymentId = paymentId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
