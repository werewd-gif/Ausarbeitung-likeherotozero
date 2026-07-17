package com.likeherotozero.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.likeherotozero.model.Emission;
import com.likeherotozero.repository.EmissionRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(EmissionRepository repository) {
        	return args -> {
        		// Seed nur einmal: wenn noch keine Daten existieren
                if (repository.count() > 0) {
                    return;
                }
        	    Emission de2022 = new Emission("DE", "Germany", 2022, 673000);
        	    de2022.setApproved(true);
        	    repository.save(de2022);

        	    Emission de2023 = new Emission("DE", "Germany", 2023, 652000);
        	    de2023.setApproved(true);
        	    repository.save(de2023);

        	    Emission fr2023 = new Emission("FR", "France", 2023, 304000);
        	    fr2023.setApproved(true);
        	    repository.save(fr2023);
        	};
    }
}
