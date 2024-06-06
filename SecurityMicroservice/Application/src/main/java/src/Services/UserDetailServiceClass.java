package src.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import src.Models.Role;
import src.Models.Users;
import src.Models.UsersDetails;
import src.Repositories.UserRepository;

import java.util.Optional;

public class UserDetailServiceClass implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userValue = userRepository.findByUsername(username);

        return userValue.map(UsersDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
    }
}
