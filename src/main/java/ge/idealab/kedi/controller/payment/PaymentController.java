package ge.idealab.kedi.controller.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ge.idealab.kedi.model.CARTU.ConfirmRequest;
import ge.idealab.kedi.model.CARTU.ConfirmResponse;
import ge.idealab.kedi.service.payment.PaymentService;
import ge.idealab.kedi.util.HttpServletRequestDebug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;


@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private HttpServletRequestDebug httpServletRequestDebug;
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/cartu/callback", method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public String cartuCallBack(@RequestParam Map<String, String> params, String signature, HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
//        this.httpServletRequestDebug.debugRequest(request);

        ConfirmRequest confirmRequest = this.mapConfirmRequestParams(params.get("ConfirmRequest"));
        ConfirmResponse confirmResponse = paymentService.checkPayment(confirmRequest);

        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        ObjectMapper xmlMapper = new XmlMapper(xmlModule);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ((XmlMapper) xmlMapper).configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        String xmlString = "";
        try {
            xmlString = xmlMapper.writeValueAsString(confirmResponse);
        } catch (Exception ex) {

        }
        System.out.println("RESP: " + xmlString);
        return xmlString;
    }

    ConfirmRequest mapConfirmRequestParams(String str) throws IOException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ConfirmRequest confirmRequest = xmlMapper.readValue(str, ConfirmRequest.class);

        return confirmRequest;
    }
}
