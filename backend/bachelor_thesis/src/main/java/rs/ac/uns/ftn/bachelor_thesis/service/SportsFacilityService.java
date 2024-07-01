package rs.ac.uns.ftn.bachelor_thesis.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.SportsFacilityDTO;
import rs.ac.uns.ftn.bachelor_thesis.exception.CustomizableBadRequestException;
import rs.ac.uns.ftn.bachelor_thesis.exception.UnauthorizedException;
import rs.ac.uns.ftn.bachelor_thesis.exception.InvalidInputDataException;
import rs.ac.uns.ftn.bachelor_thesis.model.Location;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;
import rs.ac.uns.ftn.bachelor_thesis.model.SportsFacility;
import rs.ac.uns.ftn.bachelor_thesis.repository.ManagerRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.SportsFacilityRepository;
import rs.ac.uns.ftn.bachelor_thesis.validation.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class SportsFacilityService {
    private ManagerRepository managerRepository;
    private SportsFacilityRepository sportsFacilityRepository;
    private ValidationUtil validationUtil;
    private ManagerService managerService;

    public SportsFacilityService(ManagerRepository managerRepository,
                                 SportsFacilityRepository sportsFacilityRepository,
                                 ValidationUtil validationUtil,
                                 ManagerService managerService) {
        this.managerRepository = managerRepository;
        this.sportsFacilityRepository = sportsFacilityRepository;
        this.validationUtil = validationUtil;
        this.managerService = managerService;
    }

    public SportsFacilityDTO createSportsFacility(SportsFacilityDTO dto) {
        // Manager needs to pass an object which contains following fields: location, name, pricePerHour.

        if (!validationUtil.validateSportsFacilityDTO(dto))
            throw new InvalidInputDataException("Invalid input of data!");

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Manager manager;
        try {
            manager = managerService.getManagerByEmail(email);
        } catch (Exception e) {
            throw new UnauthorizedException("You're not authorized to perform this action!");
        }

        if (manager.getSportsFacility() != null)
            throw new CustomizableBadRequestException("You've already added a sports facility!");

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

        return new SportsFacilityDTO(manager.getSportsFacility());
    }

    public List<SportsFacilityDTO> getAllSportsFacilities() {
        List<SportsFacilityDTO> facilities = new ArrayList<>();

        sportsFacilityRepository.findAll().forEach(sportsFacility -> {
            facilities.add(new SportsFacilityDTO(sportsFacility));
        });

        return facilities;
    }
}
