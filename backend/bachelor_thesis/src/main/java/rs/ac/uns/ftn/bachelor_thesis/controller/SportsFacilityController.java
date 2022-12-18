package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bachelor_thesis.service.SportsFacilityService;

@RestController
@RequestMapping("/sportsFacility")
public class SportsFacilityController {
    private SportsFacilityService sportsFacilityService;

    public SportsFacilityController(SportsFacilityService sportsFacilityService) {
        this.sportsFacilityService = sportsFacilityService;
    }
}
