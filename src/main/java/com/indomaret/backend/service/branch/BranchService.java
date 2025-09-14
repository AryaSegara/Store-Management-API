package com.indomaret.backend.service.branch;

import java.util.List;

import com.indomaret.backend.dto.BranchDTO;
import com.indomaret.backend.payload.GenericResponse;

public interface BranchService {

    GenericResponse<List<BranchDTO>> getAllBranches() ;   GenericResponse<BranchDTO> createBranch(BranchDTO dto);
    GenericResponse<BranchDTO> updateBranch(Long id, BranchDTO dto);
    GenericResponse<String> deleteBranch(Long id);
}
