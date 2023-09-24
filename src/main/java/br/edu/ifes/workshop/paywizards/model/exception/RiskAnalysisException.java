package br.edu.ifes.workshop.paywizards.model.exception;

public class RiskAnalysisException extends RuntimeException {

    public static final String MSG = "General failure during Risk Analysis.";

    public RiskAnalysisException() {
        super(MSG);
    }
}
