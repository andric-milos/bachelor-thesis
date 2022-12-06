package rs.ac.uns.ftn.bachelor_thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.bachelor_thesis.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
