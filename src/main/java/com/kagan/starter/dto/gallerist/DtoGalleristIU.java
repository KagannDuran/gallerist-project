package com.kagan.starter.dto.gallerist;

import com.kagan.starter.dto.address.DtoAddress;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoGalleristIU {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Long addressId;
}
