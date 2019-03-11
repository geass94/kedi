package ge.idealab.kedi.controller.payment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @PostMapping("/cart/callback")
    public void cartuCallBack() {
        System.out.println("CALLBACK");
    }
}
