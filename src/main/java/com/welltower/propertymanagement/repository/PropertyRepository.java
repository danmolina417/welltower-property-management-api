package com.welltower.propertymanagement.repository;

import com.welltower.propertymanagement.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Optional<Property> findByPropertyName(String propertyName);

    List<Property> findByIsActive(Boolean isActive);
}
