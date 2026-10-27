package com.ecommerce.main.payment;


import com.ecommerce.main.common.dto.ApiResponse;
import com.ecommerce.main.payment.dto.PaymentResult;
import com.ecommerce.main.payment.dto.PaymentType;
import com.ecommerce.main.payment.dto.VerifyRequest;
import com.ecommerce.main.payment.service.PaymentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RequestMapping("api/va/payment")
@RequiredArgsConstructor
public class Controller {
    private final PaymentServiceImpl paymentService;

    @PostMapping("/{id}/{method}")
    public ResponseEntity<ApiResponse> createPayment(@PathVariable(value = "id") Long orderId, @RequestParam(value = "method",required = false, defaultValue = "ZARINPALL") PaymentType method) {
        String paymentUrl = paymentService.createPayment(method, orderId);
        return ResponseEntity.ok().body(new ApiResponse("ok", paymentUrl));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> confirmPayment(@Valid @RequestBody VerifyRequest request) {
        PaymentResult paymentResult = paymentService.confirmPayment(request);
        return ResponseEntity.ok().body(new ApiResponse("ok", paymentResult));


    }
}
