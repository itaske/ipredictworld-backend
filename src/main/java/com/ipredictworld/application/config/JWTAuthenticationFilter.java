package com.ipredictworld.application.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipredictworld.application.api.UserAPI;
import com.ipredictworld.application.repository.TicketRepository;
import com.ipredictworld.application.repository.UserRepository;
import com.ipredictworld.application.response.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class JWTAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;



    public JWTAuthenticationFilter (){

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            LoginRequest loginRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequest.class);

            System.out.println(loginRequest);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword(),
                    new ArrayList<>()
            );

            System.out.println(token);

            //setDetails(request, token);
            Authentication auth = this.getAuthenticationManager().authenticate(token);
            System.out.println("Principal "+auth.getPrincipal().toString());
            return auth;
        } catch(IOException io){
            io.printStackTrace();
            throw new RuntimeException(io);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String,Object> map = new HashMap<>();
        map.put("status", "error");
        map.put("error", "ID or Password Incorrect");

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), map);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {


        JwtTokenProvider tokenProvider = new JwtTokenProvider();
        String token = tokenProvider.generateToken(authResult);
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        Map<String,Object> map = new HashMap<>();
        map.put("status", "success");
        map.put("token", token);
        map.put("email", ((UserPrincipal) authResult.getPrincipal()).getEmail());
        String id = ((UserPrincipal)authResult.getPrincipal()).getId();
        map.put("id", id);

        Optional<com.ipredictworld.application.entities.User> userOptional = userRepository.findById(id);

        boolean userOwnTicket = ticketRepository.findByUserAndPaid(userOptional.get(), true).size()>0;

        //ticketRepository.findTicketsUserPaidFor((((UserPrincipal) authResult.getPrincipal()).get)).size()>0;

        map.put("userOwnTicket", userOwnTicket);

        objectMapper.writeValue(response.getWriter(), map);
    }
}
