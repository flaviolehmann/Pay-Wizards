package br.edu.ifes.workshop.paywizards.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.HashSet;
import java.util.Set;

import br.edu.ifes.workshop.paywizards.ext.FinancialService;
import br.edu.ifes.workshop.paywizards.ext.InternalHistoryStore;
import br.edu.ifes.workshop.paywizards.model.Payment;
import br.edu.ifes.workshop.paywizards.model.exception.PaymentTooRiskyException;
import br.edu.ifes.workshop.paywizards.model.type.CustomerType;
import br.edu.ifes.workshop.paywizards.service.analysis.PaymentAnalysis;
import br.edu.ifes.workshop.paywizards.service.analysis.PaymentAnalysisKnownFraudster;
import br.edu.ifes.workshop.paywizards.service.analysis.PaymentAnalysisVip;
import br.edu.ifes.workshop.paywizards.service.risk.RiskAnalyzer;
import br.edu.ifes.workshop.paywizards.service.risk.RiskLevel;
import br.edu.ifes.workshop.paywizards.service.risk.RiskReport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private RiskAnalyzer riskAnalyzer;

    @Mock
    private FinancialService financialService;

    @Mock
    private InternalHistoryStore internalHistoryStore;

    @Spy
    private Set<PaymentAnalysis> analyses = new HashSet<>();

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private PaymentAnalysisVip paymentAnalysisVip;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private PaymentAnalysisKnownFraudster paymentAnalysisKnownFraudster;

    @BeforeEach
    void config() {
        analyses.add(paymentAnalysisVip);
        analyses.add(paymentAnalysisKnownFraudster);
    }

    @Test
    void payWorksApproveFlow() {
        // given
        Payment payment = buildPayment();
        when(riskAnalyzer.analyze(any())).thenReturn(new RiskReport(RiskLevel.NORMAL, CustomerType.VIP));

        // when
        paymentService.pay(payment);

        // then
        verify(financialService, times(1)).registerPayment(any());
        verify(internalHistoryStore, times(1)).registerCustomerAsGood(any());
    }

    @Test
    void payWorksDenyFlow() {
        // given
        Payment payment = buildPayment();
        when(riskAnalyzer.analyze(any())).thenReturn(new RiskReport(RiskLevel.NORMAL, CustomerType.KNOW_FRAUDSTER));

        // when
        assertThrows(PaymentTooRiskyException.class, () -> paymentService.pay(payment));

        // then
        verify(internalHistoryStore, times(1)).registerCustomerAsEvil(any());
    }

    private Payment buildPayment() {
        Payment payment = new Payment();
        payment.setFromAcc("192819281");
        payment.setToAcc("443829435");
        payment.setCustomerId("meta-us-2b8a8bc2f");
        payment.setAmount(500L);
        return payment;
    }
}