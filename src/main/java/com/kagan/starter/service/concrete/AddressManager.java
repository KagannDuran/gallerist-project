package com.kagan.starter.service.concrete;

import com.kagan.starter.dto.address.DtoAddress;
import com.kagan.starter.dto.address.DtoAddressUI;
import com.kagan.starter.entity.Address;
import com.kagan.starter.exception.BaseException;
import com.kagan.starter.exception.ErrorMessage;
import com.kagan.starter.exception.MessageType;
import com.kagan.starter.mapper.AddressMapper;
import com.kagan.starter.repository.AddressRepository;
import com.kagan.starter.service.abstracts.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressManager implements IAddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressManager(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public DtoAddress saveAddress(DtoAddressUI dtoAddressUI) {
        Address address = addressMapper.AddressDtoIUtoEntity(dtoAddressUI);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.EntitytoDtoAddress(savedAddress);
    }

    @Override
    public DtoAddress getAddressById(Long id) {
    Address address = addressRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));

        return addressMapper.EntitytoDtoAddress(address);

    }

    @Override
    public List<DtoAddress> getListAddress() {
        List<Address> allAddress = addressRepository.findAll();



        return allAddress.stream().map(address -> addressMapper.EntitytoDtoAddress(address)).collect(Collectors.toList());
    }


}
