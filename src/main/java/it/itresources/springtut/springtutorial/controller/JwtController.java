package it.itresources.springtut.springtutorial.controller;

import io.jsonwebtoken.lang.Assert;
import it.itresources.springtut.springtutorial.entity.RoleEntity;
import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.exception.UsernameAlreadyExistsException;
import it.itresources.springtut.springtutorial.mapper.UserMapper;
import it.itresources.springtut.springtutorial.model.*;
import it.itresources.springtut.springtutorial.repository.RoleRepository;
import it.itresources.springtut.springtutorial.repository.UserRepository;
import it.itresources.springtut.springtutorial.security.JwtUtils;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class JwtController {

    private static final Logger logger = LoggerFactory.getLogger(JwtController.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public JwtController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
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
    public ResponseEntity<AuthToken>  authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("Request to authenticate user");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        // UserDetailImpl userDetails = (UserDetailImpl)authentication.getPrincipal();
        // List<String> roles = userDetails.getAuthorities().stream().map(i -> i.getAuthority()).collect(Collectors.toList());
        
        return ResponseEntity.ok(new AuthToken(jwtToken));
    }

    @PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest signUpRequest) {
		logger.info("Request to register a new user");
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new UsernameAlreadyExistsException("Username already exists.");
		}

		UserEntity user = new UserEntity(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()));

		List<String> strRoles = signUpRequest.getRoles();
		List<RoleEntity> roles = new ArrayList<>();

		Optional.ofNullable(strRoles).ifPresentOrElse((_roles) -> {
			if (_roles.size() == 0) {
				roles.add(roleRepository.findByName("ROLE_USER")
				.orElseThrow(() -> new RuntimeException("Role is not found.")));
			}
			_roles.forEach(role -> {
				switch (role) {
					case "ROLE_ADMIN":
						RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN")
								.orElseThrow(() -> new RuntimeException("Role is not found."));
						roles.add(adminRole);

						break;
					default:
						roles.add(roleRepository.findByName("ROLE_USER")
								.orElseThrow(() -> new RuntimeException("Role is not found.")));
				}
			});
		}, () -> roles.add(roleRepository.findByName("ROLE_USER")
				.orElseThrow(() -> new RuntimeException("Role is not found."))));

		user.setRoles(roles);
		UserEntity registeredUser = userRepository.save(user);

		return ResponseEntity.ok(UserMapper.entityToDto(registeredUser));
	}

	@GetMapping("/me")
	public ResponseEntity<User> me() {
		logger.info("Request to get user information");
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		String username = PrincipalUtils.extractPrincipal(authentication);

		Optional<UserEntity> userEntity = userRepository.findByUsername(username);
		return new ResponseEntity<>(UserMapper.entityToDto(userEntity.get()), userEntity.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}

	@GetMapping("/account")
	public ResponseEntity<User> account() {
		logger.info("Request to get Account information");
		User loggedUser = PrincipalUtils.loggerUserFromContext(SecurityContextHolder.getContext());

		// User user = UserMapper.userDetailToDto(loggedUser);
		if (loggedUser == null) {
			return new ResponseEntity<>(loggedUser, HttpStatus.NO_CONTENT);
		}

		return ResponseEntity.ok(loggedUser);
	}


}
