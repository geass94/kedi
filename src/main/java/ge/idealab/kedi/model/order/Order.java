package ge.idealab.kedi.model.order;

import ge.idealab.kedi.model.BaseStatusAuditEntity;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.user.Address;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseStatusAuditEntity {
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "order_products",
            joinColumns = { @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name="fk_ordprd_order_id")) },
            inverseJoinColumns = { @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name="fk_ordprd_product_id")) })
    private Set<Product> products = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "order_transactions",
            joinColumns = { @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name="fk_ordtrnsct_order_id")) },
            inverseJoinColumns = { @JoinColumn(name = "transaction_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name="fk_ordtrnsct_transaction_id")) })
    private Set<Transaction> transactions = new HashSet<>();
    @Column
    private Address shippingAddress;
    @Column
    private Address billingAddress;
    @Column
    private String paymentMethod;
    @Column
    private String shippingMethod;

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
