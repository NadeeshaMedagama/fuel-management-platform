package com.FuelBackend.service.fuelstationregistor;

import com.FuelBackend.entity.FuelStation;
import com.FuelBackend.entity.FuelStationRegister;
import com.FuelBackend.repositoryDAO.FuelStationRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class FuelStationRegisterService{
    @Autowired
    private final FuelStationRegisterRepository fuelStationRegisterRepository;

    public FuelStationRegisterService(FuelStationRegisterRepository fuelStationRegisterRepository) {
        this.fuelStationRegisterRepository = fuelStationRegisterRepository;
    }
    public FuelStationRegister registerFuelStation(FuelStationRegister fuelStationRegister) {


        return fuelStationRegisterRepository.save(fuelStationRegister);
    }
}
