package com.jfcbxp.auth.resource;

import com.jfcbxp.auth.domain.dto.UserDTO;
import com.jfcbxp.auth.jwt.JwtTokenProvider;
import com.jfcbxp.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class AuthResource {

    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_X_YAML = "application/x-yaml";
    public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML},
            consumes = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML})
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try {
            var userName = userDTO.getUserName();
            var password = userDTO.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

            var user = userRepository.findByUserName(userName);

            var token = "";
            if (user.isPresent()) {
                token = tokenProvider.createToken(userName, user.get().getRoles());

            } else {
                throw new UsernameNotFoundException(INVALID_USERNAME_OR_PASSWORD);
            }

            Map<Object, Object> model = new HashMap<>();
            model.put("username", userName);
            model.put("token", token);

            return ResponseEntity.ok(model);


        } catch (AuthenticationException e) {
            throw new BadCredentialsException(INVALID_USERNAME_OR_PASSWORD);
        }
    }

}
