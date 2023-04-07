package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bachelor_thesis.dto.*;
import rs.ac.uns.ftn.bachelor_thesis.service.AppointmentService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody NewAppointmentDTO dto) {
        return new ResponseEntity<>(appointmentService.createAppointment(dto), HttpStatus.OK);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<Set<AppointmentDTO>> getGroupsAppointments(@PathVariable("id") Long groupId) {
        return new ResponseEntity<>(appointmentService.getGroupsAppointments(groupId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable("id") Long appointmentId) {
        return new ResponseEntity<>(appointmentService.getAppointmentById(appointmentId), HttpStatus.OK);
    }

    @PutMapping("/attend/{id}")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<AppointmentDTO> attendAppointment(@PathVariable("id") Long appointmentId) {
        return new ResponseEntity<>(appointmentService.attendAppointment(appointmentId), HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<AppointmentDTO> cancelAppointment(@PathVariable("id") Long appointmentId) {
        return new ResponseEntity<>(appointmentService.cancelAppointment(appointmentId), HttpStatus.OK);
    }

    @PostMapping("/game")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<GameBasicInfoDTO> addGame(@RequestBody CreateGameDTO dto) {
        return new ResponseEntity<>(appointmentService.addGame(dto), HttpStatus.OK);
    }

    @GetMapping("/amIattending/{id}")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<Boolean> amIattending(@PathVariable("id") Long appointmentId) {
        return new ResponseEntity<>(appointmentService.amIattending(appointmentId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllPublicAppointments() {
        return new ResponseEntity<>(appointmentService.getAllPublicAppointments(), HttpStatus.OK);
    }
}
