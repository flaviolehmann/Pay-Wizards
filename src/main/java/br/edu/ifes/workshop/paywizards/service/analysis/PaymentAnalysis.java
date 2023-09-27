package br.edu.ifes.workshop.paywizards.service.analysis;

import br.edu.ifes.workshop.paywizards.model.Payment;
import br.edu.ifes.workshop.paywizards.service.risk.RiskReport;

public interface PaymentAnalysis {

    boolean supports(RiskReport r);

    boolean shouldApprove(Payment p);
}
