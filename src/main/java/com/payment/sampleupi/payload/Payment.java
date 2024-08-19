package com.payment.sampleupi.payload;

import java.math.BigDecimal;

public class Payment {
    private BigDecimal amount;


    private String upiPassword;
    private String description;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUpiPassword() {
        return upiPassword;
    }

    public void setUpiPassword(String upiPassword) {
        this.upiPassword = upiPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
