package com.indomaret.backend.service.province;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indomaret.backend.dto.ProvinceDTO;
import com.indomaret.backend.model.Province;
import com.indomaret.backend.payload.GenericResponse;
import com.indomaret.backend.repository.ProvinceRepository;


@Service
public class ProvinceServiceImpl implements ProvinceService{

    @Autowired
    private ProvinceRepository provinceRepository;

    private ProvinceDTO mapToDTO(Province province) {
        ProvinceDTO dto = new ProvinceDTO();
        dto.setId(province.getId());
        dto.setName(province.getName());
        dto.setActive(province.isActive());
        dto.setCreatedAt(province.getCreatedAt());
        dto.setUpdatedAt(province.getUpdatedAt());
        return dto;
    }

    @Override
    public GenericResponse<List<ProvinceDTO>> getAllProvinces() {
        List<ProvinceDTO> provinces = provinceRepository.findAllActive()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
        return GenericResponse.<List<ProvinceDTO>>builder()
                .success(true)
                .message("List of provinces")
                .data(provinces)
                .build();
    }

    @Override
    public GenericResponse<ProvinceDTO> createProvince(ProvinceDTO dto) {
        Province province = Province.builder()
                .name(dto.getName())
                .active(dto.isActive())
                .build();

        provinceRepository.save(province);

        return GenericResponse.<ProvinceDTO>builder()
                .success(true)
                .message("Province created successfully")
                .data(mapToDTO(province))
                .build();
    }

    @Override
    public GenericResponse<ProvinceDTO> updateProvince(Long id, ProvinceDTO dto) {
        Optional<Province> provinceOpt = provinceRepository.findById(id);
        if (provinceOpt.isEmpty()) {
            return GenericResponse.<ProvinceDTO>builder()
                    .success(false)
                    .message("Province not found")
                    .data(null)
                    .build();
        }

        Province province = provinceOpt.get();
        province.setName(dto.getName());
        province.setActive(dto.isActive());
        province.setUpdatedAt(LocalDateTime.now());

        provinceRepository.save(province);

        return GenericResponse.<ProvinceDTO>builder()
                .success(true)
                .message("Province updated successfully")
                .data(mapToDTO(province))
                .build();
    }

    @Override
    public GenericResponse<String> deleteProvince(Long id) {
        Optional<Province> provinceOpt = provinceRepository.findById(id);
        if (provinceOpt.isEmpty()) {
            return GenericResponse.<String>builder()
                    .success(false)
                    .message("Province not found")
                    .data(null)
                    .build();
        }

        Province province = provinceOpt.get();
        province.setDeleted(true);
        province.setUpdatedAt(LocalDateTime.now());

        provinceRepository.save(province);

        return GenericResponse.<String>builder()
                .success(true)
                .message("Province deleted successfully")
                .data("Deleted ID: " + id )
                .build();
    }
}
