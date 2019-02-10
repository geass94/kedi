package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.order.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
