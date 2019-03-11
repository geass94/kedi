package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.OrderDTO;
import ge.idealab.kedi.payload.request.InitPaymentRequest;
import ge.idealab.kedi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "https://kedi.ge")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.placeOrder(orderDTO));
    }

    @PostMapping("/init-payment")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> initPayment(@RequestBody InitPaymentRequest initPaymentRequest) {
        return ResponseEntity.ok(orderService.initPayment(initPaymentRequest));
    }
}
