package rs.ac.uns.ftn.bachelor_thesis.util;

import rs.ac.uns.ftn.bachelor_thesis.dto.*;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.AppointmentPrivacy;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.TeamColor;

import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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

    private ValidationUtil() {
    }

    /**
     * Trims and validates the RegisterInfoDTO object passed as an argument.
     *
     * @param dto
     * @return null, if the validation wasn't successful. Trimmed RegisterInfoDTO object
     * if validation was successful.
     */
    public static RegisterInfoDTO trimAndValidateRegisterInfo(RegisterInfoDTO dto) {
        if (dto == null) {
            return null;
        }

        if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
            return null;
        } else if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) {
            return null;
        } else if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            return null;
        } else if (dto.getTelephone() == null || dto.getTelephone().trim().isEmpty()) {
            return null;
        } else if (dto.getRole() == null || dto.getRole().trim().isEmpty()) {
            return null;
        }

        dto.setFirstName(dto.getFirstName().trim());
        dto.setLastName(dto.getLastName().trim());
        dto.setEmail(dto.getEmail().trim());
        dto.setTelephone(dto.getTelephone().trim());
        dto.setRole(dto.getRole().trim());

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
     *
     * @param dto
     * @return false, if the CreateGroupDTO object is not valid, true if it is valid.
     */
    public static boolean validateCreateGroupDTO(CreateGroupDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            return false;
        }

        for (String email : dto.getPlayersEmails()) {
            if (!Pattern.compile(EMAIL_REGEX_PATTERN).matcher(email).matches()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Whitelisted values for privacy field are "PRIVATE" and "PUBLIC".
     * Capacity: There is no need to check if the capacity is null because Java, when passing null instead
     * of int, automatically maps that null as 0.
     *
     * @param dto
     * @return true, if the received NewAppointmentDTO object is valid; false if it isn't.
     */
    public static boolean validateNewAppointmentDTO(NewAppointmentDTO dto) {
        if (dto.getGroupId() == null) {
            return false;
        }

        // The code receives date as Long (as milliseconds) -> checking if date can be created from those milliseconds
        try {
            Date date = new Date(dto.getDate());
        } catch (Exception e) {
            return false;
        }

        if (dto.getAddress() == null || dto.getAddress().isEmpty()) {
            return false;
        }

        try {
            AppointmentPrivacy.valueOf(dto.getPrivacy().toUpperCase());
        } catch (Exception e) {
            return false;
        }

        if (dto.getPrice() < 0.0) {
            return false;
        }

        return true;
    }

    /**
     * Checks whether the received CreateGameDTO object is valid - teams must not be empty,
     * player can't be in both teams, emails must match the pattern, team color value must be valid.
     * Whitelisted values for goal->teamColor are: "RED" and "BLUE";
     *
     * @param dto
     * @return true, if the received object is valid, false if it is not.
     */
    public static boolean validateCreateGameDTO(CreateGameDTO dto) {
        if (dto.getAppointmentId() == null) {
            return false;
        }

        if (dto.getTeamRed().isEmpty() || dto.getTeamBlue().isEmpty()) {
            return false;
        }

        if (Stream.concat(dto.getTeamRed().stream(), dto.getTeamBlue().stream()).distinct().count() <
                Stream.concat(dto.getTeamRed().stream(), dto.getTeamBlue().stream()).count()) {
            return false;
        }

        for (String email : Stream.concat(dto.getTeamRed().stream(), dto.getTeamBlue().stream()).toList()) {
            if (!Pattern.compile(EMAIL_REGEX_PATTERN).matcher(email).matches())
                return false;
        }

        for (GoalDTO goal : dto.getGoals()) {
            try {
                if (TeamColor.valueOf(goal.getTeamColor().toUpperCase()).equals(TeamColor.RED)
                        && !dto.getTeamRed().contains(goal.getPlayer())) {
                    return false;
                } else if (TeamColor.valueOf(goal.getTeamColor().toUpperCase()).equals(TeamColor.BLUE)
                        && !dto.getTeamBlue().contains(goal.getPlayer())) {
                    return false;
                }
            } catch (Exception e) { // IllegalArgumentException thrown by Enum.valueOf method.
                return false;
            }

        }

        return true;
    }

    public static boolean validateSportsFacilityDTO(SportsFacilityDTO dto) {
        if (dto.getLocation().getAddress() == null || dto.getLocation().getAddress().isEmpty()) {
            return false;
        }

        if (dto.getName() == null || dto.getName().isEmpty()) {
            return false;
        }

        if (dto.getPricePerHour() < 0.0) {
            return false;
        }

        return true;
    }
}
