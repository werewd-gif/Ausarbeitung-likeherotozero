package com.likeherotozero.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;


import org.springframework.data.jpa.repository.JpaRepository;

import com.likeherotozero.model.Emission;

public interface EmissionRepository extends JpaRepository<Emission, Long> {

	    Optional<Emission> findTopByCountryCodeOrderByEmissionsYearDesc(String countryCode);

	    Optional<Emission> findTopByCountryCodeAndApprovedTrueOrderByEmissionsYearDesc(String countryCode);

	    List<Emission> findByCountryCodeOrderByEmissionsYearAsc(String countryCode);

	    List<Emission> findByCountryCodeAndApprovedTrueOrderByEmissionsYearAsc(String countryCode);

	    Optional<Emission> findById(Long id);

	    @Query("""
	        SELECT e
	        FROM Emission e
	        WHERE e.emissionsYear = (
	            SELECT MAX(e2.emissionsYear)
	            FROM Emission e2
	            WHERE e2.countryCode = e.countryCode
	        )
	        ORDER BY e.co2EmissionsKt DESC
	    """)
	    List<Emission> findLatestEmissionsPerCountry();
	    List<Emission> findByApprovedTrue();
	    List<Emission> findByApprovedFalse();

	    @Query("""
	    	    SELECT e FROM Emission e
	    	    WHERE e.approved = true
	    	    AND e.emissionsYear = (
	    	        SELECT MAX(e2.emissionsYear)
	    	        FROM Emission e2
	    	        WHERE e2.countryCode = e.countryCode
	    	        AND e2.approved = true
	    	    )
	    	""")
	    List<Emission> findLatestApprovedPerCountry();
	    

}
