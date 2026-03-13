package com.welltower.propertymanagement.controller;

import com.welltower.propertymanagement.dto.ManagerDTO;
import com.welltower.propertymanagement.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/managers")
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping
    public ResponseEntity<ManagerDTO> addManager(@RequestBody ManagerDTO managerDTO) {
        ManagerDTO createdManager = managerService.addManager(managerDTO);
        return new ResponseEntity<>(createdManager, HttpStatus.CREATED);
    }

    @GetMapping("/{managerId}")
    public ResponseEntity<ManagerDTO> getManager(@PathVariable Long managerId) {
        ManagerDTO manager = managerService.getManager(managerId);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ManagerDTO>> getAllManagers() {
        List<ManagerDTO> managers = managerService.getAllManagers();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @PutMapping("/{managerId}")
    public ResponseEntity<ManagerDTO> updateManager(@PathVariable Long managerId, @RequestBody ManagerDTO managerDTO) {
        ManagerDTO updatedManager = managerService.updateManager(managerId, managerDTO);
        return new ResponseEntity<>(updatedManager, HttpStatus.OK);
    }

    @DeleteMapping("/{managerId}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long managerId) {
        managerService.deleteManager(managerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}