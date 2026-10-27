package com.ecommerce.main.payment.gateAway;

import com.ecommerce.main.common.exception.OrderNotFoundException;
import com.ecommerce.main.common.exception.PaymentGatewayException;
import com.ecommerce.main.order.entity.Order;
import com.ecommerce.main.order.entity.OrderStatus;
import com.ecommerce.main.order.repository.OrderRepository;
import com.ecommerce.main.payment.dto.PaymentRequest;
import com.ecommerce.main.payment.dto.PaymentResult;
import com.ecommerce.main.payment.dto.VerifyRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Component
public class ZarinPallGateway implements PaymentGateway { // adjust to your interface name

    private static final String REQUEST_URL = "https://sandbox.zarinpal.com/pg/v4/payment/request.json";
    private static final String VERIFY_URL = "https://sandbox.zarinpal.com/pg/v4/payment/verify.json";
    private static final String START_PAY_URL = "https://sanbox.zarinpal.com/pg/StartPay/";

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE =
            new ParameterizedTypeReference<>() {
            };

    private final String callbackUrl;
    private final String merchantId;
    private final RestClient restClient;
    private final OrderRepository orderRepository;

    public ZarinPallGateway(@Value("${zarinpal.callback-url}") String callbackUrl,
                            @Value("${zarinpal.merchant-id}") String merchantId,
                            RestClient.Builder restClientBuilder, OrderRepository orderRepository) {
        this.callbackUrl = callbackUrl;
        this.merchantId = merchantId;
        this.restClient = restClientBuilder.build();
        this.orderRepository = orderRepository;     // build once, reuse for every call
    }

    /**
     * Step 1: asks ZarinPal for a payment session and returns the URL to redirect the user to.
     */
    @Override
    public String creatPayment(PaymentRequest req,Order order) {
        Map<String, Object> body = Map.of(
                "merchant_id", merchantId,
                "amount", req.amount(),          // Rial by default. For Toman also add: "currency", "IRT"
                "callback_url", callbackUrl,
                "description", "پرداخت سفارش");  // required by ZarinPal


        Map<String, Object> data = asMap(post(REQUEST_URL, body).get("data"));

        if (!hasCode(data, 100)) {
            throw new PaymentGatewayException("ایجاد تراکنش در درگاه پرداخت ناموفق بود");
        }

        String authority = (String) data.get("authority");
        order.setAuthorityCode(authority);
        // TODO: save `authority` with the order, so the callback can find the order later
        // .
        return START_PAY_URL + authority;
    }


    /**
     * Step 2 (after the user returns to your callback with Status=OK):
     * confirms the payment with ZarinPal and returns the bank reference id.
     */

    @Override
    public PaymentResult verifyPayment(VerifyRequest request) {
        Order order = findAndSendOrderAmount(request);


        Map<String, Object> body = Map.of(
                "merchant_id", merchantId,
                "amount", order.getTotalPrice().intValue(),
                "authority", request.authority());

        Map<String, Object> data = asMap(post(VERIFY_URL, body).get("data"));

        // 100 = verified now, 101 = this payment was already verified earlier
        if (!hasCode(data, 100) && !hasCode(data, 101)) {
            throw new PaymentGatewayException("پرداخت تایید نشد");
        }

        int refrence = data.get("ref_id") instanceof Number refId ? refId.intValue() : 0;
        order.setStatus(OrderStatus.PAID);

        return new PaymentResult(true, refrence, "پرداخت شما با موفقیت انجام شد");

        ///throw new PaymentGatewayException("پرداخت ناموفق بود در صورت کسر از حساب شما, مبلغ تا حداکثر ۷۲ ساعت آینده به حساب شما باز گردانده میشود");


    }


    private Map<String, Object> post(String url, Map<String, Object> body) {
        try {
            Map<String, Object> response = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(MAP_TYPE);

            if (response == null) {
                throw new PaymentGatewayException("پاسخی از درگاه پرداخت دریافت نشد");
            }
            return response;
        } catch (RestClientException ex) {
            // Timeout, connection failure, or a 4xx/5xx response
            throw new PaymentGatewayException("ارتباط با درگاه پرداخت برقرار نشد");
        }
    }

    // On errors ZarinPal can send "data" as an empty array, so read it defensively.
    @SuppressWarnings("unchecked")
    private Map<String, Object> asMap(Object value) {
        return value instanceof Map<?, ?> map ? (Map<String, Object>) map : Map.of();
    }

    private boolean hasCode(Map<String, Object> data, int expected) {
        return data.get("code") instanceof Number code && code.intValue() == expected;
    }

    /// update order to payed before verification
    private Order findAndSendOrderAmount(VerifyRequest verifyRequest) {
        Order order = orderRepository.findByAuthorityCode(verifyRequest.authority()).orElseThrow(OrderNotFoundException::new);
        return order;
    }
}