package com.kagan.starter.dto.account;

import com.kagan.starter.enums.CurrencyType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDtoIU {
    @NotBlank
    private String accountNo;

    @NotBlank
    private String iban;

    @NotNull
    private BigDecimal price;

    @NotNull
    private CurrencyType currencyType;
}
