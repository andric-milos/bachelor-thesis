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
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "team_players",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> players;

    @OneToMany(mappedBy = "teamRed")
    private Set<Game> gamesAsTeamRed;

    @OneToMany(mappedBy = "teamBlue")
    private Set<Game> gamesAsTeamBlue;
}
