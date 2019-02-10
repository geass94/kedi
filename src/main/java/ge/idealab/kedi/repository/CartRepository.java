package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.Cart;
import ge.idealab.kedi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByUser(User user);
}
