package com.ecommerce.main.payment.gateAway;

import com.ecommerce.main.order.entity.Order;
import com.ecommerce.main.payment.dto.PaymentRequest;
import com.ecommerce.main.payment.dto.PaymentResult;
import com.ecommerce.main.payment.dto.VerifyRequest;

public interface PaymentGateway {
    String creatPayment(PaymentRequest paymentRequest, Order order );
    PaymentResult verifyPayment(VerifyRequest request);
}

