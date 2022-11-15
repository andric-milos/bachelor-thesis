package rs.ac.uns.ftn.bachelor_thesis.model;

import lombok.*;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.TeamColor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id") // nullable
    private Game game;

    private TeamColor teamColor;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
}
