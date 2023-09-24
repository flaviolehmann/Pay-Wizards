package br.edu.ifes.workshop.paywizards.ext;

import org.springframework.stereotype.Service;

import br.edu.ifes.workshop.paywizards.model.Payment;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FinancialService {

    public void registerPayment(Payment p) {
        log.info("[{}] was successfully transferred from [{}] to [{}].",
                 p.getAmount(), p.getFromAcc(), p.getToAcc());
    }
}
