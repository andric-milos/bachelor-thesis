package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.bachelor_thesis.model.Location;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private Long id;
    private String address;
    private Double longitude;
    private Double latitude;

    public LocationDTO(Location location) {
        if (location != null) {
            this.id = location.getId();
            this.address = location.getAddress();
            this.longitude = location.getLongitude();
            this.latitude = location.getLatitude();
        }
    }
}
