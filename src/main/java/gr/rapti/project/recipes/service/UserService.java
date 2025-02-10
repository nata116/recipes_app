package gr.rapti.project.recipes.service;

import gr.rapti.project.recipes.model.User;
import gr.rapti.project.recipes.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Transactional
    public Optional<User> findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional(rollbackOn = Exception.class)
    public User saveUser(User user){
        LOGGER.info("in saveUser");
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    public String getPasswordByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getPassword).orElse(null); // Returns password or null if user is not found
    }
}
