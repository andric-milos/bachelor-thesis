package rs.ac.uns.ftn.bachelor_thesis.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.CreateGroupDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.GroupDTO;
import rs.ac.uns.ftn.bachelor_thesis.exception.CustomizableBadRequestException;
import rs.ac.uns.ftn.bachelor_thesis.exception.InvalidInputDataException;
import rs.ac.uns.ftn.bachelor_thesis.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.bachelor_thesis.exception.UnauthorizedException;
import rs.ac.uns.ftn.bachelor_thesis.mapper.GroupMapper;
import rs.ac.uns.ftn.bachelor_thesis.model.Group;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.repository.GroupRepository;
import rs.ac.uns.ftn.bachelor_thesis.validation.ValidationUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final PlayerService playerService;
    private final ValidationUtil validationUtil;
    private final GroupMapper groupMapper;

    public GroupService(GroupRepository groupRepository,
                        PlayerService playerService,
                        ValidationUtil validationUtil,
                        GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.playerService = playerService;
        this.validationUtil = validationUtil;
        this.groupMapper = groupMapper;
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

    public GroupDTO createGroup(CreateGroupDTO dto) {
        if (!validationUtil.validateCreateGroupDTO(dto))
            throw new InvalidInputDataException("Invalid input of data!");

        if (!areAllPlayersExisting(dto.getPlayersEmails()))
            throw new CustomizableBadRequestException("You selected one or more non-existing players!");

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Player creatorOfTheGroup = playerService.getPlayerByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Not logged in!"));

        Group group = new Group();
        group.setName(dto.getName());

        dto.getPlayersEmails().forEach(playerEmail -> {
            Optional<Player> player = playerService.getPlayerByEmail(playerEmail);
            player.ifPresent(value -> group.getPlayers().add(value));
        });

        group.getPlayers().add(creatorOfTheGroup);

        return groupMapper.fromEntityToDto(groupRepository.save(group));
    }

    public Set<GroupDTO> getAllGroupsByPlayer() {
        String playerEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Group> allGroups = groupRepository.findAll();
        Set<Group> playersGroups = new HashSet<>();

        allGroups.forEach(group -> {
            if (group.getPlayers().stream().anyMatch(player -> player.getEmail().equals(playerEmail))) {
                playersGroups.add(group);
            }
        });

        Set<GroupDTO> groupsDTO = new HashSet<>();
        playersGroups.forEach(group -> {
            groupsDTO.add(new GroupDTO(group));
        });

        return groupsDTO;
    }

    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public GroupDTO getGroupDtoById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Group with an id %d does not exist!", id))
        );

        String loggedInUsersEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (group.getPlayers().stream().noneMatch(player -> player.getEmail().equals(loggedInUsersEmail)))
            throw new UnauthorizedException("You're not member of the group!");

        return groupMapper.fromEntityToDto(group);
    }

    public Set<String> getPlayersEmails(Group group) {
        Set<String> playersEmails = new HashSet<>();

        group.getPlayers().forEach(player -> {
            playersEmails.add(player.getEmail());
        });

        return playersEmails;
    }
}
