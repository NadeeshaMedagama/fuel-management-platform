package com.FuelBackend.service.FuelStationService;

import com.FuelBackend.dataTransferObject.request.fuelStationRequestDTO.FuelStationRequestDTO;
import com.FuelBackend.dataTransferObject.response.CustomApiResponse;
import com.FuelBackend.dataTransferObject.response.fuelStationResponseDTO.FuelStationResponseDTO;
import com.FuelBackend.entity.FuelStation;
import com.FuelBackend.exception.NotFoundException;
import com.FuelBackend.repositoryDAO.FuelStationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FuelStationService implements FuelStationServiceRepository{

    private final FuelStationRepository fuelStationRepository;

    public FuelStationService(FuelStationRepository fuelStationRepository) {
        this.fuelStationRepository = fuelStationRepository;
    }

    @Override
    public ResponseEntity<?> createFuelStation(FuelStationRequestDTO fuelStationRequestDTO) {
        FuelStation fuelStation = new FuelStation(
                fuelStationRequestDTO.getFuelStationRegisterId(),
                fuelStationRequestDTO.getFuelStationOwnerName(),
                fuelStationRequestDTO.getFuelStationEmail(),
                fuelStationRequestDTO.getPassword()
        );

        FuelStation savedFuelStation = fuelStationRepository.save(fuelStation);

        FuelStationResponseDTO responseDTO = new FuelStationResponseDTO(
                savedFuelStation.getFuelStationId(),
                savedFuelStation.getFuelStationRegisterId(),
                savedFuelStation.getFuelStationOwnerName(),
                savedFuelStation.getFuelStationEmail()
        );
        return new ResponseEntity<>(
                new CustomApiResponse(
                        HttpStatus.CREATED.value(),
                        "fuel station created successfully",
                        responseDTO
                ),
                HttpStatus.CREATED
        );
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateFuelStation(UUID fuelStationId,FuelStationRequestDTO fuelStationRequestDTO) {
        FuelStation fuelStation = fuelStationRepository.findById(fuelStationId).orElseThrow(
                () -> new NotFoundException("fuel station not found")
        );
        fuelStation.setFuelStationOwnerName(fuelStationRequestDTO.getFuelStationOwnerName());
        fuelStation.setFuelStationEmail(fuelStationRequestDTO.getFuelStationEmail());
        fuelStation.setPassword(fuelStationRequestDTO.getPassword());
        fuelStationRepository.save(fuelStation);

        FuelStationResponseDTO responseDTO = new FuelStationResponseDTO(
                fuelStation.getFuelStationId(),
                fuelStation.getFuelStationRegisterId(),
                fuelStation.getFuelStationOwnerName(),
                fuelStation.getFuelStationEmail()
        );
        return new ResponseEntity<>(
                new CustomApiResponse(
                        HttpStatus.OK.value(),
                        "fuel station updated successfully",
                        responseDTO
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getFuelStationById(UUID fuelStationId) {
        FuelStation fuelStation = fuelStationRepository.findById(fuelStationId).orElseThrow(
                () -> new NotFoundException("fuel station not found")
        );

        FuelStationResponseDTO responseDTO = new FuelStationResponseDTO(
                fuelStation.getFuelStationId(),
                fuelStation.getFuelStationRegisterId(),
                fuelStation.getFuelStationOwnerName(),
                fuelStation.getFuelStationEmail()
        );
        return new ResponseEntity<>(
                new CustomApiResponse(
                        HttpStatus.OK.value(),
                        "fuel station updated successfully",
                        responseDTO
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getAllFuelStation() {
        List<FuelStation> fuelStations = fuelStationRepository.findAll();
        List<FuelStationResponseDTO> responseDTOList = new ArrayList<>();

        fuelStations.forEach(
                fuelStation -> {
                    responseDTOList.add(
                            new FuelStationResponseDTO(
                                    fuelStation.getFuelStationId(),
                                    fuelStation.getFuelStationRegisterId(),
                                    fuelStation.getFuelStationOwnerName(),
                                    fuelStation.getFuelStationEmail()
                            )
                    );
                }
        );
        return new ResponseEntity<>(
                new CustomApiResponse(
                        HttpStatus.OK.value(),
                        null,
                        responseDTOList
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> deleteFuelStation(UUID fuelStationId) {
        return new ResponseEntity<>("not implemented",HttpStatus.NOT_IMPLEMENTED);
    }

}
