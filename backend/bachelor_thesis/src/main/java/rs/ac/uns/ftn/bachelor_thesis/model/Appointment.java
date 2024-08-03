package rs.ac.uns.ftn.bachelor_thesis.model;

import lombok.*;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.AppointmentPrivacy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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

    private Date date; // Date and time -> MODIFY THIS AND THE REST OF THE CODE TO WORK WITH THE LocalDate
    private AppointmentPrivacy privacy;
    private int capacity; // The number of maximum players at the appointment.
    private int occupancy; // Calculated: (set of players).length
    private double price;

    @OneToOne(cascade = CascadeType.ALL)
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

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<Game> games;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Appointment(Group group,
                       Date date,
                       AppointmentPrivacy privacy,
                       Location location,
                       int capacity,
                       double price) {
        this.group = group;
        this.date = date;
        this.privacy = privacy;
        this.location = location;
        this.capacity = capacity;
        this.price = price;
    }
}
