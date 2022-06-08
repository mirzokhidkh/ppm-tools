package uz.mk.ppmtool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mk.ppmtool.domain.User;
import uz.mk.ppmtool.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user==null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }


    @Transactional
    public User loadUserById (Long id) throws UsernameNotFoundException {
        User user = userRepository.getById(id);
        if (user==null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
