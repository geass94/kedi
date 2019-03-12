package ge.idealab.kedi.model.CARTU;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfirmRequest {
    @JacksonXmlProperty(localName = "TransactionId")
    private String TransactionId;
    @JacksonXmlProperty(localName = "PaymentId")
    private String PaymentId;
    @JacksonXmlProperty(localName = "PaymentDate")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date PaymentDate;
    @JacksonXmlProperty(localName = "Amount")
    private BigDecimal Amount;
    @JacksonXmlProperty(localName = "Status")
    private String Status;
    @JacksonXmlProperty(localName = "Reason")
    private String Reason;
    @JacksonXmlProperty(localName = "CardType")
    private String CardType;

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

    public Date getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        PaymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal amount) {
        Amount = amount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }
}
