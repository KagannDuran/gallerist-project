package com.kagan.starter.service.abstracts;

import com.kagan.starter.dto.address.DtoAddress;
import com.kagan.starter.dto.address.DtoAddressUI;

import java.util.List;

public interface IAddressService {

    public DtoAddress saveAddress(DtoAddressUI dtoAddressUI);
    public DtoAddress getAddressById(Long id);
    public List<DtoAddress> getListAddress();
}
