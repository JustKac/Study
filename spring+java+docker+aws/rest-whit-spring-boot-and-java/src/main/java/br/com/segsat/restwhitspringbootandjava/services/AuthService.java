package br.com.segsat.restwhitspringbootandjava.services;

import java.util.logging.Logger;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.segsat.restwhitspringbootandjava.data.vo.v1.security.AccountCredentialsVO;
import br.com.segsat.restwhitspringbootandjava.data.vo.v1.security.TokenVO;
import br.com.segsat.restwhitspringbootandjava.repositories.UserRepository;
import br.com.segsat.restwhitspringbootandjava.security.jwt.JwtTokenProvider;

@Service
public class AuthService {

    private Logger logger = Logger.getLogger(AuthService.class.getName());

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsVO data) {
        try {
            var username = data.getUsername();
            var password = data.getPassowrd();

            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByUsername(username);

            var tokenResponse = new TokenVO();
            if (user != null) {
                tokenResponse = tokenProvider.createAcessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException(String.format("Username %s not found!", username));
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

}
