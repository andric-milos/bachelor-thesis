package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;

@Getter
@Setter
@NoArgsConstructor
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
        if (manager.getSportsFacility() != null)
            this.sportsFacility = new SportsFacilityDTO(manager.getSportsFacility());
        else
            this.sportsFacility = null;
    }
}
