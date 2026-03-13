package com.welltower.propertymanagement.service;

import com.welltower.propertymanagement.dto.ManagerDTO;
import com.welltower.propertymanagement.model.Manager;
import com.welltower.propertymanagement.repository.ManagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public ManagerDTO addManager(ManagerDTO managerDTO) {
        if (managerRepository.existsByEmail(managerDTO.getEmail())) {
            throw new IllegalArgumentException("Manager with email " + managerDTO.getEmail() + " already exists");
        }

        Manager manager = new Manager();
        manager.setFirstName(managerDTO.getFirstName());
        manager.setLastName(managerDTO.getLastName());
        manager.setEmail(managerDTO.getEmail());
        manager.setPhoneNumber(managerDTO.getPhoneNumber());

        Manager savedManager = managerRepository.save(manager);
        return convertToDTO(savedManager);
    }

    public ManagerDTO getManager(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found with id: " + managerId));
        return convertToDTO(manager);
    }

    public List<ManagerDTO> getAllManagers() {
        return managerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ManagerDTO updateManager(Long managerId, ManagerDTO managerDTO) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found with id: " + managerId));

        // Check if email is being changed and if it's already taken
        if (!manager.getEmail().equals(managerDTO.getEmail()) &&
            managerRepository.existsByEmail(managerDTO.getEmail())) {
            throw new IllegalArgumentException("Manager with email " + managerDTO.getEmail() + " already exists");
        }

        manager.setFirstName(managerDTO.getFirstName());
        manager.setLastName(managerDTO.getLastName());
        manager.setEmail(managerDTO.getEmail());
        manager.setPhoneNumber(managerDTO.getPhoneNumber());

        Manager updatedManager = managerRepository.save(manager);
        return convertToDTO(updatedManager);
    }

    public void deleteManager(Long managerId) {
        if (!managerRepository.existsById(managerId)) {
            throw new RuntimeException("Manager not found with id: " + managerId);
        }
        managerRepository.deleteById(managerId);
    }

    private ManagerDTO convertToDTO(Manager manager) {
        ManagerDTO dto = new ManagerDTO();
        dto.setId(manager.getId());
        dto.setFirstName(manager.getFirstName());
        dto.setLastName(manager.getLastName());
        dto.setEmail(manager.getEmail());
        dto.setPhoneNumber(manager.getPhoneNumber());
        dto.setCreatedAt(manager.getCreatedAt());
        dto.setUpdatedAt(manager.getUpdatedAt());
        return dto;
    }
}