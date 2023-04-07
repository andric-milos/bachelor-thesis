package rs.ac.uns.ftn.bachelor_thesis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bachelor_thesis.dto.*;
import rs.ac.uns.ftn.bachelor_thesis.security.TokenUtil;
import rs.ac.uns.ftn.bachelor_thesis.service.UserService;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenUtil tokenUtil;

    @PostMapping("/login")
    public ResponseEntity<TokensDTO> login(@RequestBody LoginInfoDTO dto, HttpServletRequest request) {
        return new ResponseEntity<>(userService.login(dto, request.getRequestURL().toString()), HttpStatus.OK);
    }

    @GetMapping("/token/renew")
    public ResponseEntity<Map<String, String>> renewToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String refreshToken = tokenUtil.getToken(authorizationHeader);

        return new ResponseEntity<>(userService.renewToken(refreshToken, request.getRequestURL().toString()), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterInfoDTO dto) {
        return new ResponseEntity<>(userService.register(dto), HttpStatus.OK);
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasAnyRole('ROLE_PLAYER', 'ROLE_MANAGER')")
    public ResponseEntity<UserDTO> whoAmI() {
        return new ResponseEntity<>(userService.whoAmI(), HttpStatus.OK);
    }
}
