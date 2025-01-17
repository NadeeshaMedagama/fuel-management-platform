package com.FuelBackend.service.vehicalService;

import com.FuelBackend.dataTransferObject.request.vehicleRequestDTO.VehicleRequestDTO;
import com.FuelBackend.dataTransferObject.response.CustomApiResponse;
import com.FuelBackend.dataTransferObject.response.vehicleResponseDTO.VehicleResponseDTO;
import com.FuelBackend.entity.*;
import com.FuelBackend.enums.OwnerType;
import com.FuelBackend.exception.NotFoundException;
import com.FuelBackend.repositoryDAO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VehicleService implements VehicleServiceRepository{

    private final VehicleRepository vehicleRepository;

    private final VehicleClassesRepository vehicleClassesRepository;

    private final UserRepository userRepository;

    private final FuelRepository fuelRepository;

    private final BusinessGovernmentRepository businessGovernmentRepository;

    @Autowired
    public VehicleService(
            VehicleRepository vehicleRepository,
            VehicleClassesRepository vehicleClassesRepository,
            UserRepository userRepository, FuelRepository fuelRepository, BusinessGovernmentRepository businessGovernmentRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleClassesRepository = vehicleClassesRepository;
        this.userRepository = userRepository;
        this.fuelRepository = fuelRepository;
        this.businessGovernmentRepository = businessGovernmentRepository;
    }

    @Override
    public ResponseEntity<?> createVehicle(VehicleRequestDTO vehicleRequestDTO) {
        User user = userRepository.findById(vehicleRequestDTO.getOwnerId()).orElseThrow(
                () -> new NotFoundException("userId not found")
        );
        VehicleClasses vehicleClass = vehicleClassesRepository.findById(vehicleRequestDTO.getVehicleClassId())
                .orElseThrow(
                        () -> new NotFoundException("vehicleClassId not found")
                );
        Fuel fuel = fuelRepository.findById(vehicleRequestDTO.getFuelId()).orElseThrow(
                () -> new NotFoundException("fuel id is not found")
        );
        Vehicle vehicle = new Vehicle(
                vehicleRequestDTO.getVehicleRegisterId(),
                vehicleRequestDTO.getVehicleEngineNo(),
                vehicleRequestDTO.getModel(),
                vehicleRequestDTO.getYearOfManufacture(),
                OwnerType.User,
                vehicleRequestDTO.getOwnerId(),
                vehicleClass,
                fuel
        );

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        VehicleResponseDTO responseDTO = new VehicleResponseDTO(
                savedVehicle.getVehicleId(),
                savedVehicle.getVehicleRegisterId(),
                savedVehicle.getVehicleEngineNo(),
                savedVehicle.getModel(),
                savedVehicle.getYearOfManufacture(),
                savedVehicle.getCurrentFuelCapacity(),
                savedVehicle.getOwnerId(),
                savedVehicle.getVehicleClasses().getVehicleClassId(),
                savedVehicle.getFuel().getFuelId()
        );
        return new ResponseEntity<>(
                new CustomApiResponse(
                        HttpStatus.CREATED.value(),
                        "vehicle created successfully",
                        responseDTO
                ),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<?> createBusinessGovVehicle(VehicleRequestDTO vehicleRequestDTO) {
        // check the business is already exists
        BusinessGovernment gov = businessGovernmentRepository.findById(vehicleRequestDTO.getOwnerId())
                .orElseThrow(
                        () -> new NotFoundException("businessGov not registered")
                );
        VehicleClasses vehicleClass = vehicleClassesRepository.findById(vehicleRequestDTO.getVehicleClassId())
                .orElseThrow(
                        () -> new NotFoundException("vehicleClassId not found")
                );
        Fuel fuel = fuelRepository.findById(vehicleRequestDTO.getFuelId()).orElseThrow(
                () -> new NotFoundException("fuel id is not found")
        );

        Vehicle vehicle = new Vehicle(
                vehicleRequestDTO.getVehicleRegisterId(),
                vehicleRequestDTO.getVehicleEngineNo(),
                vehicleRequestDTO.getModel(),
                vehicleRequestDTO.getYearOfManufacture(),
                OwnerType.BusinessGovUser,
                vehicleRequestDTO.getOwnerId(),
                vehicleClass,
                fuel
        );

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return new ResponseEntity<>(
                new CustomApiResponse(
                        HttpStatus.OK.value(),
                        "businessGov vehicle created successfully",
                        new VehicleResponseDTO(
                                savedVehicle.getVehicleId(),
                                savedVehicle.getVehicleRegisterId(),
                                savedVehicle.getVehicleEngineNo(),
                                savedVehicle.getModel(),
                                savedVehicle.getYearOfManufacture(),
                                savedVehicle.getCurrentFuelCapacity(),
                                savedVehicle.getOwnerId(),
                                savedVehicle.getVehicleClasses().getVehicleClassId(),
                                savedVehicle.getFuel().getFuelId()
                        )
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> findVehicleById(UUID vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new NotFoundException("vehicle not found")
        );
        VehicleResponseDTO responseDTO = new VehicleResponseDTO(
                vehicle.getVehicleId(),
                vehicle.getVehicleRegisterId(),
                vehicle.getVehicleEngineNo(),
                vehicle.getModel(),
                vehicle.getYearOfManufacture(),
                vehicle.getCurrentFuelCapacity(),
                vehicle.getOwnerId(),
                vehicle.getVehicleClasses().getVehicleClassId(),
                vehicle.getFuel().getFuelId()
        );
        return new ResponseEntity<>(
                new CustomApiResponse(
                        HttpStatus.OK.value(),
                        null,
                        responseDTO
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> updateVehicleCurrentFuelCapacity(UUID vehicleId, Double fuelCapacity) {
        return new ResponseEntity<>("not implemented", HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<?> getAllVehicle() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<VehicleResponseDTO> responseList = new ArrayList<>();

        vehicles.forEach(
                vehicle -> {
                    responseList.add(
                            new VehicleResponseDTO(
                                    vehicle.getVehicleId(),
                                    vehicle.getVehicleRegisterId(),
                                    vehicle.getVehicleEngineNo(),
                                    vehicle.getModel(),
                                    vehicle.getYearOfManufacture(),
                                    vehicle.getCurrentFuelCapacity(),
                                    vehicle.getOwnerId(),
                                    vehicle.getVehicleClasses().getVehicleClassId(),
                                    vehicle.getFuel().getFuelId()
                            )
                    );
                }
        );
        return new ResponseEntity<>(
                new CustomApiResponse(
                        HttpStatus.OK.value(),
                        null,
                        responseList
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> updateVehicle(VehicleRequestDTO vehicleRequestDTO) {
        return new ResponseEntity<>("not implemented", HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<?> deleteVehicle(UUID vehicleId) {
        return new ResponseEntity<>("not implemented", HttpStatus.NOT_IMPLEMENTED);
    }
}
