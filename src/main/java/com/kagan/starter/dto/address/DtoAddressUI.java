package com.kagan.starter.dto.address;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoAddressUI {

    @NotBlank
    private String city;

    @NotBlank
    private String  district;

    @NotBlank
    private String neighborhood;

    @NotBlank
    private String street;
}
