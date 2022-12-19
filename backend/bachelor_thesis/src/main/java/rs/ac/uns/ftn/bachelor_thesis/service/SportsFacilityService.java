package rs.ac.uns.ftn.bachelor_thesis.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.SportsFacilityDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Location;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;
import rs.ac.uns.ftn.bachelor_thesis.model.SportsFacility;
import rs.ac.uns.ftn.bachelor_thesis.repository.ManagerRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.SportsFacilityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SportsFacilityService {
    private ManagerRepository managerRepository;
    private SportsFacilityRepository sportsFacilityRepository;

    public SportsFacilityService(ManagerRepository managerRepository,
                                 SportsFacilityRepository sportsFacilityRepository) {
        this.managerRepository = managerRepository;
        this.sportsFacilityRepository = sportsFacilityRepository;
    }

    public SportsFacility createSportsFacility(SportsFacilityDTO dto, Manager manager) {
        SportsFacility facility = SportsFacility.builder()
                .name(dto.getName())
                .location(Location.builder()
                        .address(dto.getLocation().getAddress())
                        .longitude(dto.getLocation().getLongitude())
                        .latitude(dto.getLocation().getLatitude())
                        .build())
                .pricePerHour(dto.getPricePerHour())
                .manager(manager)
                .build();
        manager.setSportsFacility(facility);
        managerRepository.save(manager);

        return manager.getSportsFacility();
    }

    public List<SportsFacilityDTO> getAllSportsFacilities() {
        // List<SportsFacility> facilities = sportsFacilityRepository.findAll();
        List<SportsFacilityDTO> facilities = new ArrayList<>();

        sportsFacilityRepository.findAll().forEach(sportsFacility -> {
            facilities.add(new SportsFacilityDTO(sportsFacility));
        });

        return facilities;
    }
}
