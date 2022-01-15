package com.epam.esm.security;

import com.epam.esm.configuration.Translator;
import com.epam.esm.entity.User;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepositoryImpl userRepository;
    private final Translator translator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException(translator.toLocale("security.userNotFound")));

        return UserDetailsMapper.mapToUserDetails(user);
    }

}
