package com.web.service;

import com.web.entity.SysUser;
import com.web.repo.SysUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/30
 */
@Service
@RequiredArgsConstructor
public class SysUserService {

    private final SysUserRepo repo;

    public Optional<SysUser> findByUsername(String username){
        return repo.findByUsername(username);
    }
}
