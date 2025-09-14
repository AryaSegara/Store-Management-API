package com.indomaret.backend.service.province;

import java.util.List;

import com.indomaret.backend.dto.ProvinceDTO;
import com.indomaret.backend.payload.GenericResponse;

public interface ProvinceService {

    GenericResponse<List<ProvinceDTO>> getAllProvinces();
    GenericResponse<ProvinceDTO> createProvince(ProvinceDTO dto);
    GenericResponse<ProvinceDTO> updateProvince(Long id, ProvinceDTO dto);
    GenericResponse<String> deleteProvince(Long id);

}
