package rs.ac.uns.ftn.bachelor_thesis.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id") // Maybe add a nullable=false?
    private Appointment appointment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teamRed_id") // Maybe add a nullable=false?
    private Team teamRed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teamBlue_id") // Maybe add a nullable=false?
    private Team teamBlue;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Goal> goals;
}
