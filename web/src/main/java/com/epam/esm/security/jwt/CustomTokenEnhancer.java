package com.epam.esm.security.jwt;

import com.epam.esm.configuration.Translator;
import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class CustomTokenEnhancer implements TokenEnhancer {

    private final String issuerUri;
    private final UserRepository userRepository;
    @Autowired
    private Translator translator;

    public CustomTokenEnhancer(String issuerUri, UserRepository userRepository) {
        this.issuerUri = issuerUri;
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
                new BadCredentialsException(translator.toLocale("security.userNotFound")));
        DefaultOAuth2AccessToken enhanced = new DefaultOAuth2AccessToken(accessToken);
        Map<String, Object> extraInfo = new HashMap<>();
        extraInfo.put("user_id", user.getId());
        extraInfo.put("iss", issuerUri);
        extraInfo.put("user_role", user.getUserRole().name());
        enhanced.setAdditionalInformation(extraInfo);
        return enhanced;
    }

}
