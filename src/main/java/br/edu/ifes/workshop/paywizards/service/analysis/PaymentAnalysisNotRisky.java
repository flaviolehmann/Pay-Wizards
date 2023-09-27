package br.edu.ifes.workshop.paywizards.service.analysis;

import org.springframework.stereotype.Component;

import br.edu.ifes.workshop.paywizards.model.Payment;
import br.edu.ifes.workshop.paywizards.service.risk.RiskLevel;
import br.edu.ifes.workshop.paywizards.service.risk.RiskReport;

@Component
public class PaymentAnalysisNotRisky implements PaymentAnalysis {

    @Override
    public boolean supports(RiskReport r) {
        return r.getRiskLevel().equals(RiskLevel.NOT_RISKY);
    }

    @Override
    public boolean shouldApprove(Payment p) {
        return true;
    }
}
