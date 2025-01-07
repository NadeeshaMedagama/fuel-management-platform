package com.FuelBackend.controller;

import com.FuelBackend.dataTransferObject.request.fuelStationRequestDTO.FuelStationRequestDTO;
import com.FuelBackend.entity.FuelStation;
import com.FuelBackend.service.FuelStationService.FuelStationService;
import com.FuelBackend.service.FuelStationService.FuelStationServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/fuelStation")
public class FuelStationController {

    private final FuelStationServiceRepository fuelStationServiceRepository;
    @Autowired
    private final FuelStationService fuelStationService;

    @Autowired
    public FuelStationController(FuelStationServiceRepository fuelStationServiceRepository, FuelStationService fuelStationService) {
        this.fuelStationServiceRepository = fuelStationServiceRepository;
        this.fuelStationService = fuelStationService;
    }

    @PostMapping
    public ResponseEntity<?> createFuelStation(@RequestBody FuelStationRequestDTO fuelStationRequestDTO){
        return fuelStationServiceRepository.createFuelStation(fuelStationRequestDTO);
    }

    @GetMapping("/{fuelStationId}")
    public ResponseEntity<?> getFuelStationById(@PathVariable UUID fuelStationId){
        return fuelStationServiceRepository.getFuelStationById(fuelStationId);
    }

    @PutMapping("/{fuelStationId}")
    public ResponseEntity<?> updateFuelStation(@PathVariable UUID fuelStationId,@RequestBody FuelStationRequestDTO fuelStationRequestDTO){
        return fuelStationServiceRepository.updateFuelStation(fuelStationId,fuelStationRequestDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllFuelStation(){
        return fuelStationServiceRepository.getAllFuelStation();
    }

    @DeleteMapping("/{fuelStationId}")
    public ResponseEntity<?> deleteFuelStation(@PathVariable UUID fuelStationId){
        return fuelStationServiceRepository.deleteFuelStation(fuelStationId);
    }

}
