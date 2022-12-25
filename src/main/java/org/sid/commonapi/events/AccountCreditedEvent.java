package org.sid.commonapi.events;

import lombok.Getter;
import org.sid.commonapi.enums.AccountStatus;

public class AccountCreditedEvent extends BaseEvent<String>{
    @Getter private double amount;
    @Getter private String currency;

    public AccountCreditedEvent(String id, double amount, String currency) {
        super(id);
        this.currency = currency;
        this.amount = amount;
    }
}
