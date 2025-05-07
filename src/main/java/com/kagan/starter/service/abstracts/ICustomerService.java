package com.kagan.starter.service.abstracts;

import com.kagan.starter.dto.customer.DtoCustomer;
import com.kagan.starter.dto.customer.DtoCustomerIU;

import java.util.List;

public interface ICustomerService {

    public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU);
    public List<DtoCustomer> getAllCustomer();
    public DtoCustomer getByCustomerId(Long id);
    public DtoCustomer updateCustomer(DtoCustomerIU dtoCustomerIU,Long id);
    public void deleteCustomer(Long id);
}
