package rs.ac.uns.ftn.bachelor_thesis.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "location")
    private SportsFacility sportsFacility;

    @OneToOne(mappedBy = "location")
    private Appointment appointment;

    private String address;
    private double longitude;
    private double latitude;

    public Location(String address, double longitude, double latitude) {
        this.address = address;
        this.longitude = longitude;
        this.longitude = latitude;
    }
}
