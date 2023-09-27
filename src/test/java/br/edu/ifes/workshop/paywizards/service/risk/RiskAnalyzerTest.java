package br.edu.ifes.workshop.paywizards.service.risk;

import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import br.edu.ifes.workshop.paywizards.ext.IdentityProvider;
import br.edu.ifes.workshop.paywizards.model.Payment;
import br.edu.ifes.workshop.paywizards.model.type.CustomerType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RiskAnalyzerTest {

    @InjectMocks
    private RiskAnalyzer riskAnalyzer;

    @Mock
    private IdentityProvider identityProvider;

    @EnumSource(CustomerType.class)
    @ParameterizedTest
    void analyzeWorks(CustomerType customerType) {
        // given
        var payment = buildPayment();
        when(identityProvider.queryCustomer(any())).thenReturn(customerType);

        // when
        RiskReport report = riskAnalyzer.analyze(payment);

        // then
        assertEquals(customerType, report.getCustomerType());
        verify(identityProvider, times(1)).queryCustomer(any());
    }

    private Payment buildPayment() {
        Payment payment = new Payment();
        payment.setFromAcc("192819281");
        payment.setToAcc("443829435");
        payment.setCustomerId("ebay-us-2b8a8bc2f");
        payment.setAmount(500L);
        return payment;
    }
}