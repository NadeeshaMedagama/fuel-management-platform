package com.FuelBackend.service.authenticationService;

import com.FuelBackend.dataTransferObject.request.loginRequestDTO.*;
import com.FuelBackend.dataTransferObject.response.LoginResponseDTO;
import com.FuelBackend.dataTransferObject.response.administratorResponseDTO.AdministratorResponseDTO;
import com.FuelBackend.dataTransferObject.response.businessGovernmentResponseDTO.BusinessGovernmentResponseDTO;
import com.FuelBackend.dataTransferObject.response.employeeResponseDTO.EmployeeResponseDTO;
import com.FuelBackend.dataTransferObject.response.fuelStationResponseDTO.FuelStationResponseDTO;
import com.FuelBackend.dataTransferObject.response.userResponseDTO.UserResponseDTO;
import com.FuelBackend.entity.*;
import com.FuelBackend.exception.UnauthorizedAccessException;
import com.FuelBackend.repositoryDAO.*;
import com.FuelBackend.utility.JwtUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationServiceRepository{

    private final UserRepository userRepository;

    private final EmployeeRepository employeeRepository;

    private final BusinessGovernmentRepository businessGovernmentRepository;

    private final FuelStationRepository fuelStationRepository;

    private final JwtUtility jwtUtility;

    private final AuthenticationManager authenticationManager;

    private final AdministratorRepository administratorRepository;


    public AuthenticationService(UserRepository userRepository, EmployeeRepository employeeRepository, BusinessGovernmentRepository businessGovernmentRepository, FuelStationRepository fuelStationRepository, JwtUtility jwtUtility, AuthenticationManager authenticationManager, AdministratorRepository administratorRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.businessGovernmentRepository = businessGovernmentRepository;
        this.fuelStationRepository = fuelStationRepository;
        this.jwtUtility = jwtUtility;
        this.authenticationManager = authenticationManager;
        this.administratorRepository = administratorRepository;
    }

    @Override
    public ResponseEntity<?> userLogin(UserLoginRequestDTO userLoginRequestDTO) {
        User user = userRepository.findByMobile(userLoginRequestDTO.getMobile()).orElseThrow(
                () -> new UnauthorizedAccessException("username or password incorrect")
        );

        String token= "";
        if (
                user != null &&
                    user.getVerifyMobile() &&
                        user.getPassword().equals(userLoginRequestDTO.getPassword())
        ) {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            userLoginRequestDTO.getMobile(),
//                            userLoginRequestDTO.getPassword()
//                    )
//            );
            // create a jwt token
            token = jwtUtility.generateToken(user.getMobile());
        } else {
            throw new UnauthorizedAccessException("username or password incorrect");
        }


        return new ResponseEntity<>(
                new LoginResponseDTO(
                        HttpStatus.OK.value(),
                        "user login successfully",
                        token,
                        new UserResponseDTO(
                                user.getUserId(),
                                user.getF_name(),
                                user.getL_name(),
                                user.getEmail(),
                                user.getMobile(),
                                user.getVerifyMobile()
                        )
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> employeeLogin(EmployeeLoginRequestDTO employeeLoginRequestDTO) {
        Employee employee = employeeRepository.findByEmployeeUsername(employeeLoginRequestDTO.getUsername())
                .orElseThrow(
                        () -> new UnauthorizedAccessException("username or password incorrect")
                );
        String token;
        if (
                employee != null &&
                        employee.getEmployeeStatus() &&
                            employee.getPassword().equals(employeeLoginRequestDTO.getPassword())
        ){

            token = jwtUtility.generateToken(employee.getEmployeeUsername());

        }else{
            throw new UnauthorizedAccessException("username or password incorrect");
        }
        return new ResponseEntity<>(
                new LoginResponseDTO(
                        HttpStatus.OK.value(),
                        "employee login successfully",
                        token,
                        new EmployeeResponseDTO(
                                employee.getEmployeeId(),
                                employee.getEmployeeUsername(),
                                employee.getEmployeeEmail(),
                                employee.getFuelStation().getFuelStationId(),
                                employee.getEmployeeStatus()
                        )
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> fuelStationLogin(FuelStationLoginRequestDTO fuelStationLoginRequestDTO) {
        FuelStation fuelStation = fuelStationRepository.findByFuelStationRegisterId(
                fuelStationLoginRequestDTO.getFuelStationRegisterId()
        ).orElseThrow(
                () -> new UnauthorizedAccessException("username or password incorrect")
        );
        String token;
        if (
                fuelStation != null &&
                        fuelStation.getPassword().equals(fuelStationLoginRequestDTO.getPassword())
        ){

            token = jwtUtility.generateToken(fuelStation.getFuelStationRegisterId());
        }else{
            throw new UnauthorizedAccessException("username or password incorrect");
        }
        return new ResponseEntity<>(
                new LoginResponseDTO(
                        HttpStatus.OK.value(),
                        "fuel station login successfully",
                        token,
                        new FuelStationResponseDTO(
                                fuelStation.getFuelStationId(),
                                fuelStation.getFuelStationRegisterId(),
                                fuelStation.getFuelStationOwnerName(),
                                fuelStation.getFuelStationEmail()
                        )
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> businessLogin(BusinessGovLoginRequestDTO businessGovLoginRequestDTO) {
        BusinessGovernment businessGovernment = businessGovernmentRepository.findByBusinessGovernmentRegNo(
                businessGovLoginRequestDTO.getBusinessGovernmentRegNo()
        ).orElseThrow(
                () -> new UnauthorizedAccessException("username or password incorrect")
        );
        String token;
        if(
                businessGovernment != null &&
                        businessGovernment.getMobileIsVerify() &&
                            businessGovernment.getPassword().equals(businessGovLoginRequestDTO.getPassword())
        ){

            token = jwtUtility.generateToken(businessGovernment.getBusinessGovernmentRegNo());
        }else{
            throw new UnauthorizedAccessException("username or password incorrect");
        }
        return new ResponseEntity<>(
                new LoginResponseDTO(
                        HttpStatus.OK.value(),
                        "businessGov login successfully",
                        token,
                        new BusinessGovernmentResponseDTO(
                                businessGovernment.getBusinessGovernmentId(),
                                businessGovernment.getBusinessGovernmentRegNo(),
                                businessGovernment.getEmail(),
                                businessGovernment.getMobile(),
                                businessGovernment.getMobileIsVerify()
                        )
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> administratorLogin(AdministratorLoginRequestDTO administratorLoginRequestDTO) {
        Administrator administrator = administratorRepository.findByAdministratorUsername(
                administratorLoginRequestDTO.getAdministratorUsername()
        ).orElseThrow(
                () -> new UnauthorizedAccessException("username or password incorrect")
        );
        String token;
        if (
                administrator != null &&
                        administrator.getPassword().equals(administratorLoginRequestDTO.getPassword())
        ){

            token = jwtUtility.generateToken(administrator.getAdministratorUsername());
        }else{
            throw new UnauthorizedAccessException("username or password incorrect");
        }
        return new ResponseEntity<>(
                new LoginResponseDTO(
                        HttpStatus.OK.value(),
                        "administrator login successfully",
                        token,
                        new AdministratorResponseDTO(
                                administrator.getAdministratorId(),
                                administrator.getAdministratorUsername(),
                                administrator.getAdministratorEmail()
                        )
                ),
                HttpStatus.OK
        );
    }
}