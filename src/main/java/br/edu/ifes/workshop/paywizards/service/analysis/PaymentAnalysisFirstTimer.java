package br.edu.ifes.workshop.paywizards.service.analysis;

import org.springframework.stereotype.Component;

import br.edu.ifes.workshop.paywizards.model.Payment;
import br.edu.ifes.workshop.paywizards.model.type.CustomerType;
import br.edu.ifes.workshop.paywizards.service.risk.RiskReport;

@Component
public class PaymentAnalysisFirstTimer implements PaymentAnalysis {

    public static final int MAX_AMOUNT_FOR_FIRST_TIMER_PAYMENTS = 200;

    @Override
    public boolean supports(RiskReport r) {
        return r.getCustomerType().equals(CustomerType.FIRST_TIMER);
    }

    @Override
    public boolean shouldApprove(Payment p) {
        if (p.getAmount() <= MAX_AMOUNT_FOR_FIRST_TIMER_PAYMENTS) {
            return true;
        }
        return false;
    }
}
