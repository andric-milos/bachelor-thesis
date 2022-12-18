package rs.ac.uns.ftn.bachelor_thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.bachelor_thesis.model.SportsFacility;

@Repository
public interface SportsFacilityRepository extends JpaRepository<SportsFacility, Long> {
}
