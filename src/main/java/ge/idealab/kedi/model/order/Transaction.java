package ge.idealab.kedi.model.order;

import ge.idealab.kedi.model.BaseStatusAuditEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseStatusAuditEntity {
    @Column
    private String uuid;
    @ManyToOne
    @JoinColumn(name="order_id", nullable = false)
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
