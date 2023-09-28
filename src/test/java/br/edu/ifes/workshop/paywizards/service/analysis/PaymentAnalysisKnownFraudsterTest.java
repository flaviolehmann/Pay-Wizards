package br.edu.ifes.workshop.paywizards.service.analysis;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import br.edu.ifes.workshop.paywizards.model.type.CustomerType;
import br.edu.ifes.workshop.paywizards.service.risk.RiskReport;

import static org.junit.jupiter.api.Assertions.*;

class PaymentAnalysisKnownFraudsterTest {

    private static final PaymentAnalysisKnownFraudster SUBJECT = new PaymentAnalysisKnownFraudster();

    @EnumSource(CustomerType.class)
    @ParameterizedTest
    void supportsWorks(CustomerType customerType) {
        RiskReport riskReport = new RiskReport(null, customerType);

        if (customerType.equals(CustomerType.KNOW_FRAUDSTER)) {
            assertTrue(SUBJECT.supports(riskReport));
            return;
        }
        assertFalse(SUBJECT.supports(riskReport));
    }

    @Test
    void shouldApproveWorks() {
        assertFalse(SUBJECT.shouldApprove(null));
    }
}