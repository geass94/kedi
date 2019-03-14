package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.order.Order;
import ge.idealab.kedi.model.order.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Set<Transaction> findAllByOrderAndStatus(Order order, Status status);
    Set<Transaction> findAllByOrderAndStatusIn(Order order, Set<Status> statuses);
}
