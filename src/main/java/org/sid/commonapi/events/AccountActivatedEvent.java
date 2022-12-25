package org.sid.commonapi.events;

import lombok.Getter;
import org.sid.commonapi.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String>{


    @Getter private AccountStatus accountStatus;

    public AccountActivatedEvent(String id,  AccountStatus accountStatus) {
        super(id);
        this.accountStatus = accountStatus;
    }
}
