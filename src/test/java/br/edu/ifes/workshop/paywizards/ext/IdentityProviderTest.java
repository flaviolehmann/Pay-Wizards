package br.edu.ifes.workshop.paywizards.ext;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import br.edu.ifes.workshop.paywizards.model.type.CustomerType;

import static org.junit.jupiter.api.Assertions.*;

class IdentityProviderTest {

    private static final IdentityProvider SUBJECT = new IdentityProvider();

//    @Test
//    void queryCustomerReturnsCustomerTypeWhenValidCustomer() {
//        CustomerType customerType = SUBJECT.queryCustomer("ebay-us-2b8a8bc2f");
//        assertEquals(CustomerType.NORMAL, customerType);
//    }
//
//    @Test
//    void queryCustomerReturnsNullTypeWhenInvalidCustomer() {
//        CustomerType actual = SUBJECT.queryCustomer("xxxx-us-2b8a8bc2f");
//        assertEquals(CustomerType.FIRST_TIMER, actual);
//    }
//
//    @DisplayName("queryCustomer returns FIRST_TIMER when input is null")
//    @Test
//    void queryCustomerWithNullInput() {
//        CustomerType actual = SUBJECT.queryCustomer(null);
//        assertEquals(CustomerType.FIRST_TIMER, actual);
//    }

    @CsvSource({
            "ebay-us-2b8a8bc2f, NORMAL",
            "xxxx-us-2b8a8bc2f, FIRST_TIMER",
            "null, FIRST_TIMER"
    })
    @ParameterizedTest(name = "queryCustomer returns {1} when input is {0}")
    void queryCustomerWorks(String customerId, CustomerType expected) {
        if (customerId.equals("null")) {
            customerId = null;
        }
        CustomerType actual = SUBJECT.queryCustomer(customerId);
        assertEquals(expected, actual);
    }

    @DisplayName("queryCustomer throws exception when input is too long")
    @Test
    void queryCustomerWithTooLongInput() {
        assertThrows(RuntimeException.class, () -> SUBJECT.queryCustomer("amzn-us-2b8a8bc2ff"));
    }
}