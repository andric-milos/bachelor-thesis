package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.bachelor_thesis.model.SportsFacility;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SportsFacilityDTO {
    private Long id;
    private LocationDTO location;
    private String name;
    private double pricePerHour;
    private String managerEmail;
    private String managerFirstName;
    private String managerLastName;

    public SportsFacilityDTO(SportsFacility sportsFacility) {
        this.id = sportsFacility.getId();
        this.location = new LocationDTO(sportsFacility.getLocation());
        this.name = sportsFacility.getName();
        this.pricePerHour = sportsFacility.getPricePerHour();
        this.managerEmail = sportsFacility.getManager().getEmail();
        this.managerFirstName = sportsFacility.getManager().getFirstName();
        this.managerLastName = sportsFacility.getManager().getLastName();
    }
}
