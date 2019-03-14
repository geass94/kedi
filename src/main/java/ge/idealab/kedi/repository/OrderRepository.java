package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUuidAndStatus(String uuid, Status status);
    Order findByUuidAndStatusIn(String uuid, Set<Status> statuses);
}
