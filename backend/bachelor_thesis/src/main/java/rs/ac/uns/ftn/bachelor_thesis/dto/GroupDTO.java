package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.bachelor_thesis.model.Group;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class GroupDTO {
    private Long id;
    private String name;
    private Set<String> playersEmails = new HashSet<>();

    public GroupDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();

        Set<String> emails = new HashSet<>();
        group.getPlayers().forEach(player -> {
            emails.add(player.getEmail());
        });
        this.setPlayersEmails(emails);
    }
}
