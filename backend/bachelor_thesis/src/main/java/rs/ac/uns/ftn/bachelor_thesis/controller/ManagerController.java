package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bachelor_thesis.dto.ManagerDTO;
import rs.ac.uns.ftn.bachelor_thesis.service.ManagerService;

@RestController
@RequestMapping(value = "/manager")
public class ManagerController {
    private ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ManagerDTO> getManagerByEmail(@PathVariable String email) {
        return new ResponseEntity<>(managerService.getManagerDtoByEmail(email), HttpStatus.OK);
    }
}
