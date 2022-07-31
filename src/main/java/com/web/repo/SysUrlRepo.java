package com.web.repo;

import com.web.entity.SysUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/31
 */
public interface SysUrlRepo extends JpaRepository<SysUrl, Long> {

    Optional<SysUrl> findByUrl(String url);
}
