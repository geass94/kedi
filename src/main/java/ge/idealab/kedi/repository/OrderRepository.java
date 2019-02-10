package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
