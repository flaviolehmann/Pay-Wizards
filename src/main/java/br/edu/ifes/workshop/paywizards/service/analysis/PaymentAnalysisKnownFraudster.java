package br.edu.ifes.workshop.paywizards.service.analysis;

import org.springframework.stereotype.Component;

import br.edu.ifes.workshop.paywizards.model.Payment;
import br.edu.ifes.workshop.paywizards.model.type.CustomerType;
import br.edu.ifes.workshop.paywizards.service.risk.RiskReport;

@Component
public class PaymentAnalysisKnownFraudster implements PaymentAnalysis {

    @Override
    public boolean supports(RiskReport r) {
        return r.getCustomerType().equals(CustomerType.KNOW_FRAUDSTER);
    }

    @Override
    public boolean shouldApprove(Payment p) {
        return false;
    }
}
