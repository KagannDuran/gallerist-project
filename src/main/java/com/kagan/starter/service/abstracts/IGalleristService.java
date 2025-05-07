package com.kagan.starter.service.abstracts;

import com.kagan.starter.dto.gallerist.DtoGallerist;
import com.kagan.starter.dto.gallerist.DtoGalleristIU;

import java.util.List;

public interface IGalleristService {
    public DtoGallerist saveGallerist(DtoGalleristIU dtoGalleristIU);
    public DtoGallerist getGalleristById(Long id);
    public List<DtoGallerist> getAllGallerist();
    public DtoGallerist updateGallerist(DtoGalleristIU dtoGalleristIU,Long id);
    public void deleteGallerist(Long id);
}
