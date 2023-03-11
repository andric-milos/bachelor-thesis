package rs.ac.uns.ftn.bachelor_thesis.mapper;

public interface BaseMapper<Entity, Dto> {
    Dto fromEntityToDto(Entity e);
    Entity fromDtoToEntity(Dto dto);
}
