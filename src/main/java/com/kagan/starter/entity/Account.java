package com.kagan.starter.entity;

import com.kagan.starter.enums.CurrencyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "iban")
    private String iban;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "current_type")
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;
}
