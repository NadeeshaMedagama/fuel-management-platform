package com.FuelBackend.repositoryDAO;

import com.FuelBackend.entity.FuelStationRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelStationRegisterRepository extends JpaRepository<FuelStationRegister, Long> {
}

