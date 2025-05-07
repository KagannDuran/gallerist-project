package com.kagan.starter.service.concrete;

import com.kagan.starter.dto.address.DtoAddress;
import com.kagan.starter.dto.customer.DtoCustomer;
import com.kagan.starter.dto.customer.DtoCustomerIU;
import com.kagan.starter.entity.Account;
import com.kagan.starter.entity.Address;
import com.kagan.starter.entity.Customer;
import com.kagan.starter.exception.BaseException;
import com.kagan.starter.exception.ErrorMessage;
import com.kagan.starter.exception.MessageType;
import com.kagan.starter.mapper.CustomerMapper;
import com.kagan.starter.repository.AccountRepository;
import com.kagan.starter.repository.AddressRepository;
import com.kagan.starter.repository.CustomerRepository;
import com.kagan.starter.service.abstracts.IAccountService;
import com.kagan.starter.service.abstracts.IAddressService;
import com.kagan.starter.service.abstracts.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final AccountRepository accountRepository;
    private final CustomerMapper customerMapper;

    public CustomerManager(CustomerRepository customerRepository, AddressRepository addressRepository, AccountRepository accountRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.accountRepository = accountRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU) {
        Address address = addressRepository.findById(dtoCustomerIU.getAddressId())  // dtoCustomerIU'dan AddressId alıyoruz
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoCustomerIU.getAddressId().toString())));

        Account account = accountRepository.findById(dtoCustomerIU.getAccountId())  // dtoCustomerIU'dan AccountId alıyoruz
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoCustomerIU.getAccountId().toString())));

        Customer customer = customerMapper.iuToEntity(dtoCustomerIU);
        customer.setAddress(address);
        customer.setAccount(account);

        return customerMapper.toDto(customerRepository.save(customer));
    }

    @Override
    public List<DtoCustomer> getAllCustomer() {
        return customerRepository.findAll().stream().map(customerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public DtoCustomer getByCustomerId(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));
        return customerMapper.toDto(customer);
    }

    @Override
    public DtoCustomer updateCustomer(DtoCustomerIU dtoCustomerIU, Long id) {
        // Address ve Account ID'leri kullanılarak ilgili entity'leri alıyoruz
        Address address = addressRepository.findById(dtoCustomerIU.getAddressId())  // dtoCustomerIU'dan AddressId alıyoruz
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoCustomerIU.getAddressId().toString())));

        Account account = accountRepository.findById(dtoCustomerIU.getAccountId())  // dtoCustomerIU'dan AccountId alıyoruz
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoCustomerIU.getAccountId().toString())));

        Customer customer =customerRepository.findById(id)
                .orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));

        customerMapper.updateCustomerFromDto(dtoCustomerIU,customer);

        customer.setAccount(account);
        customer.setAddress(address);

        return customerMapper.toDto(customerRepository.save(customer));


    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));
        customerRepository.delete(customer);
    }
}
