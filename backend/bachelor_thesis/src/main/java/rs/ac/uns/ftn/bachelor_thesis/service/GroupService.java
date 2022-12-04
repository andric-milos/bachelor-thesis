package rs.ac.uns.ftn.bachelor_thesis.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.CreateGroupDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Group;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.repository.GroupRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final PlayerService playerService;

    public GroupService(GroupRepository groupRepository, PlayerService playerService) {
        this.groupRepository = groupRepository;
        this.playerService = playerService;
    }

    /**
     * Loops through the whole list of passed player's emails and for every email
     * checks if there is an existing player in the database.
     * @param playersEmails
     * @return true or false
     */
    public boolean areAllPlayersExisting(Set<String> playersEmails) {
        for (String email : playersEmails) {
            Optional<Player> player = playerService.getPlayerByEmail(email);
            if (player.isEmpty())
                return false;
        }

        return true;
    }

    public Group createGroup(CreateGroupDTO dto, Player creator) {
        Group group = new Group();
        group.setName(dto.getName());

        dto.getPlayersEmails().forEach(playerEmail -> {
            Optional<Player> player = playerService.getPlayerByEmail(playerEmail);
            player.ifPresent(value -> group.getPlayers().add(value));
        });

        group.getPlayers().add(creator); /* Existence of creator of the group is previously verified in
                                            GroupController class. */

        return groupRepository.save(group);
    }

    public Set<Group> getAllGroupsByPlayer(String playerEmail) {
        List<Group> allGroups = groupRepository.findAll();
        Set<Group> playersGroups = new HashSet<>();

        allGroups.forEach(group -> {
            if (group.getPlayers().stream().anyMatch(player -> player.getEmail().equals(playerEmail))) {
                playersGroups.add(group);
            }
        });

        return playersGroups;
    }
}
