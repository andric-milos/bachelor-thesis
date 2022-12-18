package rs.ac.uns.ftn.bachelor_thesis.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.repository.SportsFacilityRepository;

@Service
public class SportsFacilityService {
    private SportsFacilityRepository sportsFacilityRepository;

    public SportsFacilityService(SportsFacilityRepository sportsFacilityRepository) {
        this.sportsFacilityRepository = sportsFacilityRepository;
    }
}
