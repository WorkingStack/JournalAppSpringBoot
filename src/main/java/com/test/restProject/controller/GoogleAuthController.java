package com.test.restProject.controller;

import com.test.restProject.entity.User;
import com.test.restProject.repository.UserRepository;
import com.test.restProject.services.CustomUserDetailsServiceImplement;
import com.test.restProject.utility.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/auth/google")
@Slf4j
public class GoogleAuthController {

   @Value("${spring.security.oauth2.client.registration.google.client-id}")
   private String clientId;
   @Value("${spring.security.oauth2.client.registration.google.client-secret}")
   private String clientSecret;

   @Autowired
   private RestTemplate restTemplate;
   @Autowired
   private CustomUserDetailsServiceImplement customUserDetailsServiceImplement;
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private JWTUtil jwtUtil;

   @GetMapping("/callback")
   public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
      try {
         String tokenEndpoint = "https://oauth2.googleapis.com/token";
         MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
         params.add("code", code);
         params.add("client_id", clientId);
         params.add("client_secret", clientSecret);
         params.add("redirect_uri", "https://developers.google.com/oauthplayground");
         params.add("grant_type", "authorization_code");

         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

         HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

         ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
         String idToken = (String) tokenResponse.getBody().get("id_token");
         String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
         ResponseEntity<Map> userResponseInfo = restTemplate.getForEntity(userInfoUrl, Map.class);
         if(userResponseInfo.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> userInfo = userResponseInfo.getBody();
            String email = (String) userInfo.get("email");
            UserDetails userDetails = null;
            try {
               userDetails = customUserDetailsServiceImplement.loadUserByUsername(email);
            }
            catch (Exception e) {
               User user = User.builder()
                       .email(email)
                       .userName(email)
                       .userPassword(passwordEncoder.encode(UUID.randomUUID().toString()))
                       .roles(Arrays.asList("USER"))
                       .build();
               userRepository.save(user);
            }

            Map<String, Object> claims= new HashMap<>();
            String jwtToken = jwtUtil.generateToken(claims, email);
            return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
         }

         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
      catch (Exception e) {
         log.error("Exception occur in class GoogleAuthController : ", e);
         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }
}
