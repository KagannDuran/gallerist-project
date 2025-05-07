package com.kagan.starter.dto.car;

import com.kagan.starter.enums.CarStatusType;
import com.kagan.starter.enums.CurrencyType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCarIU {

    @NotNull
    private String plaka;

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    private Integer productionYear;

    @NotNull
    private BigDecimal price;

    @NotNull
    private CurrencyType currencyType;

    @NotNull
    private BigDecimal  decimalPrice;

    @NotNull
    private CarStatusType carStatusType;
}
