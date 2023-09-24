package br.edu.ifes.workshop.paywizards.ext;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InternalHistoryStore {

    public void registerCustomerAsGood(String customerId) {
        log.info("Customer [{}] had their account activity history updated as GOOD.",
                 customerId);
    }

    public void registerCustomerAsEvil(String customerId) {
        log.info("Customer [{}] had their account activity history updated as EVIL.",
                 customerId);
    }

    @SuppressWarnings("unused")
    public void registerCustomerAsUgly(String customerId) {
        log.info("Customer [{}] had their account activity history updated as UGLY.",
                 customerId);
    }
}
