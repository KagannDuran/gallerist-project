package com.kagan.starter.dto.galleristcar;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoGalleristCarIU {
    @NotNull
    private Long galleristId;

    @NotNull
    private Long CarId;
}
