package rs.ac.uns.ftn.bachelor_thesis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bachelor_thesis.dto.LoginInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.TokensDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.UserRoleDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;
import rs.ac.uns.ftn.bachelor_thesis.model.User;
import rs.ac.uns.ftn.bachelor_thesis.security.TokenUtil;
import rs.ac.uns.ftn.bachelor_thesis.service.ManagerService;
import rs.ac.uns.ftn.bachelor_thesis.service.PlayerService;
import rs.ac.uns.ftn.bachelor_thesis.service.UserService;
import rs.ac.uns.ftn.bachelor_thesis.validation.ValidationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenUtil tokenUtil;
    private final PlayerService playerService;
    private final ValidationUtil validationUtil;
    private final ManagerService managerService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.saveUser(user));
        // here and in the method bellow, it should return 201 created, not 200 ok (37:00)
    }

    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @PostMapping("/")
    public ResponseEntity<?> addRoleToUser(@RequestBody UserRoleDTO dto) {
        userService.addRoleToUser(dto.getUserEmail(), dto.getRoleName());
        return ResponseEntity.ok().build();
        // refactor this userService.addRoleToUser method to return feedback if the method is executed successfully.
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginInfoDTO dto, HttpServletRequest request) {
        log.info("Email: {}, Password: {}", dto.getEmail(), dto.getPassword());
        TokensDTO tokensDTO = userService.login(dto, request.getRequestURL().toString());
        return new ResponseEntity<>(tokensDTO, HttpStatus.OK);
    }

    @GetMapping("/token/renew")
    public ResponseEntity<?> renewToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String refreshToken = tokenUtil.getToken(authorizationHeader);

        if (refreshToken != null) {
            try {
                String accessToken = userService.renewToken(refreshToken, request.getRequestURL().toString());

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);

                return new ResponseEntity<>(tokens, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), FORBIDDEN);
            }
        }

        return new ResponseEntity<>("Refresh token is missing!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterInfoDTO dto) {
        // Role whitelisted values: "manager", "player"

        dto = validationUtil.trimAndValidateRegisterInfo(dto);

        if (dto == null) {
            return new ResponseEntity<>("Invalid input of data!" , HttpStatus.BAD_REQUEST);
        }

        if (dto.getRole().equals("player")) {
            int status = playerService.registerPlayer(dto);

            if (status == 1) {
                return new ResponseEntity<>("Role \"Player\" not found in the database!", HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (status == 2) {
                return new ResponseEntity<>("Email " + dto.getEmail() + " is already taken!" , HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(HttpStatus.OK); // return maybe CREATED, not OK? And return DTO instead od Player class?
        } else if (dto.getRole().equals("manager")) {
            int status = managerService.registerManager(dto);

            if (status == 1) {
                return new ResponseEntity<>("Role \"Player\" not found in the database!" , HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (status == 2) {
                return new ResponseEntity<>("Email " + dto.getEmail() + " is already taken!" , HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
