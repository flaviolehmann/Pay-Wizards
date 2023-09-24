package br.edu.ifes.workshop.paywizards.model;

import lombok.Data;

@Data
public class Payment {

    private String fromAcc;
    private String toAcc;
    private String customerId;
    private Long amount;
}
