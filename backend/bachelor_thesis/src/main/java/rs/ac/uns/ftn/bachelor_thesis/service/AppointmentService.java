package rs.ac.uns.ftn.bachelor_thesis.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.NewAppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.AppointmentPrivacy;
import rs.ac.uns.ftn.bachelor_thesis.model.Appointment;
import rs.ac.uns.ftn.bachelor_thesis.model.Group;
import rs.ac.uns.ftn.bachelor_thesis.model.Location;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.repository.AppointmentRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class AppointmentService {
    private AppointmentRepository appointmentRepository;
    private GroupService groupService;

    public AppointmentService(AppointmentRepository appointmentRepository, GroupService groupService) {
        this.appointmentRepository = appointmentRepository;
        this.groupService = groupService;
    }

    public Appointment createAppointment(NewAppointmentDTO dto) {
        Optional<Group> group = groupService.getGroupById(dto.getGroupId());

        if (group.isPresent()) {
            Appointment newAppointment = new Appointment(
                    group.get(),
                    new Date(dto.getDate()),
                    AppointmentPrivacy.valueOf(dto.getPrivacy().toUpperCase()),
                    new Location(dto.getAddress(), 0.0, 0.0),
                    dto.getCapacity(),
                    dto.getPrice()
            );

            return appointmentRepository.save(newAppointment);
        }

        return null;
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment addPlayerToAppointment(Appointment appointment, Player player) {
        try {
            if (appointment.getPlayers().add(player)) {
                appointment.setOccupancy(appointment.getOccupancy() + 1);
                return appointmentRepository.save(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return appointment;
    }
    
    public boolean removePlayerFromAppointment(Appointment appointment, Player player) {
        if (appointment.getPlayers().remove(player)) {
            appointment.setOccupancy(appointment.getOccupancy() - 1);
            appointmentRepository.save(appointment);
            return true;
        }

        return false;
    }
}
