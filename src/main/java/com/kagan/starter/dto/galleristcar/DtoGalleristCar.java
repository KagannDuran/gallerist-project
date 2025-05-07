package com.kagan.starter.dto.galleristcar;


import com.kagan.starter.dto.car.DtoCar;
import com.kagan.starter.dto.gallerist.DtoGallerist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoGalleristCar {
    private Long id;
    private DtoGallerist gallerist;
    private DtoCar car;
    private Date createTime;

}
