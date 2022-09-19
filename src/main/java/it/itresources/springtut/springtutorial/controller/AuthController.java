package it.itresources.springtut.springtutorial.controller;

import io.jsonwebtoken.lang.Assert;
import it.itresources.springtut.springtutorial.entity.RoleEntity;
import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.exception.UsernameAlreadyExistsException;
import it.itresources.springtut.springtutorial.mapper.UserMapper;
import it.itresources.springtut.springtutorial.model.*;
import it.itresources.springtut.springtutorial.model.request.LoginRequest;
import it.itresources.springtut.springtutorial.model.request.UserRegistrationRequest;
import it.itresources.springtut.springtutorial.repository.RoleRepository;
import it.itresources.springtut.springtutorial.repository.UserRepository;
import it.itresources.springtut.springtutorial.security.JwtUtils;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.itresources.springtut.springtutorial.security.PrincipalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        Assert.notNull(authenticationManager, "Injected AuthenticationManager instance cannot be null!");
        Assert.notNull(userRepository, "Injected UserRepository instance cannot be null!");
        Assert.notNull(roleRepository, "Injected RoleRepository instance cannot be null!");
        Assert.notNull(passwordEncoder, "Injected PasswordEncoder instance cannot be null!");
        Assert.notNull(jwtUtils, "Injected JwtUtils instance cannot be null!");
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
	public ResponseEntity<AuthToken> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
		logger.info("Request to authenticate user");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		ArrayList<String> tokens = jwtUtils.generateJwtTokens(authentication);

		String jwtToken = tokens.get(0);
		String jwtRefreshToken = tokens.get(1);

		// UserDetailImpl userDetails = (UserDetailImpl)authentication.getPrincipal();
		// List<String> roles = userDetails.getAuthorities().stream().map(i ->
		// i.getAuthority()).collect(Collectors.toList());

		// return ResponseEntity.created(URI.create("/persons/" +
		// person.getName())).build();
		
		return ResponseEntity.ok(new AuthToken(jwtToken, jwtRefreshToken));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest signUpRequest) {
		
		logger.info("Request to register a new user");
		if (serviceUserImpl.checkUserExistance(signUpRequest.getUsername())) {
			throw new UsernameAlreadyExistsException("Username already exists.");
		}
		LocalDate date=DateMapper.stringToDate(signUpRequest.getHiredDate());
		List<RoleEntity> roles=new ArrayList<>();
		signUpRequest.getRoles().forEach(x->{
			roles.add(serviceRolesImpl.findRoleByName(x).get());
		});
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("DA CAMBIARE").toUriString());
		return ResponseEntity.created(uri).body(UserMapper.entityToDto(serviceRolesImpl.registration(UserMapper.signUpToEntity(signUpRequest,date , roles, passwordEncoder.encode(signUpRequest.getPassword())))));
	}
	@PostMapping ("/refresh")
	public ResponseEntity<?> refresh(HttpServletRequest request) {
		logger.info("refreshing Token");
		String header=request.getHeader("Authorization");
		if(header.startsWith("Bearer ")){
			String refresh=header.substring("Bearer ".length());
			jwtUtils.validateJwtToken(refresh);
			String username=jwtUtils.getUsernameFromToken(refresh);
			UserDetails userDetail=userDetailsServiceImpl.loadUserByUsername(username);
			Authentication authentication=authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDetail.getUsername(), passwordEncoder.encode(userDetail.getPassword())));
			ArrayList<String> tokens=jwtUtils.generateJwtTokens(authentication);
			return ResponseEntity.ok(new AuthToken(tokens.get(0), tokens.get(1)));
		}
		return ResponseEntity.badRequest().body("Invalid Token");
	}


}
