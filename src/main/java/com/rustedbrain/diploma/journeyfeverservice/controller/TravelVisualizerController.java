package com.rustedbrain.diploma.journeyfeverservice.controller;

import com.rustedbrain.diploma.journeyfeverservice.controller.security.util.Constants;
import com.rustedbrain.diploma.journeyfeverservice.controller.service.TravelVisualizerService;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.security.AuthenticationRequest;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.security.UserDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.GreetingServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.ServiceInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/travel")
public class TravelVisualizerController {

    private final TravelVisualizerService controller;

    @Autowired
    public TravelVisualizerController(TravelVisualizerService controller) {
        this.controller = controller;
    }


    @RequestMapping(path = "/status", method = RequestMethod.GET)
    public ServiceInfo protectedStatus() {
        return controller.status();
    }

    @RequestMapping(path = "/greeting", method = RequestMethod.GET)
    public GreetingServiceInfo greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return controller.greeting(name);
    }

    @RequestMapping(value = "/place/add", method = {RequestMethod.POST})
    @ApiOperation(value = "add place")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = UserDTO.class),
            @ApiResponse(code = 403, message = Constants.FORBIDDEN),
            @ApiResponse(code = 422, message = Constants.USER_NOT_FOUND),
            @ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED)})
    public ResponseEntity<UserDTO> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
//        try {
//            String username = authenticationRequest.getUsername();
//            String password = authenticationRequest.getPassword();
//
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//            Authentication authentication = this.authenticationManager.authenticate(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
//
//            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::toString).collect(Collectors.toList());
//            return new ResponseEntity<>(new UserDTO(userDetails.getUsername(), roles,
//                    TokenUtil.createToken(userDetails), HttpStatus.OK), HttpStatus.OK);
//        } catch (BadCredentialsException bce) {
//            return new ResponseEntity<>(new UserDTO(), HttpStatus.UNPROCESSABLE_ENTITY);
//        } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        // }
    }
}
