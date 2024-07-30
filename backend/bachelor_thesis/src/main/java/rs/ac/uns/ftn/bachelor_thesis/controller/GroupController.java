package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bachelor_thesis.dto.CreateGroupDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.GroupDTO;
import rs.ac.uns.ftn.bachelor_thesis.service.GroupService;

import java.util.Set;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<GroupDTO> createGroup(@RequestBody CreateGroupDTO dto) {
        return new ResponseEntity<>(groupService.createGroup(dto), HttpStatus.OK); // CREATED?
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<Set<GroupDTO>> getAllMyGroups() {
        return new ResponseEntity<>(groupService.getAllGroupsByPlayer(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable("id") Long groupId) {
        return new ResponseEntity<>(groupService.getGroupDtoById(groupId), HttpStatus.OK);
    }
}
