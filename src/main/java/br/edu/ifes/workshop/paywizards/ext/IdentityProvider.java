package br.edu.ifes.workshop.paywizards.ext;

import org.springframework.stereotype.Service;

import java.util.Map;

import br.edu.ifes.workshop.paywizards.model.type.CustomerType;

@Service
public class IdentityProvider {

    public static final Map<String, CustomerType> MOCKED_CUSTOMERS = Map.of(
            "ebay-us-2b8a8bc2f", CustomerType.NORMAL,
            "meta-us-2b8a8bc2f", CustomerType.VIP,
            "amzn-us-2b8a8bc2f", CustomerType.KNOW_FRAUDSTER);

    public CustomerType queryCustomer(String customerId) {
        return MOCKED_CUSTOMERS.getOrDefault(customerId, CustomerType.FIRST_TIMER);
    }
}
