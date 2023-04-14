package rs.ac.uns.ftn.bachelor_thesis.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {
    private static ValidationUtil validationUtil;

    @BeforeAll
    static void setup() {
        validationUtil = new ValidationUtil();
    }

    @Test
    @DisplayName("trimAndValidateRegisterInfo: all fields are valid")
    void trimAndValidateRegisterInfo_shouldReturnTrimmedObject_whenAllFieldsAreValid() {
        RegisterInfoDTO dto = new RegisterInfoDTO(
                "John",
                "Doe",
                "johndoe@gmail.com",
                "1234",
                "062454455",
                "player"
        );

        assertNotNull(validationUtil.trimAndValidateRegisterInfo(dto));
    }

    @Test
    @DisplayName("trimAndValidateRegisterInfo: firstname is not valid")
    void trimAndValidateRegisterInfo_shouldReturnNull_whenFirstNameIsNotValid() {
        RegisterInfoDTO dto = new RegisterInfoDTO(
                "",
                "Doe",
                "johndoe@gmail.com",
                "1234",
                "062454455",
                "player"
        );

        assertNull(validationUtil.trimAndValidateRegisterInfo(dto));
    }

    @Test
    @DisplayName("trimAndValidateRegisterInfo: email is not valid")
    void trimAndValidateRegisterInfo_shouldReturnNull_whenEmailIsNotValid() {
        RegisterInfoDTO dto = new RegisterInfoDTO(
                "John",
                "Doe",
                "johndoe.gmail.com",
                "1234",
                "062454455",
                "player"
        );

        assertNull(validationUtil.trimAndValidateRegisterInfo(dto));
    }

    @Test
    @DisplayName("trimAndValidateRegisterInfo: telephone is not valid")
    void trimAndValidateRegisterInfo_shouldReturnNull_whenTelephoneIsNotValid() {
        RegisterInfoDTO dto = new RegisterInfoDTO(
                "John",
                "Doe",
                "johndoe@gmail.com",
                "1234",
                "062",
                "player"
        );

        assertNull(validationUtil.trimAndValidateRegisterInfo(dto));
    }

    @Test
    @DisplayName("trimAndValidateRegisterInfo: role is not valid")
    void trimAndValidateRegisterInfo_shouldReturnTrimmedObject_whenRoleIsNotValid() {
        RegisterInfoDTO dto = new RegisterInfoDTO(
                "John",
                "Doe",
                "johndoe@gmail.com",
                "1234",
                "062237936",
                "invalidRole"
        );

        assertNotNull(validationUtil.trimAndValidateRegisterInfo(dto));
    }
}