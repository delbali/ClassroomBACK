package it.itresources.springtut.springtutorial.services.impl;

import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.model.UserDetailImpl;
import it.itresources.springtut.springtutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        Assert.notNull(userRepository, "Injected UerRepository instance cannot be null!");
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not authenticated. Username: " + username));
        return UserDetailImpl.build(user);
    }
}
