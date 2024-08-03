package rs.ac.uns.ftn.bachelor_thesis.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import rs.ac.uns.ftn.bachelor_thesis.dto.CreateGroupDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.NewAppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static rs.ac.uns.ftn.bachelor_thesis.util.ValidationUtil.trimAndValidateRegisterInfo;
import static rs.ac.uns.ftn.bachelor_thesis.util.ValidationUtil.validateCreateGroupDTO;
import static rs.ac.uns.ftn.bachelor_thesis.util.ValidationUtil.validateNewAppointmentDTO;

class ValidationUtilTest {
    @Nested
    @DisplayName("Method: trimAndValidateRegisterInfo")
    class trimAndValidateRegisterInfoTest {
        @Test
        void shouldReturnTrimmedObjectWhenAllFieldsAreValid() {
            RegisterInfoDTO dto = new RegisterInfoDTO(
                    "John",
                    "Doe",
                    "johndoe@gmail.com",
                    "1234",
                    "062454455",
                    "player"
            );

            assertNotNull(trimAndValidateRegisterInfo(dto));
        }

        @Test
        void shouldReturnNullWhenFirstNameIsEmptyString() {
            RegisterInfoDTO dto = new RegisterInfoDTO(
                    "",
                    "Doe",
                    "johndoe@gmail.com",
                    "1234",
                    "062454455",
                    "player"
            );

            assertNull(trimAndValidateRegisterInfo(dto));
        }

        @Test
        void shouldReturnNullWhenFirstNameIsNull() {
            RegisterInfoDTO dto = new RegisterInfoDTO(
                    null,
                    "Doe",
                    "johndoe@gmail.com",
                    "1234",
                    "062454455",
                    "player"
            );

            assertNull(trimAndValidateRegisterInfo(dto));
        }

        @Test
        void shouldReturnNullWhenEmailIsNotValid() {
            RegisterInfoDTO dto = new RegisterInfoDTO(
                    "John",
                    "Doe",
                    "johndoe.gmail.com",
                    "1234",
                    "062454455",
                    "player"
            );

            assertNull(trimAndValidateRegisterInfo(dto));
        }

        @Test
        void shouldReturnNullWhenTelephoneIsNotValid() {
            RegisterInfoDTO dto = new RegisterInfoDTO(
                    "John",
                    "Doe",
                    "johndoe@gmail.com",
                    "1234",
                    "062",
                    "player"
            );

            assertNull(trimAndValidateRegisterInfo(dto));
        }

        @Test
        void shouldReturnTrimmedObjectWhenRoleIsNotValid() {
            RegisterInfoDTO dto = new RegisterInfoDTO(
                    "John",
                    "Doe",
                    "johndoe@gmail.com",
                    "1234",
                    "062237936",
                    "invalidRole"
            );

            assertNotNull(trimAndValidateRegisterInfo(dto));
        }
    }

    @Nested
    @DisplayName("Method: validateCreateGroupDTO")
    class validateCreateGroupDTOTests {
        @Test
        void shouldReturnTrueWhenAllFieldsWithinPassedObjectAreValid() {
            CreateGroupDTO dto = new CreateGroupDTO(
                    "groupName",
                    Set.of("johndoe@gmail.com", "janedoe@gmail.com")
            );

            assertTrue(validateCreateGroupDTO(dto));
        }

        @Test
        void shouldReturnFalseWhenGroupNameIsNull() {
            CreateGroupDTO dto = new CreateGroupDTO(
                    null,
                    Set.of("johndoe@gmail.com", "janedoe@gmail.com")
            );

            assertFalse(validateCreateGroupDTO(dto));
        }

        @Test
        void shouldReturnFalseWhenGroupNameIsEmptyString() {
            CreateGroupDTO dto = new CreateGroupDTO(
                    "",
                    Set.of("johndoe@gmail.com", "janedoe@gmail.com")
            );

            assertFalse(validateCreateGroupDTO(dto));
        }

        @Test
        void shouldReturnFalseWhenOneOfThePlayerEmailsIsNotValid() {
            CreateGroupDTO dto = new CreateGroupDTO(
                    "groupName",
                    Set.of("johndoe@gmail.com", "nonValidEmail")
            );

            assertFalse(validateCreateGroupDTO(dto));
        }
    }

    @Nested
    @DisplayName("Method: validateNewAppointmentDTO")
    class validateNewAppointmentDTOTests {
        @Test
        void shouldReturnTrueWhenAllFieldsWithinPassedObjectAreValid() {
            NewAppointmentDTO dto = buildValidNewAppointmentDtoObject();
            assertTrue(validateNewAppointmentDTO(dto));
        }

        @Test
        void shouldReturnFalseWhenGroupIsNull() {
            NewAppointmentDTO dto = buildValidNewAppointmentDtoObject();
            dto.setGroupId(null);
            assertFalse(validateNewAppointmentDTO(dto));
        }

        @Test
        void shouldReturnFalseWhenDateIsNull() {
            NewAppointmentDTO dto = buildValidNewAppointmentDtoObject();
            dto.setDate(null);
            assertFalse(validateNewAppointmentDTO(dto));
        }

        @Test
        void shouldReturnFalseWhenAddressIsNull() {
            NewAppointmentDTO dto = buildValidNewAppointmentDtoObject();
            dto.setAddress(null);
            assertFalse(validateNewAppointmentDTO(dto));
        }

        @Test
        void shouldReturnFalseWhenAddressIsEmptyString() {
            NewAppointmentDTO dto = buildValidNewAppointmentDtoObject();
            dto.setAddress("");
            assertFalse(validateNewAppointmentDTO(dto));
        }

        @Test
        void shouldReturnFalseWhenPrivacyIsInvalid() {
            NewAppointmentDTO dto = buildValidNewAppointmentDtoObject();
            dto.setPrivacy("invalid privacy value");
            assertFalse(validateNewAppointmentDTO(dto));
        }

        @Test
        void shouldReturnFalseWhenPriceIsBelowZero() {
            NewAppointmentDTO dto = buildValidNewAppointmentDtoObject();
            dto.setPrice(-5000.0);
            assertFalse(validateNewAppointmentDTO(dto));
        }
    }

    private NewAppointmentDTO buildValidNewAppointmentDtoObject() {
        NewAppointmentDTO dto = new NewAppointmentDTO();
        dto.setGroupId(1L);
        dto.setDate(1722727800000L); // Aug 03 2024 23:30
        dto.setPrivacy("private");
        dto.setAddress("whatever");
        dto.setCapacity(5);
        dto.setPrice(1500.00);

        return dto;
    }
}