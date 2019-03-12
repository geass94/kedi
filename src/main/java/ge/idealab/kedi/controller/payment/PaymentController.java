package ge.idealab.kedi.controller.payment;

import ge.idealab.kedi.model.CARTU.ConfirmRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @RequestMapping("/cartu/callback")
    public void cartuCallBack(@RequestBody ConfirmRequest confirmRequest, HttpServletRequest request,
                              HttpServletResponse response) throws IOException, ServletException {
        System.out.println(confirmRequest.getTransactionId());
    }
}
