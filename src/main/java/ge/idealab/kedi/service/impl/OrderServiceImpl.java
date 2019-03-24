package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.dto.OrderDTO;
import ge.idealab.kedi.dto.ProductDTO;
import ge.idealab.kedi.model.CARTU.PaymentRequest;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.order.Order;
import ge.idealab.kedi.model.order.Transaction;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.user.User;
import ge.idealab.kedi.payload.request.InitPaymentRequest;
import ge.idealab.kedi.repository.OrderRepository;
import ge.idealab.kedi.repository.TransactionRepository;
import ge.idealab.kedi.service.CartService;
import ge.idealab.kedi.service.OrderService;
import ge.idealab.kedi.service.ProductService;
import ge.idealab.kedi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @Override
    public Order placeOrder(OrderDTO orderDTO) {
        Order order = new Order();
        BigDecimal subTotal = BigDecimal.ZERO;
        String uuid = UUID.randomUUID().toString();

        for (ProductDTO p : orderDTO.getProducts()) {
            if (orderDTO.getProducts().containsAll( p.getBundle().getProducts() )) {
                subTotal = subTotal.add( p.getBundle().getPrice() );
            } else {
                subTotal = subTotal.add(p.getPrice());
            }
        }
        order.setSubTotal(subTotal.setScale(2));
        order.setUuid(uuid);
        order.setStatus(Status.ORDERED);
        order.setProducts(this.mapProductDTOs(orderDTO.getProducts()));
        order.setUser(userService.getUserFromContext());
        order = orderRepository.save(order);

        Transaction transaction = new Transaction();
        String uuid2 = UUID.randomUUID().toString();
        transaction.setOrder(order);
        transaction.setStatus(Status.ORDERED);
        transaction.setUuid(uuid2);
        transaction.setAmount(order.getSubTotal());
        transaction.setTransactionId(order.getUuid());
        transaction = transactionRepository.save(transaction);

        order.setTransactions(Collections.singleton(transaction));
        order = orderRepository.save(order);
        return order;
    }

    @Override
    public void approveOrder(Order order) {
        Order o = orderRepository.getOne(order.getId());
        cartService.clearShoppingCartByProducts(o.getProducts());
    }

    @Override
    public PaymentRequest initPayment(InitPaymentRequest initPaymentRequest) {
        Order order = orderRepository.getOne(initPaymentRequest.getId());
        PaymentRequest paymentRequest = new PaymentRequest(
                order.getUuid(),
                order.getSubTotal(),
                268L,
                981L,
                "Kedi!www.kedi.ge",
                "https://kedi.ge",
                "Tbilisi",
                "000000008001387-00000001"
        );
        return paymentRequest;
    }

    @Override
    public List<Order> getOrderHistory() {
        Set<Status> statuses = new HashSet<>();
        statuses.add(Status.ACTIVE); statuses.add(Status.ORDERED); statuses.add(Status.DECLINED); statuses.add(Status.PAID);
        User user = userService.getUserFromContext();
        return orderRepository.findAllByUserAndStatusIsInOrderByStatusDesc(user, statuses);
    }

    List<Product> mapProductDTOs(List<ProductDTO> productDTOS) {
        List<Product> products = new ArrayList<>();
        for (ProductDTO p : productDTOS) {
            products.add(productService.getOne(p.getId()));
        }
        return products;
    }
}
