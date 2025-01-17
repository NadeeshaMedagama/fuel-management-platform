package com.FuelBackend.controller;

import com.FuelBackend.dataTransferObject.request.businessGovernmentRequestDTO.BusinessGovernmentRequestDTO;
import com.FuelBackend.service.businessGovernmentService.BusinessGovernmentServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/businessGov")
public class BusinessGovernmentController {

    private final BusinessGovernmentServiceRepository businessGovernmentServiceRepository;

    @Autowired
    public BusinessGovernmentController(BusinessGovernmentServiceRepository businessGovernmentServiceRepository) {
        this.businessGovernmentServiceRepository = businessGovernmentServiceRepository;
    }

    @PostMapping
    public ResponseEntity<?> createBusinessGovernment(@RequestBody BusinessGovernmentRequestDTO businessGovernmentRequestDTO){
        return businessGovernmentServiceRepository.createBusinessGovernment(businessGovernmentRequestDTO);
    }

    @PutMapping("/{businessGovernmentId}")
    public ResponseEntity<?> verifyBusinessGovernmentMobile(
            @PathVariable Integer businessGovernmentId,
            @RequestBody Integer otp
    ){
        return businessGovernmentServiceRepository.verifyBusinessGovernmentMobile(businessGovernmentId,otp);
    }

    @GetMapping("/{businessGovernmentId}")
    public ResponseEntity<?> businessGovernmentFindById(@PathVariable Integer businessGovernmentId){
        return businessGovernmentServiceRepository.businessGovernmentFindById(businessGovernmentId);
    }

    @GetMapping
    public ResponseEntity<?> businessGovernmentGetAll(){
        return businessGovernmentServiceRepository.businessGovernmentGetAll();
    }
}
