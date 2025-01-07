package com.FuelBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "fuel_station_register")
public class FuelStationRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Location is required")
    private String location;

    @NotNull(message = "Station name is required")
    @Size(min = 3, max = 50, message = "Station name must be between 3 and 50 characters")
    private String stationName;

    @NotNull(message = "license number is required")
    private String lisenceNumber;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Contact number is required")

    private String contactNumber;


    // Getter and Setter for id
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Getter and Setter for stationName
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for contactNumber
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLisenceNumber() {
        return lisenceNumber;
    }

    public void setLisenceNumber(String lisenceNumber) {
        this.lisenceNumber = lisenceNumber;
    }

    @Override
    public String toString() {
        return "FuelStationRegister{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", stationName='" + stationName + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", lisenceNumber='" + lisenceNumber + '\'' +
                '}';
    }
}
