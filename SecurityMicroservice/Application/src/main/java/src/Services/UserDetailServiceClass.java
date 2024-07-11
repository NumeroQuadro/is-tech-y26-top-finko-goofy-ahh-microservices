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
    @Autowired
    private DefaultCredentialsHolder defaultCredentialsHolder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            var admin = new Users();
            admin.setUsername(defaultCredentialsHolder.getName());
            admin.setId(0);
            admin.setRole(Role.ADMIN);
            admin.setPassword(new BCryptPasswordEncoder().encode(defaultCredentialsHolder.getPassword()));

            return new UsersDetails(admin);
        }
        var userValue = userRepository.findByUsername(username);

        return userValue.map(UsersDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
    }
}
