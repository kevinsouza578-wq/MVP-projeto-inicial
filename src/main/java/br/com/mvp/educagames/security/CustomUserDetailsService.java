package br.com.mvp.educagames.security;

import br.com.mvp.educagames.entity.User;
import br.com.mvp.educagames.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        String login = usernameOrEmail.trim();
        User user = userRepository.findByUsername(login)
                .or(() -> userRepository.findByEmail(login.toLowerCase()))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado."));

        return UserPrincipal.from(user);
    }
}
