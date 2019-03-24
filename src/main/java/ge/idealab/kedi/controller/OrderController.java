package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.OrderDTO;
import ge.idealab.kedi.model.order.Order;
import ge.idealab.kedi.payload.request.InitPaymentRequest;
import ge.idealab.kedi.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
//@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/get-order-history")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getOrderHistory(){
        ModelMapper modelMapper = new ModelMapper();

        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderService.getOrderHistory()){
            orderDTOS.add(modelMapper.map(order, OrderDTO.class));
        }
        return ResponseEntity.ok(orderDTOS);
    }
}
