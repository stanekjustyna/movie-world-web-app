package com.justynastanek.movieworldwebapp.auth;

import com.justynastanek.movieworldwebapp.model.User;
import com.justynastanek.movieworldwebapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepository;

    @Autowired
    public ApplicationUserDaoService(PasswordEncoder passwordEncoder, UserRepo userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return findByUserId(username);
    }

    private Optional<ApplicationUser> findByUserId(String id) {

            Optional<User> user = userRepository.findByUsername(id);

            return (user.isEmpty()) ? Optional.empty() : Optional.of(new ApplicationUser(
                    user.get().getRoles().stream()
                            .map(e -> new SimpleGrantedAuthority(e.getRoleName())).collect(Collectors.toList()),
                    user.get().getPassword(), user.get().getUsername(),
                    true, true, true, true
            ));
    }

}
