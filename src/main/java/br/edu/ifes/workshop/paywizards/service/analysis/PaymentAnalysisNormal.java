package br.edu.ifes.workshop.paywizards.service.analysis;

import org.springframework.stereotype.Component;

import br.edu.ifes.workshop.paywizards.model.Payment;
import br.edu.ifes.workshop.paywizards.model.type.CustomerType;
import br.edu.ifes.workshop.paywizards.service.risk.RiskLevel;
import br.edu.ifes.workshop.paywizards.service.risk.RiskReport;

@Component
public class PaymentAnalysisNormal implements PaymentAnalysis {

    public static final int MAX_AMOUNT_FOR_NORMAL_PAYMENTS = 1000;

    @Override
    public boolean supports(RiskReport r) {
        return r.getRiskLevel()
                       .equals(RiskLevel.NORMAL) || r.getCustomerType()
                       .equals(CustomerType.NORMAL);
    }

    @Override
    public boolean shouldApprove(Payment p) {
        return p.getAmount() <= MAX_AMOUNT_FOR_NORMAL_PAYMENTS;
    }
}
