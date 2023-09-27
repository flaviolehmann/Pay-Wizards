package br.edu.ifes.workshop.paywizards.ext;

import org.junit.jupiter.api.*;

import br.edu.ifes.workshop.paywizards.model.Payment;

import static org.junit.jupiter.api.Assertions.*;

class FinancialServiceTest {

    private static final FinancialService SUBJECT = new FinancialService();

    @Test
    void registerPaymentWorks() {
        Payment payment = new Payment();
        payment.setFromAcc("192819281");
        payment.setToAcc("443829435");
        payment.setCustomerId("ebay-us-2b8a8bc2f");
        payment.setAmount(500L);

        assertDoesNotThrow(() -> SUBJECT.registerPayment(payment));
    }
}