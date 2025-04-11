package com.elingenio.Proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.elingenio.Proyecto.Modelo.CustomerData;

public interface CustomerDataRepository extends JpaRepository<CustomerData, Long> {
}