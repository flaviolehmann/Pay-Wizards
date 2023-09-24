package br.edu.ifes.workshop.paywizards.service;

import org.springframework.stereotype.Service;

import br.edu.ifes.workshop.paywizards.ext.FinancialService;
import br.edu.ifes.workshop.paywizards.ext.InternalHistoryStore;
import br.edu.ifes.workshop.paywizards.model.Payment;
import br.edu.ifes.workshop.paywizards.model.exception.PaymentTooRiskyException;
import br.edu.ifes.workshop.paywizards.model.type.CustomerType;
import br.edu.ifes.workshop.paywizards.service.risk.RiskAnalyzer;
import br.edu.ifes.workshop.paywizards.service.risk.RiskLevel;
import br.edu.ifes.workshop.paywizards.service.risk.RiskReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    public static final int MAX_AMOUNT_FOR_NORMAL_PAYMENTS = 1000;
    public static final int MAX_AMOUNT_FOR_FIRST_TIMER_PAYMENTS = 200;
    public static final int MAX_AMOUNT_FOR_RISKY_PAYMENTS = 50;

    private final RiskAnalyzer riskAnalyzer;
    private final FinancialService financialService;
    private final InternalHistoryStore internalHistoryStore;

    public void pay(Payment payment) {
        RiskReport riskReport = riskAnalyzer.analyze(payment);

        if (riskReport.getCustomerType().equals(CustomerType.VIP)) {
            approve(payment);
            return;
        }
        else if (riskReport.getCustomerType().equals(CustomerType.FIRST_TIMER)) {
            if (payment.getAmount() <= MAX_AMOUNT_FOR_FIRST_TIMER_PAYMENTS) {
                approve(payment);
                return;
            }
            deny(payment);
            return;
        }
        else if (riskReport.getRiskLevel().equals(RiskLevel.NOT_RISKY)) {
            approve(payment);
            return;
        }
        else if (riskReport.getRiskLevel().equals(RiskLevel.NORMAL)
                 || riskReport.getCustomerType().equals(CustomerType.NORMAL)) {
            if (payment.getAmount() <= MAX_AMOUNT_FOR_NORMAL_PAYMENTS) {
                approve(payment);
                return;
            }
            deny(payment);
            return;
        }
        else if (riskReport.getRiskLevel().equals(RiskLevel.RISKY)) {
            if (payment.getAmount() <= MAX_AMOUNT_FOR_RISKY_PAYMENTS) {
                approve(payment);
                return;
            }
            deny(payment);
            return;
        }
        else if (riskReport.getCustomerType().equals(CustomerType.KNOW_FRAUDSTER)) {
            deny(payment);
            return;
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
