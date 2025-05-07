package com.kagan.starter.dto.gallerist;

import com.kagan.starter.dto.address.DtoAddress;
import com.kagan.starter.entity.Address;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoGallerist {
    private Long id;
    private String firstName;
    private String lastName;
    private Date createTime;
    private DtoAddress address;
}
