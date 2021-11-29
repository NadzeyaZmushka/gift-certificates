package com.epam.esm.config;

import com.epam.esm.exception.AccessDeniedHandlerImpl;
import com.epam.esm.exception.AuthEntryPointImpl;
import com.epam.esm.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) //для @PreAuthorize
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    //для аутентификации
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService);
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }

    //для авторизации

    /**
     * sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) которая управляет сессией юзера в системе спринг секюрити.
     * Так как авторизация пользователя по токену, не нужно создавать и хранить для него сессию. Поэтому STATELESS.
     * authorizeRequests() - значит все запросы должны проходить через Spring Security
     * antMatchers - какие урл адреса будут доступны для определенной роли, а какие нет
     * addFilterBefore - чтобы спринг как-то увидел пользователя в системе. фильтр, который будет срабатывать при каждом запросе
     * (будет доставать данные из токена, получать юзера из базы данных и сохранять данные пользователя и его роли в Spring Security,
     * чтобы спринг мог дальше выполнять свою работу и определять доступен ли определенный адрес для пользователя)
     * hasRole  спринг добавляет к имени роли префикс ROLE_
     */
    //todo: дописать остальные
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login", "/api/signup").anonymous()
                .antMatchers(HttpMethod.POST, "/api/logout").authenticated()
                .antMatchers(HttpMethod.GET, "/api/users", "/api/users/{\\d}", "/api/orders").authenticated()
                .antMatchers(HttpMethod.GET, "/api/certificates", "api/certificates/{\\d+}", "/api/tags", "api/tags/{\\d}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/tags/most-used").hasRole("ADMIN") // todo только для админа?
                .antMatchers(HttpMethod.POST, "/api/certificates", "/api/tags", "/api/certificates/{\\d}/tags", "/api/orders").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/certificates/{\\d}", "/api/tags/{\\d}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/certificates/{\\d}").hasRole("ADMIN")
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint()).and()//будет вызван, если пользователь попытается получить доступ к конечной точке, требующей аутентификации
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and()
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//отключить сессии и использовать только токен;
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthEntryPointImpl();
    }

//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        return new AccessDeniedHandlerImpl();
//    }

//    @Bean
//    protected DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder());
//        provider.setUserDetailsService(userDetailsService);
//        return provider;
//    }

}
