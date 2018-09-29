package com.rustedbrain.diploma.journeyfeverservice.controller;

import com.rustedbrain.diploma.journeyfeverservice.controller.security.util.Constants;
import com.rustedbrain.diploma.journeyfeverservice.controller.security.util.TokenUtil;
import com.rustedbrain.diploma.journeyfeverservice.controller.service.UserService;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.security.AuthenticationRequest;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.security.EditProfileRequest;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.security.RegistrationRequest;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.security.UserDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.Role;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final UserDetailsService customUserDetailsService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, UserService userService, @Qualifier("customUserDetailsService") UserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ApiOperation(value = "register")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = UserDTO.class),
            @ApiResponse(code = 406, message = Constants.USER_PROFILE_NOT_UPDATED),
            @ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED),
            @ApiResponse(code = 409, message = Constants.CONFLICT)})
    public ResponseEntity<UserDTO> register(@RequestBody RegistrationRequest registrationRequest) {
        try {
            String firstName = registrationRequest.getFirstName();
            String lastName = registrationRequest.getLastName();
            String username = registrationRequest.getUsername();
            String password = registrationRequest.getPassword();
            String email = registrationRequest.getMail();
            Role role = registrationRequest.getRole();
            User user = userService.createUser(firstName, lastName, username, password, email, role);

            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(user.getUsername());

            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::toString).collect(Collectors.toList());
            return new ResponseEntity<>(new UserDTO(userDetails.getUsername(), roles,
                    TokenUtil.createToken(userDetails), HttpStatus.OK), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new UserDTO(), HttpStatus.NOT_ACCEPTABLE);
        } catch (DataIntegrityViolationException ve) {
            return new ResponseEntity<>(new UserDTO(HttpStatus.CONFLICT), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new UserDTO(HttpStatus.EXPECTATION_FAILED), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/profile-edit", method = {RequestMethod.POST})
    @ApiOperation(value = "profile-edit")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = UserDTO.class),
            @ApiResponse(code = 406, message = Constants.USER_PROFILE_NOT_UPDATED),
            @ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED)})
    public ResponseEntity<UserDTO> editProfile(@RequestBody EditProfileRequest editProfileRequest) {
        try {
            String targetEditProfileLoginOrEmail = editProfileRequest.getTargetEditLoginOrEmail();
            String firstName = editProfileRequest.getFirstName();
            String lastName = editProfileRequest.getLastName();
            String username = editProfileRequest.getUsername();
            String password = editProfileRequest.getPassword();
            String email = editProfileRequest.getMail();
            Role role = editProfileRequest.getRole();
            User editedUser = userService.editUser(targetEditProfileLoginOrEmail, firstName, lastName, username, password, email, role);

            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(editedUser.getUsername());

            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::toString).collect(Collectors.toList());
            return new ResponseEntity<>(new UserDTO(userDetails.getUsername(), roles,
                    TokenUtil.createToken(userDetails), HttpStatus.OK), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new UserDTO(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/deregister", method = {RequestMethod.POST})
    @ApiOperation(value = "deregister")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = UserDTO.class),
            @ApiResponse(code = 406, message = Constants.USER_PROFILE_NOT_UPDATED),
            @ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED)})
    public ResponseEntity deregister(@RequestBody String mailOrLogin) {
        try {
            userService.removeUser(mailOrLogin);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/authenticate", method = {RequestMethod.POST})
    @ApiOperation(value = "authenticate")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = UserDTO.class),
            @ApiResponse(code = 403, message = Constants.FORBIDDEN),
            @ApiResponse(code = 422, message = Constants.USER_NOT_FOUND),
            @ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED)})
    public ResponseEntity<UserDTO> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            String username = authenticationRequest.getUsername();
            String password = authenticationRequest.getPassword();

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = this.authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::toString).collect(Collectors.toList());
            return new ResponseEntity<>(new UserDTO(userDetails.getUsername(), roles,
                    TokenUtil.createToken(userDetails), HttpStatus.OK), HttpStatus.OK);
        } catch (BadCredentialsException bce) {
            return new ResponseEntity<>(new UserDTO(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}