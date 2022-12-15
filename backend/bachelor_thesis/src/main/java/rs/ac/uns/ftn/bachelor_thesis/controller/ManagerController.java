package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bachelor_thesis.dto.ManagerDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;
import rs.ac.uns.ftn.bachelor_thesis.service.ManagerService;

import java.util.Optional;

@RestController
@RequestMapping(value = "/manager")
public class ManagerController {
    private ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getManagerByEmail(@PathVariable String email) {
        Optional<Manager> manager = managerService.getManagerByEmail(email);

        if (manager.isEmpty())
            return new ResponseEntity<>("Manager with email " + email + " doesn't exists!", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new ManagerDTO(manager.get()), HttpStatus.OK);
    }
}
