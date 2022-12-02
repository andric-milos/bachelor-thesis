package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bachelor_thesis.dto.CreateGroupDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Group;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.service.GroupService;
import rs.ac.uns.ftn.bachelor_thesis.service.PlayerService;
import rs.ac.uns.ftn.bachelor_thesis.validation.ValidationUtil;

import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {
    private GroupService groupService;
    private ValidationUtil validationUtil;
    private PlayerService playerService;

    public GroupController(GroupService groupService, ValidationUtil validationUtil, PlayerService playerService) {
        this.groupService = groupService;
        this.validationUtil = validationUtil;
        this.playerService = playerService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupDTO dto) {
        if (!validationUtil.validateCreateGroupDTO(dto))
            return new ResponseEntity<>("Invalid input of data!", HttpStatus.BAD_REQUEST);

        if (!groupService.areAllPlayersExisting(dto.getPlayersEmails()))
            return new ResponseEntity<>("You selected one or more non-existing players!", HttpStatus.BAD_REQUEST);

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Player> player = playerService.getPlayerByEmail(email);
        if (player.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Group group = groupService.createGroup(dto, player.get());
        if (group == null) {
            return new ResponseEntity<>("Something went wrong on the server!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK); // CREATED?
    }
}
