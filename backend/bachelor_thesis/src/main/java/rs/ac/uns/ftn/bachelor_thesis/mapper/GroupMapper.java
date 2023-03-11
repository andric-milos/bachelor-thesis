package rs.ac.uns.ftn.bachelor_thesis.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.bachelor_thesis.dto.GroupDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Group;

@Component
public class GroupMapper implements BaseMapper<Group, GroupDTO> {
    @Override
    public GroupDTO fromEntityToDto(Group e) {
        GroupDTO dto = new GroupDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        e.getPlayers().forEach(player -> {
            dto.getPlayersEmails().add(player.getEmail());
        });

        return dto;
    }

    @Override
    public Group fromDtoToEntity(GroupDTO groupDTO) {
        return null;
    }
}
