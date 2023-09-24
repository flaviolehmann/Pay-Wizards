package br.edu.ifes.workshop.paywizards.service.risk;

import br.edu.ifes.workshop.paywizards.model.type.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RiskReport {

    private RiskLevel riskLevel;
    private CustomerType customerType;
}
