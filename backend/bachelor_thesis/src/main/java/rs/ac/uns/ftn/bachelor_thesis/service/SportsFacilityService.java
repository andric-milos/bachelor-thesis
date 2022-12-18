package rs.ac.uns.ftn.bachelor_thesis.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.SportsFacilityDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Location;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;
import rs.ac.uns.ftn.bachelor_thesis.model.SportsFacility;
import rs.ac.uns.ftn.bachelor_thesis.repository.ManagerRepository;

@Service
public class SportsFacilityService {
    private ManagerRepository managerRepository;

    public SportsFacilityService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
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
}
