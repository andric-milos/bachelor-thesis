package rs.ac.uns.ftn.bachelor_thesis.validation;

import rs.ac.uns.ftn.bachelor_thesis.dto.CreateGroupDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;

import java.util.regex.Pattern;

public class ValidationUtil {
    /*
    The following restrictions are imposed in the email address' local part:
        - It allows numeric values from 0 to 9.
        - Both uppercase and lowercase letters from a to z are allowed.
        - Allowed are underscore “_”, hyphen “-“, and dot “.”
        - Dot isn't allowed at the start and end of the local part.
        - Consecutive dots aren't allowed.
        - For the local part, a maximum of 64 characters are allowed.
    Restrictions for the domain part:
        - It allows numeric values from 0 to 9.
        - We allow both uppercase and lowercase letters from a to z.
        - Hyphen “-” and dot “.” aren't allowed at the start and end of the domain part.
        - No consecutive dots.
    */
    private static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-.][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String TELEPHONE_PATTERN = "06\\d{7,8}";

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

        if (!Pattern.compile(EMAIL_REGEX_PATTERN).matcher(dto.getEmail()).matches()) {
            return null;
        }

        if (!Pattern.compile(TELEPHONE_PATTERN).matcher(dto.getTelephone()).matches()) {
            return null;
        }

        return dto;
    }

    /**
     * Loops through the whole list of passed player's emails and for every email
     * checks if it fits a required pattern. Also, checks if the name of the group
     * is a valid string.
     * @param dto
     * @return false, if the CreateGroupDTO object is not valid, true if it is valid.
     */
    public boolean validateCreateGroupDTO(CreateGroupDTO dto) {
        if (dto.getName() == null || dto.getName().equals(""))
            return false;

        for (String email : dto.getPlayersEmails()) {
            if (!Pattern.compile(EMAIL_REGEX_PATTERN).matcher(email).matches())
                return false;
        }

        return true;
    }
}