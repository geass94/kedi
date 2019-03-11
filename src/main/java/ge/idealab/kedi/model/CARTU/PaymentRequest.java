package ge.idealab.kedi.model.CARTU;

import java.math.BigDecimal;

public class PaymentRequest {
    private String PurchaseDesc;
    private BigDecimal PurchaseAmt;
    private Long CountryCode;
    private Long CurrencyCode;
    private String MerchantName;
    private String MerchantURL;
    private String MerchantCity;
    private Long MerchantID;

    public PaymentRequest() {
    }

    public PaymentRequest(String purchaseDesc, BigDecimal purchaseAmt, Long countryCode, Long currencyCode, String merchantName, String merchantURL, String merchantCity, Long merchantID) {
        PurchaseDesc = purchaseDesc;
        PurchaseAmt = purchaseAmt;
        CountryCode = countryCode;
        CurrencyCode = currencyCode;
        MerchantName = merchantName;
        MerchantURL = merchantURL;
        MerchantCity = merchantCity;
        MerchantID = merchantID;
    }

    public String getPurchaseDesc() {
        return PurchaseDesc;
    }

    public void setPurchaseDesc(String purchaseDesc) {
        PurchaseDesc = purchaseDesc;
    }

    public BigDecimal getPurchaseAmt() {
        return PurchaseAmt;
    }

    public void setPurchaseAmt(BigDecimal purchaseAmt) {
        PurchaseAmt = purchaseAmt;
    }

    public Long getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(Long countryCode) {
        CountryCode = countryCode;
    }

    public Long getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(Long currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getMerchantName() {
        return MerchantName;
    }

    public void setMerchantName(String merchantName) {
        MerchantName = merchantName;
    }

    public String getMerchantURL() {
        return MerchantURL;
    }

    public void setMerchantURL(String merchantURL) {
        MerchantURL = merchantURL;
    }

    public String getMerchantCity() {
        return MerchantCity;
    }

    public void setMerchantCity(String merchantCity) {
        MerchantCity = merchantCity;
    }

    public Long getMerchantID() {
        return MerchantID;
    }

    public void setMerchantID(Long merchantID) {
        MerchantID = merchantID;
    }
}
