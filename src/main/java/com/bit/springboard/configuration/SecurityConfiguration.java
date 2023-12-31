package com.bit.springboard.configuration;

import com.bit.springboard.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
//Security filterchain을 구성하기 위한 어노테이션
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    //비밀번호 암호화를 위한 PasswordEncoder
    //복호화가 불가능하다.
    //match 메소드를 이용해서 사용자의 입력값과 DB의 저장값을 비교한다. => true, flase 리턴
    //match(암호화되지 않은 값, 암호화된 값)
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //필터 체인 구현(HttpSecurity 객체 사용)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(httpSecurityCorsConfigurer -> {})
                //csrf 공격에 대한 옵션 꺼두기
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(httpSecurityHttpBasicConfigurer -> {
                    httpSecurityHttpBasicConfigurer.disable();
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    );
                })
                //요청 주소에 대한 권한 설정
                .authorizeHttpRequests((authorizeRequests) -> {
                    //'/' 요청은 모든 사용자 이용 가능
                    authorizeRequests.requestMatchers("/").permitAll();
                    //css, js, images, upload 같은 정적 리소스 역시 권한 처리 필수
                    authorizeRequests.requestMatchers("/upload/**").permitAll();
                    //게시판 기능은 권한을 가지고 있는 사용자만 사용 가능
                    authorizeRequests.requestMatchers("/board/**").hasAnyRole("ADMIN", "USER");
                    //관리자 페이지는 관리자만 사용 가능
                    authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN");
                    //회원 가입, 로그인, 아이디 중복 체크 등 요청은 모든 사용자 이용 가능
                    authorizeRequests.requestMatchers("/user/join").permitAll();
                    authorizeRequests.requestMatchers("/user/id-check").permitAll();
                    authorizeRequests.requestMatchers("/user/login").permitAll();
                    authorizeRequests.requestMatchers("/api/**").permitAll();
                    //이외의 요청은 인증된 사용자만 사용 가능
                    //리액트에 route 설정한 /, /login, /join, /board-list 등
                    authorizeRequests.anyRequest().permitAll();
                })
                .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class)
                .build();
    }

}
