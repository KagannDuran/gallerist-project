package com.kagan.starter.dto.car;

import com.kagan.starter.enums.CarStatusType;
import com.kagan.starter.enums.CurrencyType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCar {

    private Long id;
    private String plaka;
    private String brand;
    private String model;
    private Integer productionYear;
    private BigDecimal price;
    private CurrencyType currencyType;
    private BigDecimal  decimalPrice;
    private Date createTime;
    private CarStatusType carStatusType;
}
