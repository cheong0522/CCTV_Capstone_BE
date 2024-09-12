package com.simcheong.cctv._core.config;

import com.simcheong.cctv._core.security.JwtAuthenticationFilter;
import com.simcheong.cctv._core.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider);

        http
                .cors(Customizer.withDefaults()) // CORS 설정
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/index.html", "/about.html", "/shop.html", "/login.html", "/signup.html", "/mypage.html", "/register_shop.html", "/shop_detail.html", "/register_cctv.html", "/detect.html").permitAll()
                                .requestMatchers("/css/**", "/javascript/**", "/static/css/**", "/static/javascript/**", "/favicon.ico", "/static/**", "/test_video.mp4", "/1.png", "/2.png").permitAll()
                                .requestMatchers("/api/signup", "/api/login", "/api/shops/**", "/api/sms/**", "/api/kakao/**").permitAll()
                                .requestMatchers("/oauth", "/oauth/**", "/error").permitAll()
                                .requestMatchers("/h2-console/**").permitAll() // H2 콘솔 접근 허용
                                .requestMatchers("/predict/**", "/webcam_stream/**").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 추가
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())); // H2 콘솔을 위한 설정

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/css/**", "/javascript/**", "/images/**", "/favicon.ico");
    }
}
