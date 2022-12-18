package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bachelor_thesis.dto.SportsFacilityDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;
import rs.ac.uns.ftn.bachelor_thesis.model.SportsFacility;
import rs.ac.uns.ftn.bachelor_thesis.service.ManagerService;
import rs.ac.uns.ftn.bachelor_thesis.service.SportsFacilityService;
import rs.ac.uns.ftn.bachelor_thesis.validation.ValidationUtil;

import java.util.Optional;

@RestController
@RequestMapping("/sportsFacility")
public class SportsFacilityController {
    private SportsFacilityService sportsFacilityService;
    private ValidationUtil validationUtil;
    private ManagerService managerService;

    public SportsFacilityController(SportsFacilityService sportsFacilityService,
                                    ValidationUtil validationUtil,
                                    ManagerService managerService) {
        this.sportsFacilityService = sportsFacilityService;
        this.validationUtil = validationUtil;
        this.managerService = managerService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> createSportsFacility(@RequestBody SportsFacilityDTO dto) {
        // Manager needs to pass an object which contains following field: location, name, pricePerHour.

        if (!validationUtil.validateSportsFacilityDTO(dto))
            return new ResponseEntity<>("Invalid input of data!", HttpStatus.BAD_REQUEST);

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Manager> manager = managerService.getManagerByEmail(email);
        if (manager.isEmpty())
            return new ResponseEntity<>("Forbidden!", HttpStatus.FORBIDDEN);

        if (manager.get().getSportsFacility() != null)
            return new ResponseEntity<>("You've already added a sports facility!", HttpStatus.BAD_REQUEST);

        SportsFacility facility = sportsFacilityService.createSportsFacility(dto, manager.get());

        return new ResponseEntity<>(new SportsFacilityDTO(facility), HttpStatus.OK);
    }
}
