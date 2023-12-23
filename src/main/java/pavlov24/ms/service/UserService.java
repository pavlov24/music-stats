package pavlov24.ms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pavlov24.ms.entity.User;
import pavlov24.ms.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            log.info("{}", optionalUser.get());
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
