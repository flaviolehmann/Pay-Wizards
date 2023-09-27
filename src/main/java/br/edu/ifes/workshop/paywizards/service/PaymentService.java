package br.edu.ifes.workshop.paywizards.service;

import org.springframework.stereotype.Service;

import java.util.Set;

import br.edu.ifes.workshop.paywizards.ext.FinancialService;
import br.edu.ifes.workshop.paywizards.ext.InternalHistoryStore;
import br.edu.ifes.workshop.paywizards.model.Payment;
import br.edu.ifes.workshop.paywizards.model.exception.PaymentTooRiskyException;
import br.edu.ifes.workshop.paywizards.model.type.CustomerType;
import br.edu.ifes.workshop.paywizards.service.analysis.PaymentAnalysis;
import br.edu.ifes.workshop.paywizards.service.risk.RiskAnalyzer;
import br.edu.ifes.workshop.paywizards.service.risk.RiskLevel;
import br.edu.ifes.workshop.paywizards.service.risk.RiskReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RiskAnalyzer riskAnalyzer;
    private final FinancialService financialService;
    private final InternalHistoryStore internalHistoryStore;
    private final Set<PaymentAnalysis> analyses;

    public void pay(Payment payment) {
        RiskReport riskReport = riskAnalyzer.analyze(payment);

        for (PaymentAnalysis analysis : analyses) {
            if (analysis.supports(riskReport)) {
                if (analysis.shouldApprove(payment)) {
                    approve(payment);
                } else {
                    deny(payment);
                }
                return;
            }
        }
        deny(payment);
    }

    private void approve(Payment payment) {
        log.info("Payment APPROVED for customer [{}]", payment.getCustomerId());
        financialService.registerPayment(payment);
        internalHistoryStore.registerCustomerAsGood(payment.getCustomerId());
    }

    private void deny(Payment payment) {
        log.info("Payment DENIED for customer [{}]", payment.getCustomerId());
        internalHistoryStore.registerCustomerAsEvil(payment.getCustomerId());
        throw new PaymentTooRiskyException();
    }
}
