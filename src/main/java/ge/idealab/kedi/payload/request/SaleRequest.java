package ge.idealab.kedi.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import ge.idealab.kedi.dto.ProductDTO;

import java.util.Date;
import java.util.List;

public class SaleRequest {
    private List<ProductDTO> products;
    private Float sale;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date countDown;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public Float getSale() {
        return sale;
    }

    public void setSale(Float sale) {
        this.sale = sale;
    }

    public Date getCountDown() {
        return countDown;
    }

    public void setCountDown(Date countDown) {
        this.countDown = countDown;
    }
}
