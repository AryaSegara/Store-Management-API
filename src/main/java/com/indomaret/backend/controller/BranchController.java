package com.indomaret.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.indomaret.backend.dto.BranchDTO;
import com.indomaret.backend.payload.GenericResponse;
import com.indomaret.backend.service.branch.BranchService;


@RestController
@RequestMapping("/api/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public GenericResponse<List<BranchDTO>> getAllBranches() {
        return branchService.getAllBranches();
    }

    @PostMapping
    public GenericResponse<BranchDTO> createBranch(@RequestBody BranchDTO dto) {
        return branchService.createBranch(dto);
    }

    @PutMapping("/{id}")
    public GenericResponse<BranchDTO> updateBranch(@PathVariable Long id, @RequestBody BranchDTO dto) {
        return branchService.updateBranch(id, dto);
    }

    @DeleteMapping("/{id}")
    public GenericResponse<String> deleteBranch(@PathVariable Long id) {
        return branchService.deleteBranch(id);
    }
}
