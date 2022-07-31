package com.web.service;

import com.web.entity.SysUrl;
import com.web.repo.SysUrlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/31
 */
@Service
@RequiredArgsConstructor
public class SysUrlService {

    private final SysUrlRepo repo;

    public Optional<SysUrl> findByUrl(String url) {
        return repo.findByUrl(url);
    }

}
