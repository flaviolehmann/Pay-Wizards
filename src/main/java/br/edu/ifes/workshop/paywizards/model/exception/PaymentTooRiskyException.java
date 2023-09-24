package br.edu.ifes.workshop.paywizards.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentTooRiskyException extends RuntimeException {

    public static final String MSG = "Payment is to risky to be performed. Operation cancelled.";

    public PaymentTooRiskyException() {
        super(MSG);
    }
}
