package ge.idealab.kedi.controller.payment;

import ge.idealab.kedi.model.CARTU.ConfirmRequest;
import ge.idealab.kedi.model.CARTU.ConfirmResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @RequestMapping(value = "/cartu/callback", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE})
    public ConfirmResponse cartuCallBack(@RequestBody ConfirmRequest confirmRequest, HttpServletRequest request,
                                         HttpServletResponse response) throws IOException, ServletException {
        System.out.println(confirmRequest.getTransactionId());
        ConfirmResponse confirmResponse = new ConfirmResponse();
        confirmResponse.setTransactionId(confirmRequest.getTransactionId());
        confirmResponse.setPaymentId(confirmRequest.getPaymentId());
        confirmResponse.setStatus("ACCEPTED");
        return confirmResponse;
    }
}
