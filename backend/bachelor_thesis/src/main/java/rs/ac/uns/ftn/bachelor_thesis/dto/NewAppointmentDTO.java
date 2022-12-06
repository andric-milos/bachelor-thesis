package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewAppointmentDTO {
    private Long groupId;
    private Long date;  // Date and time
    private String privacy;
    private String address;
    private int capacity;
    private double price;
}
