package com.kagan.starter.dto.saledCar;

import com.kagan.starter.dto.car.DtoCar;
import com.kagan.starter.dto.customer.DtoCustomer;
import com.kagan.starter.dto.gallerist.DtoGallerist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoSaledCar {
    private Long id;
    private DtoCustomer customer;
    private DtoGallerist gallerist;
    private DtoCar car;
    private Date createTime;

}
