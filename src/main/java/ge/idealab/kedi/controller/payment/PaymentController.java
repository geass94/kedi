package ge.idealab.kedi.controller.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ge.idealab.kedi.model.CARTU.ConfirmRequest;
import ge.idealab.kedi.model.CARTU.ConfirmResponse;
import ge.idealab.kedi.util.HttpServletRequestDebug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private HttpServletRequestDebug httpServletRequestDebug;

    @RequestMapping(value = "/cartu/callback", method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public String cartuCallBack(@RequestParam Map<String, String> params, String signature, HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        this.receiveCallback(request);
        ConfirmRequest confirmRequest = this.mapConfirmRequestParams(params.get("ConfirmRequest"));

        ConfirmResponse confirmResponse = new ConfirmResponse();

        confirmResponse.setTransactionId(confirmRequest.getTransactionId());
        confirmResponse.setPaymentId(confirmRequest.getPaymentId());
        confirmResponse.setStatus("ACCEPTED");

//        JacksonXmlModule module = new JacksonXmlModule();
//        module.setDefaultUseWrapper(false);
//        ObjectMapper xmlMapper = new XmlMapper(module);
//        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        ((XmlMapper) xmlMapper).configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        String xmlString = "<ConfirmResponse>" +
                "<TransactionId>"+confirmResponse.getTransactionId()+"</TransactionId>" +
                "<PaymentId>"+confirmResponse.getPaymentId()+"</PaymentId>" +
                "<Status>"+confirmResponse.getStatus()+"</Status>" +
                "</ConfirmResponse>";
//        try {
//            xmlString = xmlMapper.writeValueAsString(confirmResponse);
//        } catch (Exception ex) {
//
//        }
        System.out.println("RESP: " + xmlString);
        return xmlString;
    }

    ConfirmRequest mapConfirmRequestParams(String str) throws IOException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ConfirmRequest confirmRequest = xmlMapper.readValue(str, ConfirmRequest.class);

//        System.out.println("TRID: " + confirmRequest.getTransactionId());
//        System.out.println("PID: " + confirmRequest.getPaymentId());
//        System.out.println("STATUS :" +confirmRequest.getStatus());
//        System.out.println("AMOUNT: " + confirmRequest.getAmount());
//        System.out.println("TYPE: " + confirmRequest.getCardType());
//        System.out.println("DATE: " + confirmRequest.getPaymentDate());
//        System.out.println("REASON: " + confirmRequest.getReason());

        return confirmRequest;
    }


    BigDecimal strToBD(String number) {
        Double dbl = Double.valueOf(number);
        return BigDecimal.valueOf(dbl);
    }

    Date strToDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = formatter.parse(dateStr);
        return date;
    }

    private void receiveCallback(HttpServletRequest request) {
        httpServletRequestDebug.debugRequest(request);
    }
}
