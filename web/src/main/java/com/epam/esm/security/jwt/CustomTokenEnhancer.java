package com.epam.esm.security.jwt;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//дополнить payload токена перед его сохранением
@Component
@RequiredArgsConstructor
public class CustomTokenEnhancer implements TokenEnhancer {

    private final UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException(authentication.getName()));
        DefaultOAuth2AccessToken enhanced = new DefaultOAuth2AccessToken(accessToken);
        Map<String, Object> extraInfo = new HashMap<>();
        extraInfo.put("user_id", user.getId());
        extraInfo.put("iss", "http://localhost:8080"); //используется для указания приложения, из которого отправляется токен
        extraInfo.put("user_role", user.getUserRole().name());
        enhanced.setAdditionalInformation(extraInfo);
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.MINUTE, 15);
//        Date expirationDate = c.getTime();
//        enhanced.setExpiration(expirationDate);
        return enhanced;
    }

}
