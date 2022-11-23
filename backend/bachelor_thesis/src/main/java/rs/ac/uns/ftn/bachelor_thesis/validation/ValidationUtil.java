package rs.ac.uns.ftn.bachelor_thesis.validation;

import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;

public class ValidationUtil {
    /**
     * Trims and validates the RegisterInfoDTO object passed as an argument.
     *
     * @param dto
     * @return null, if the validation wasn't successful. Trimmed RegisterInfoDTO object
     * if validation was successful.
     */
    public RegisterInfoDTO trimAndValidateRegisterInfo(RegisterInfoDTO dto) {
        if (dto == null) {
            return null;
        }

        dto.setFirstName(dto.getFirstName().trim());
        dto.setLastName(dto.getLastName().trim());
        dto.setEmail(dto.getEmail().trim());
        dto.setTelephone(dto.getTelephone().trim());
        dto.setRole(dto.getRole().trim());

        if (dto.getFirstName().equals("") || dto.getFirstName() == null) {
            return null;
        } else if (dto.getLastName().equals("") || dto.getLastName() == null) {
            return null;
        } else if (dto.getEmail().equals("") || dto.getEmail() == null) {
            return null;
        } else if (dto.getTelephone().equals("") || dto.getTelephone() == null) {
            return null;
        } else if (dto.getRole().equals("") || dto.getRole() == null) {
            return null;
        }

        return dto;
    }
}
