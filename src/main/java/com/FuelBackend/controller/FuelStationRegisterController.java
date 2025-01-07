package com.FuelBackend.controller;

import com.FuelBackend.entity.FuelStation;
import com.FuelBackend.entity.FuelStationRegister;
import com.FuelBackend.repositoryDAO.FuelStationRegisterRepository;
import com.FuelBackend.service.fuelstationregistor.FuelStationRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FuelStationRegisterController {
private final FuelStationRegisterService fuelStationRegisterService;

    public FuelStationRegisterController(FuelStationRegisterService fuelStationRegisterService) {
        this.fuelStationRegisterService = fuelStationRegisterService;
    }
@Autowired
    FuelStationRegisterRepository fuelStationRegisterRepository;
    @CrossOrigin(origins = "http://localhost:5174")
    @PostMapping("/register")
    public ResponseEntity<FuelStationRegister> registerStation(@RequestBody FuelStationRegister fuelStationRegister) {

        FuelStationRegister registeredStation= fuelStationRegisterService.registerFuelStation(fuelStationRegister);
        return new ResponseEntity<>(registeredStation, HttpStatus.CREATED);
    }
    @GetMapping("/test")
    public ResponseEntity<List<FuelStationRegister>> testConnection() {
        return new ResponseEntity<>(fuelStationRegisterRepository.findAll(), HttpStatus.OK);
}}
