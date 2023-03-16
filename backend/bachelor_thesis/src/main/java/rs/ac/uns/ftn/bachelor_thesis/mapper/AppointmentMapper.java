package rs.ac.uns.ftn.bachelor_thesis.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.bachelor_thesis.dto.AppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.LocationDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Appointment;

@Component
public class AppointmentMapper implements BaseMapper<Appointment, AppointmentDTO> {
    @Override
    public AppointmentDTO fromEntityToDto(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .date(appointment.getDate().getTime())
                .capacity(appointment.getCapacity())
                .occupancy(appointment.getOccupancy())
                .price(appointment.getPrice())
                .location(new LocationDTO(
                        appointment.getLocation().getId(),
                        appointment.getLocation().getAddress(),
                        appointment.getLocation().getLongitude(),
                        appointment.getLocation().getLatitude()))
                .groupName(appointment.getGroup().getName())
                .build();
    }

    @Override
    public Appointment fromDtoToEntity(AppointmentDTO appointmentDTO) {
        return null;
    }
}
