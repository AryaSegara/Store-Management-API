package com.indomaret.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.indomaret.backend.dto.ProvinceDTO;
import com.indomaret.backend.payload.GenericResponse;
import com.indomaret.backend.service.province.ProvinceService;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping
    public GenericResponse<List<ProvinceDTO>> getAllProvinces() {
        return provinceService.getAllProvinces();
    }

    @PostMapping
    public GenericResponse<ProvinceDTO> createProvince(@RequestBody ProvinceDTO dto) {
        return provinceService.createProvince(dto);
    }

    @PutMapping("/{id}")
    public GenericResponse<ProvinceDTO> updateProvince(@PathVariable Long id, @RequestBody ProvinceDTO dto) {
        return provinceService.updateProvince(id, dto);
    }

    @DeleteMapping("/{id}")
    public GenericResponse<String> deleteProvince(@PathVariable Long id) {
        return provinceService.deleteProvince(id);
    }
}
