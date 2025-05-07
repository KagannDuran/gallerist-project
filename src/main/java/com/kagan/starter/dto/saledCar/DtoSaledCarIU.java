package com.kagan.starter.dto.saledCar;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoSaledCarIU {
    @NotNull
    private Long customerId;

    @NotNull
    private Long galleristId;

    @NotNull
    private Long carId;
}
