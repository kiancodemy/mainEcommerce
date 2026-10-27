package com.ecommerce.main.payment.service;

import com.ecommerce.main.common.exception.OrderNotFoundException;
import com.ecommerce.main.order.entity.Order;
import com.ecommerce.main.order.repository.OrderRepository;
import com.ecommerce.main.payment.dto.PaymentRequest;
import com.ecommerce.main.payment.dto.PaymentResult;
import com.ecommerce.main.payment.dto.PaymentType;
import com.ecommerce.main.payment.dto.VerifyRequest;
import com.ecommerce.main.payment.gateAway.PaymentGateway;
import com.ecommerce.main.payment.gateAway.ZarinPallGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl{

    private final OrderRepository orderRepository;
    private final ZarinPallGateway zarinPallGateway;



    public String createPayment(PaymentType paymentType, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        PaymentGateway paymentGateway = selectPaymentGateway(paymentType);
        PaymentRequest paymentRequest = new PaymentRequest(order.getTotalPrice().intValue());
        return paymentGateway.creatPayment(paymentRequest, order);
    }

    public PaymentResult confirmPayment(VerifyRequest verifyRequest) {
        PaymentResult paymentResult = zarinPallGateway.verifyPayment(verifyRequest);
        return paymentResult;

    }

    private PaymentGateway selectPaymentGateway(PaymentType paymentType) {
        return switch (paymentType) {

            default -> zarinPallGateway;

        };
    }
}
