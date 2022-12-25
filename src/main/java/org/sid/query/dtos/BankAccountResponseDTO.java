package org.sid.query.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.commonapi.enums.AccountStatus;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountResponseDTO {
    private String id;
    private BigDecimal balance;
    private AccountStatus status;
}
