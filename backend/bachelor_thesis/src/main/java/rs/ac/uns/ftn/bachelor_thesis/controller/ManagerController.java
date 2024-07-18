package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bachelor_thesis.dto.ManagerDTO;
import rs.ac.uns.ftn.bachelor_thesis.mapper.ManagerMapper;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;
import rs.ac.uns.ftn.bachelor_thesis.service.ManagerService;

@RestController
@RequestMapping(value = "/manager")
public class ManagerController {
    private final ManagerService managerService;
    private final ManagerMapper managerMapper;

    public ManagerController(ManagerService managerService, ManagerMapper managerMapper) {
        this.managerService = managerService;
        this.managerMapper = managerMapper;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ManagerDTO> getManagerByEmail(@PathVariable String email) {
        Manager manager = managerService.getManagerByEmail(email);
        return new ResponseEntity<>(managerMapper.fromEntityToDto(manager), HttpStatus.OK); // Now when I've created the ManagerMapper class, is there a need for ManangerDTO(Manager manager) constructor?
    }
}
