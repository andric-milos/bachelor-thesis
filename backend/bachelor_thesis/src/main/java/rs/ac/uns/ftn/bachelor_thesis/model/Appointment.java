package rs.ac.uns.ftn.bachelor_thesis.model;

import lombok.*;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.AppointmentPrivacy;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Date date; // Date and time.
    private AppointmentPrivacy privacy;
    private int capacity; // The number of maximum players at the appointment.
    private int occupancy; // Calculated: (set of players).length
    private double price;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToMany
    @JoinTable(
            name = "appointment_players",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> players; // Maybe, put List instead of a Set, because List has an order structure.
    // Set/list of players who applied for the appointment

    @OneToMany(mappedBy = "appointment")
    private Set<Game> games;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
