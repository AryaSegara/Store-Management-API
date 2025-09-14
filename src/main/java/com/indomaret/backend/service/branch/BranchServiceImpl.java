package com.indomaret.backend.service.branch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indomaret.backend.dto.BranchDTO;
import com.indomaret.backend.model.Branch;
import com.indomaret.backend.model.Province;
import com.indomaret.backend.payload.GenericResponse;
import com.indomaret.backend.repository.BranchRepository;
import com.indomaret.backend.repository.ProvinceRepository;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    private BranchDTO mapToDTO(Branch branch) {
        return BranchDTO.builder()
                .id(branch.getId())
                .name(branch.getName())
                .provinceId(branch.getProvince().getId())
                .active(branch.isActive())
                .createdAt(branch.getCreatedAt())
                .updatedAt(branch.getUpdatedAt())
                .build();
    }

    @Override
    public GenericResponse<List<BranchDTO>> getAllBranches() {
        List<BranchDTO> branches = branchRepository.findAllActive()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return GenericResponse.<List<BranchDTO>>builder()
                .success(true)
                .message("List of branches")
                .data(branches)
                .build();
    }

    @Override
    public GenericResponse<BranchDTO> createBranch(BranchDTO dto) {
        Optional<Province> provinceOpt = provinceRepository.findById(dto.getProvinceId());
        if (provinceOpt.isEmpty()) {
            return GenericResponse.<BranchDTO>builder()
                    .success(false)
                    .message("Province not found")
                    .data(null)
                    .build();
        }

        Branch branch = Branch.builder()
                .name(dto.getName())
                .province(provinceOpt.get())
                .active(dto.isActive())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        branchRepository.save(branch);

        return GenericResponse.<BranchDTO>builder()
                .success(true)
                .message("Branch created successfully")
                .data(mapToDTO(branch))
                .build();
    }

    @Override
    public GenericResponse<BranchDTO> updateBranch(Long id, BranchDTO dto) {
        Optional<Branch> branchOpt = branchRepository.findById(id);
        if (branchOpt.isEmpty()) {
            return GenericResponse.<BranchDTO>builder()
                    .success(false)
                    .message("Branch not found")
                    .data(null)
                    .build();
        }

        Branch branch = branchOpt.get();
        branch.setName(dto.getName());
        branch.setActive(dto.isActive());
        branch.setUpdatedAt(LocalDateTime.now());

        branchRepository.save(branch);

        return GenericResponse.<BranchDTO>builder()
                .success(true)
                .message("Branch updated successfully")
                .data(mapToDTO(branch))
                .build();
    }

    @Override
    public GenericResponse<String> deleteBranch(Long id) {
        Optional<Branch> branchOpt = branchRepository.findById(id);
        if (branchOpt.isEmpty()) {
            return GenericResponse.<String>builder()
                    .success(false)
                    .message("Branch not found")
                    .data(null)
                    .build();
        }

        Branch branch = branchOpt.get();
        branch.setDeleted(true);
        branch.setUpdatedAt(LocalDateTime.now());
        branchRepository.save(branch);

        return GenericResponse.<String>builder()
                .success(true)
                .message("Branch deleted successfully")
                .data("Deleted ID: " + id + " at " + branch.getUpdatedAt())
                .build();
    }

}
