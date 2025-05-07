package com.kagan.starter.service.concrete;

import com.kagan.starter.dto.gallerist.DtoGallerist;
import com.kagan.starter.dto.gallerist.DtoGalleristIU;
import com.kagan.starter.entity.Address;
import com.kagan.starter.entity.Customer;
import com.kagan.starter.entity.Gallerist;
import com.kagan.starter.exception.BaseException;
import com.kagan.starter.exception.ErrorMessage;
import com.kagan.starter.exception.MessageType;
import com.kagan.starter.mapper.GalleristMapper;
import com.kagan.starter.repository.AddressRepository;
import com.kagan.starter.repository.GalleristRepository;
import com.kagan.starter.service.abstracts.IGalleristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GalleristManager implements IGalleristService {

    private final GalleristRepository galleristRepository;
    private final AddressRepository addressRepository;
    private final GalleristMapper galleristMapper;

    public GalleristManager(GalleristRepository galleristRepository, AddressRepository addressRepository, GalleristMapper galleristMapper) {
        this.galleristRepository = galleristRepository;
        this.addressRepository = addressRepository;
        this.galleristMapper = galleristMapper;
    }

    @Override
    public DtoGallerist saveGallerist(DtoGalleristIU dtoGalleristIU) {
        Address address = addressRepository.findById(dtoGalleristIU.getAddressId())
                .orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoGalleristIU.getAddressId().toString())));

        Gallerist gallerist = galleristMapper.dtoGalleristIUtoEntity(dtoGalleristIU);
        gallerist.setAddress(address);

        return galleristMapper.entityToDtoGallerist(galleristRepository.save(gallerist));
    }

    @Override
    public DtoGallerist getGalleristById(Long id) {

        Gallerist gallerist = galleristRepository.findById(id)
                .orElseThrow(() ->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));

        return galleristMapper.entityToDtoGallerist(gallerist);
    }

    @Override
    public List<DtoGallerist> getAllGallerist() {
        List<Gallerist> gallerists = galleristRepository.findAll();
        return gallerists.stream().map(galleristMapper::entityToDtoGallerist).collect(Collectors.toList());
    }

    @Override
    public DtoGallerist updateGallerist(DtoGalleristIU dtoGalleristIU, Long id) {

        Address address = addressRepository.findById(dtoGalleristIU.getAddressId())
                .orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoGalleristIU.getAddressId().toString())));

        Gallerist gallerist = galleristRepository.findById(id)
                .orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));

        galleristMapper.updateGalleristFromDto(dtoGalleristIU,gallerist);

        gallerist.setAddress(address);

        return galleristMapper.entityToDtoGallerist(galleristRepository.save(gallerist));
    }

    @Override
    public void deleteGallerist(Long id) {
        Gallerist gallerist = galleristRepository.findById(id)
                .orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));

        galleristRepository.delete(gallerist);
    }
}
