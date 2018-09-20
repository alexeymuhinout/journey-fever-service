package com.rustedbrain.diploma.journeyfeverservice;

import com.rustedbrain.diploma.journeyfeverservice.controller.security.AuthTokenFilter;
import com.rustedbrain.diploma.journeyfeverservice.controller.service.UserService;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.security.AuthenticationRequest;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.security.EditProfileRequest;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.security.RegistrationRequest;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.security.UserDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.ServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.Role;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TravelVisualizerRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoginControllerTests {

    private static final String DEFAULT_SERVER_URL = "http://192.168.154.102:8080";
    private static final String GREETING_URL = DEFAULT_SERVER_URL + "/travel/status";
    private static final String REGISTER_URL = DEFAULT_SERVER_URL + "/login/register";
    private static final String DEREGISTER_URL = DEFAULT_SERVER_URL + "/login/deregister";
    private static final String EDIT_PROFILE_URL = DEFAULT_SERVER_URL + "/login/profile-edit";
    private static final String LOGIN_URL = DEFAULT_SERVER_URL + "/login/authenticate";

    @Autowired
    private UserService userService;

    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        String adminFirstName = "admin";
        String adminLastName = "admin";
        String adminUsername = "admin";
        String adminPassword = "admin";
        String adminEmail = "admin@gmail.com";
        Role adminRole = Role.ADMINISTRATOR;
        this.userService.createUser(adminFirstName, adminLastName, adminUsername, adminPassword, adminEmail, adminRole);

        String moderFirstName = "moder";
        String moderLastName = "moder";
        String moderUsername = "moder";
        String moderPassword = "moder";
        String moderEmail = "moder@gmail.com";
        Role moderRole = Role.MODERATOR;
        this.userService.createUser(moderFirstName, moderLastName, moderUsername, moderPassword, moderEmail, moderRole);

        String firstName = "user";
        String lastName = "user";
        String username = "user";
        String password = "user";
        String email = "user@gmail.com";
        Role role = Role.USER;
        this.userService.createUser(firstName, lastName, username, password, email, role);
        this.restTemplate = new TestRestTemplate();
    }

    @After
    public void tearDown() {
        String adminEmail = "admin@gmail.com";
        this.userService.removeUser(adminEmail);

        String moderEmail = "moder@gmail.com";
        this.userService.removeUser(moderEmail);

        String userEmail = "user@gmail.com";
        this.userService.removeUser(userEmail);
    }

    @Test
    public void testGreetingUnauthorized() throws URISyntaxException {
        ResponseEntity<ServiceInfo> statusInfo = restTemplate.getForEntity(new URI(GREETING_URL), ServiceInfo.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, statusInfo.getStatusCode());
    }

    @Test
    public void testGreetingAuthorized() throws URISyntaxException {
        HttpHeaders httpHeaders = new HttpHeaders();
        AuthenticationRequest request = new AuthenticationRequest("admin", "admin");

        ResponseEntity<UserDTO> auth = restTemplate.exchange(
                new RequestEntity<>(request, httpHeaders, HttpMethod.POST, new URI(LOGIN_URL)),
                UserDTO.class);
        Assert.assertNotNull(auth.getBody());
        Assert.assertNotEquals(HttpStatus.NOT_FOUND, auth.getBody().getStatus());

        httpHeaders.add(AuthTokenFilter.AUTH_TOKEN_HEADER_NAME, auth.getBody().getToken());
        ResponseEntity<ServiceInfo> statusInfo = restTemplate.exchange(
                new RequestEntity<>(request, httpHeaders, HttpMethod.GET, new URI(GREETING_URL)),
                ServiceInfo.class);
        Assert.assertEquals(HttpStatus.OK, statusInfo.getStatusCode());
    }

    @Test
    public void testAuthorizeWrongPassword() throws URISyntaxException {
        HttpHeaders httpHeaders = new HttpHeaders();
        AuthenticationRequest request = new AuthenticationRequest("admin", "wrongadmin");

        ResponseEntity<UserDTO> auth = restTemplate.exchange(
                new RequestEntity<>(request, httpHeaders, HttpMethod.POST, new URI(LOGIN_URL)),
                UserDTO.class);
        Assert.assertNotNull(auth.getBody());
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, auth.getStatusCode());
    }

    @Test
    public void testRegister() throws URISyntaxException {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Tester");
        registrationRequest.setLastName("Tester");
        registrationRequest.setMail("tester@gmail.com");
        registrationRequest.setPassword("tester");
        registrationRequest.setUsername("tester");
        registrationRequest.setRole(Role.USER);

        ResponseEntity<UserDTO> statusInfo = restTemplate.postForEntity(new URI(REGISTER_URL), registrationRequest, UserDTO.class);
        Assert.assertEquals(HttpStatus.OK, statusInfo.getStatusCode());
        Assert.assertNotNull(statusInfo.getBody());
        Assert.assertEquals(registrationRequest.getUsername(), statusInfo.getBody().getUsername());
        Assert.assertEquals(Collections.singletonList(Role.USER.name()), statusInfo.getBody().getRoles());
    }

    @Test
    public void testDeregisterByAdmin() throws URISyntaxException {
        HttpHeaders httpHeaders = new HttpHeaders();
        AuthenticationRequest request = new AuthenticationRequest("admin", "admin");

        ResponseEntity<UserDTO> auth = restTemplate.exchange(
                new RequestEntity<>(request, httpHeaders, HttpMethod.POST, new URI(LOGIN_URL)),
                UserDTO.class);
        Assert.assertNotNull(auth.getBody());
        Assert.assertNotEquals(HttpStatus.NOT_FOUND, auth.getBody().getStatus());

        httpHeaders.add(AuthTokenFilter.AUTH_TOKEN_HEADER_NAME, auth.getBody().getToken());

        String deregisterMail = "admin@gmail.com";
        ResponseEntity<Object> statusInfo = restTemplate.exchange(
                new RequestEntity<>(deregisterMail, httpHeaders, HttpMethod.POST, new URI(DEREGISTER_URL)), Object.class);
        Assert.assertEquals(HttpStatus.OK, statusInfo.getStatusCode());
    }

    @Test
    public void testChangeRegistrationInfoByAdmin() throws URISyntaxException {
        HttpHeaders httpHeaders = new HttpHeaders();
        AuthenticationRequest request = new AuthenticationRequest("user", "user");

        ResponseEntity<UserDTO> auth = restTemplate.exchange(
                new RequestEntity<>(request, httpHeaders, HttpMethod.POST, new URI(LOGIN_URL)),
                UserDTO.class);
        Assert.assertNotNull(auth.getBody());
        Assert.assertNotEquals(HttpStatus.NOT_FOUND, auth.getBody().getStatus());

        httpHeaders.add(AuthTokenFilter.AUTH_TOKEN_HEADER_NAME, auth.getBody().getToken());

        String editProfileUserMail = "user@gmail.com";

        EditProfileRequest editRequest = new EditProfileRequest();
        editRequest.setTargetEditLoginOrEmail(editProfileUserMail);
        editRequest.setUsername("newuser");
        editRequest.setRole(Role.ADMINISTRATOR);

        ResponseEntity<UserDTO> statusInfo = restTemplate.postForEntity(new URI(EDIT_PROFILE_URL), editRequest, UserDTO.class);
        Assert.assertEquals(HttpStatus.OK, statusInfo.getStatusCode());
        Assert.assertNotNull(statusInfo.getBody());
        Assert.assertEquals(editRequest.getUsername(), statusInfo.getBody().getUsername());
        Assert.assertEquals(Collections.singletonList(Role.ADMINISTRATOR.name()), statusInfo.getBody().getRoles());
    }
}
