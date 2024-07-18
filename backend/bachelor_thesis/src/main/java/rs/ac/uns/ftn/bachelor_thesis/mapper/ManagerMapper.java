package rs.ac.uns.ftn.bachelor_thesis.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.bachelor_thesis.dto.ManagerDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;

@Component
public final class ManagerMapper implements BaseMapper<Manager, ManagerDTO> {
    @Override
    public ManagerDTO fromEntityToDto(Manager e) {
        return e != null ? new ManagerDTO(e) : null;
    }

    @Override
    public Manager fromDtoToEntity(ManagerDTO managerDTO) {
        throw new UnsupportedOperationException("Yet to be implemented");
    }
}
