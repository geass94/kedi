package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.bag.Cart;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByProductAndUserAndStatus(Product product, User user, Status status);
    List<Cart> findAllByUserAndStatus(User user, Status status);

    List<Cart> findAllByUserAndProductIsInAndSavedForLaterIsFalseAndWishlistIsFalseAndStatus(User user, List<Product> products, Status status);
}
