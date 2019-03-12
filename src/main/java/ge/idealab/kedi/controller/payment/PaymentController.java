package ge.idealab.kedi.controller.payment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @RequestMapping("/cartu/callback")
    public void cartuCallBack(HttpServletRequest request,
                              HttpServletResponse response) throws IOException, ServletException {
        System.out.println(request.getContextPath());
        System.out.println(request.getMethod());
        System.out.println(request.getQueryString());
        for (Part p : request.getParts()){
            System.out.println(p.getName());
        }
    }
}
