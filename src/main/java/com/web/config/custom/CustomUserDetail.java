package com.web.config.custom;

import com.web.entity.SysUser;
import com.web.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/30
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetail implements UserDetailsService {

    private final SysUserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SysUser> userOptional = service.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("用户不存在");
        }
        SysUser sysUser = userOptional.get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//        User.builder()

        List<SimpleGrantedAuthority> authorities = sysUser.getRoles().stream()
                .map(sysRole -> new SimpleGrantedAuthority(sysRole.getRoleCode())).collect(Collectors.toList());

        return new User(username, encoder.encode(sysUser.getPassword()), authorities);
    }
}
