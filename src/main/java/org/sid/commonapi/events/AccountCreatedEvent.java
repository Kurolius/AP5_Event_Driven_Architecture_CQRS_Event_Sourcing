package org.sid.commonapi.events;

import lombok.Getter;
import org.sid.commonapi.enums.AccountStatus;

public class AccountCreatedEvent extends BaseEvent<String>{

    @Getter private double initialeBalance;
    @Getter private String currency;
    @Getter private AccountStatus accountStatus;

    public AccountCreatedEvent(String id, double initialeBalance, String currency, AccountStatus accountStatus) {
        super(id);
        this.initialeBalance = initialeBalance;
        this.currency = currency;
        this.accountStatus = accountStatus;
    }
}
