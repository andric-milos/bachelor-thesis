package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bachelor_thesis.dto.SportsFacilityDTO;
import rs.ac.uns.ftn.bachelor_thesis.service.ManagerService;
import rs.ac.uns.ftn.bachelor_thesis.service.SportsFacilityService;
import rs.ac.uns.ftn.bachelor_thesis.validation.ValidationUtil;

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
        return new ResponseEntity<>(sportsFacilityService.createSportsFacility(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllSportsFacilities() {
        return new ResponseEntity<>(sportsFacilityService.getAllSportsFacilities(), HttpStatus.OK);
    }
}
