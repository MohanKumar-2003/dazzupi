package com.payment.sampleupi.upiController;

import com.payment.sampleupi.payload.JwtResponse;
import com.payment.sampleupi.payload.MessageResponse;
import com.payment.sampleupi.security.JwtTokenProvider;
import com.payment.sampleupi.security.UserPrincipal;
import com.payment.sampleupi.upiEntity.User;
import com.payment.sampleupi.upiRepository.UpiAccRepository;
import com.payment.sampleupi.upiRepository.UserRepository;
import com.payment.sampleupi.upiService.UpiAccService;
import com.payment.sampleupi.upiService.UserService;

import org.apache.tomcat.util.modeler.OperationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.sql.Types.NUMERIC;
import static javax.management.openmbean.SimpleType.STRING;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@Component
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private UpiAccService upiAccService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UpiAccRepository upiAccRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User signupRequest){
        if(userService.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.ok(new MessageResponse("User is already there!"));
        }
        if(upiAccService.existsByUpiAccName(signupRequest.getUpiAccount().getUpiId())) {
            User reguser = new User(signupRequest.getUsername(),
                    signupRequest.getEmail(),
                    encoder.encode(signupRequest.getPassword()), signupRequest.getPhone());


            userService.save(reguser,signupRequest.getUpiAccount().getUpiId());
            return ResponseEntity.ok(new MessageResponse("User is successfully registered"));
        }
        return ResponseEntity.ok(new MessageResponse("User need to have a UPI Account"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authUser(@RequestBody User user){
        if(userService.existsByUsername(user.getUsername())){
        try{
            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt=jwtTokenProvider.generateToken(authentication);
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

    return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUserId(),userDetails.getUsername(),userDetails.getEmail(),true,"User credentials is correct"));}
        catch (AuthenticationException e) {
            return ResponseEntity.ok(new JwtResponse("Username or password is wrong/User is not Verified", false));
        }}
        return ResponseEntity.ok(new JwtResponse("Username or password is wrong/User is not Verified", false));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok("{\"message\": \"Logged out successfully\"}");
    }

    }


