package com.ecommerce.main.payment.controller;


import com.ecommerce.main.common.dto.ApiResponse;
import com.ecommerce.main.payment.dto.PaymentResult;
import com.ecommerce.main.payment.dto.PaymentType;
import com.ecommerce.main.payment.dto.VerifyRequest;
import com.ecommerce.main.payment.service.PaymentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RequestMapping("api/v1/payment")
@Tag(name="payment process for user")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentServiceImpl paymentService;

    @Operation(summary = "this is for starting payment , it send the data it needs for bank and recieve a url string as response . you should then redirect the client ot bank by this url so that he can pay")
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse> createPayment(@PathVariable(value = "id") Long orderId, @RequestParam(value = "method",required = false, defaultValue = "ZARINPALL") PaymentType method) {
        String paymentUrl = paymentService.createPayment(method, orderId);
        return ResponseEntity.ok().body(new ApiResponse("ok", paymentUrl));
    }

    @Operation(summary = "after user payed he will redirect to confirmed payment page and if status in ok you should send this request . you should send authority INFO  you recieve redirected url (confirm_url/?authority&&status=OK)")
    @PostMapping
    public ResponseEntity<ApiResponse> confirmPayment(@Valid @RequestBody VerifyRequest request) {
        PaymentResult paymentResult = paymentService.confirmPayment(request);
        return ResponseEntity.ok().body(new ApiResponse("ok", paymentResult));


    }
}
