package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.*;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ManagerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private SportsFacilityDTO sportsFacility;

    public ManagerDTO(Manager manager) {
        this.id = manager.getId();
        this.firstName = manager.getFirstName();
        this.lastName = manager.getLastName();
        this.email = manager.getEmail();
        this.telephone = manager.getTelephone();
        this.sportsFacility = manager.getSportsFacility() != null ? new SportsFacilityDTO(manager.getSportsFacility()) : null;
    }
}
