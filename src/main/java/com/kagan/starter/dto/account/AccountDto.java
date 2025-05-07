package com.kagan.starter.dto.account;

import com.kagan.starter.enums.CurrencyType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.convert.DataSizeUnit;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;
    private String accountNo;
    private String iban;
    private BigDecimal price;
    private CurrencyType currencyType;
    private Date createTime;

}
