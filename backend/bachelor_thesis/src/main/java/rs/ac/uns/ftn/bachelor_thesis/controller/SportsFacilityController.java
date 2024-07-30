package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bachelor_thesis.dto.SportsFacilityDTO;
import rs.ac.uns.ftn.bachelor_thesis.service.ManagerService;
import rs.ac.uns.ftn.bachelor_thesis.service.SportsFacilityService;
import rs.ac.uns.ftn.bachelor_thesis.validation.ValidationUtil;

import java.util.List;

@RestController
@RequestMapping("/sportsFacility")
public class SportsFacilityController {
    private final SportsFacilityService sportsFacilityService;
    private final ValidationUtil validationUtil;
    private final ManagerService managerService;

    public SportsFacilityController(SportsFacilityService sportsFacilityService,
                                    ValidationUtil validationUtil,
                                    ManagerService managerService) {
        this.sportsFacilityService = sportsFacilityService;
        this.validationUtil = validationUtil;
        this.managerService = managerService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<SportsFacilityDTO> createSportsFacility(@RequestBody SportsFacilityDTO dto) {
        return new ResponseEntity<>(sportsFacilityService.createSportsFacility(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SportsFacilityDTO>> getAllSportsFacilities() {
        return new ResponseEntity<>(sportsFacilityService.getAllSportsFacilities(), HttpStatus.OK);
    }
}
