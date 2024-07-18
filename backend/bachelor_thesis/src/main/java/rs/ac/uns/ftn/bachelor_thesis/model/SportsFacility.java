package rs.ac.uns.ftn.bachelor_thesis.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "sports_facilities")
public class SportsFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(mappedBy = "sportsFacility")
    private Manager manager;

    private String name;
    private Double pricePerHour; // In RSD.

    @OneToMany(mappedBy = "sportsFacility")
    private Set<SportsFacilityComment> comments;

    // openHours -> podeliti na start i end?
}
