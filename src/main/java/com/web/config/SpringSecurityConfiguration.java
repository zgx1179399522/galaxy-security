package com.web.config;

import com.web.config.custom.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;


/**
 * @Author zhang guoxiang
 * @Date 2022/7/30
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfiguration {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomSessionInformationExpiredStrategy customSessionInformationExpiredStrategy;
    private final CustomAccessDecisionManager customAccessDecisionManager;
    private final CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
    private final CustomAbstractSecurityInterceptor customAbstractSecurityInterceptor;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeHttpRequests()
                //自定义动态路径权限处理
                //customFilterInvocationSecurityMetadataSource 路径权限设置
                //customAccessDecisionManager 权限校验设置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        object.setAccessDecisionManager(customAccessDecisionManager);
                        return object;
                    }
                })
                .anyRequest()
                .authenticated();


        //自定义异常处理
        http.exceptionHandling()
                //匿名用户访问 无权限 处理
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);

        //自定义登录处理
        http.formLogin()
                .permitAll()
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler);

        //自定义退出处理
        http.logout()
                .permitAll()
                .logoutSuccessHandler(customLogoutHandler)
                .deleteCookies("JSESSIONID");

        //自定义session管理
        http.sessionManagement()
                .maximumSessions(1)
                //自定义session过期处理 customSessionInformationExpiredStrategy
                .expiredSessionStrategy(customSessionInformationExpiredStrategy);

        http.addFilterBefore(customAbstractSecurityInterceptor,FilterSecurityInterceptor.class);

        return http.build();
    }
}
