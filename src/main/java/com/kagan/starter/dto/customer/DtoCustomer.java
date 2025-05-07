package com.kagan.starter.dto.customer;

import com.kagan.starter.dto.account.AccountDto;
import com.kagan.starter.dto.address.DtoAddress;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCustomer {
    private Long id;

    private String firstName;

    private String lastName;

    private String tckn;

    private Date birthOfDate;

    private Date createTime;

    private DtoAddress address;

    private AccountDto account;

}
