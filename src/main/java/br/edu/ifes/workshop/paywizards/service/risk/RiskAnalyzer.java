package br.edu.ifes.workshop.paywizards.service.risk;

import org.springframework.stereotype.Service;

import br.edu.ifes.workshop.paywizards.ext.IdentityProvider;
import br.edu.ifes.workshop.paywizards.model.Payment;
import br.edu.ifes.workshop.paywizards.model.exception.RiskAnalysisException;
import br.edu.ifes.workshop.paywizards.model.type.CustomerType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RiskAnalyzer {

    private final IdentityProvider identityProvider;

    public RiskReport analyze(Payment payment) {
        CustomerType customerType = identityProvider.queryCustomer(payment.getCustomerId());

        switch (customerType) {
            case NORMAL, FIRST_TIMER -> {
                return new RiskReport(RiskLevel.NORMAL, customerType);
            }
            case VIP -> {
                return new RiskReport(RiskLevel.NOT_RISKY, customerType);
            }
            case KNOW_FRAUDSTER -> {
                return new RiskReport(RiskLevel.RISKY, customerType);
            }
        }
        throw new RiskAnalysisException();
    }
}
