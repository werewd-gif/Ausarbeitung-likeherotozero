package com.likeherotozero.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Emission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String countryCode;
    private String countryName;

    @Column(name = "emissions_year")
    private Integer emissionsYear;


    private double co2EmissionsKt;

    // 🔹 Pflicht-Konstruktor für JPA
    protected Emission() {
    }

    public Emission(String countryCode, String countryName, int emissionsYear, double co2EmissionsKt) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.emissionsYear = emissionsYear;
        this.co2EmissionsKt = co2EmissionsKt;
        this.approved = false; // neue Daten sind zunächst nicht freigegeben
    }

    // Getter

    public Long getId() {
        return id;
    }
    public boolean isApproved() {
        return approved;
    }
    public void setApproved(boolean approved) {
        this.approved = approved;
    }



    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getEmissionsYear() {
        return emissionsYear;
    }

    public double getCo2EmissionsKt() {
        return co2EmissionsKt;
    }
    
    @Column(nullable = false)
    private boolean approved = true;
 
}
