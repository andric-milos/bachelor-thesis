package rs.ac.uns.ftn.bachelor_thesis.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import rs.ac.uns.ftn.bachelor_thesis.dto.CreateGroupDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static rs.ac.uns.ftn.bachelor_thesis.util.ValidationUtil.trimAndValidateRegisterInfo;
import static rs.ac.uns.ftn.bachelor_thesis.util.ValidationUtil.validateCreateGroupDTO;

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
}