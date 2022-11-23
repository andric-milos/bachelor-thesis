package rs.ac.uns.ftn.bachelor_thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
