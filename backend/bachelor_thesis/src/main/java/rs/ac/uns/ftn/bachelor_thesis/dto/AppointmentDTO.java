package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.bachelor_thesis.model.Appointment;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentDTO {
    private Long id;
    private Long date;
    private String privacy;
    private int capacity;
    private int occupancy;
    private double price;
    private LocationDTO location;
    private Long groupId;

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.date = appointment.getDate().getTime();
        this.privacy = appointment.getPrivacy().toString().toLowerCase();
        this.capacity = appointment.getCapacity();
        this.occupancy = appointment.getOccupancy();
        this.price = appointment.getPrice();
        this.location = new LocationDTO(
                appointment.getLocation().getId(),
                appointment.getLocation().getAddress(),
                appointment.getLocation().getLongitude(),
                appointment.getLocation().getLatitude()
        );
        this.groupId = appointment.getGroup().getId();
    }
}
