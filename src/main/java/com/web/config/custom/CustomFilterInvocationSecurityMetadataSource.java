package com.web.config.custom;

import com.web.entity.SysRole;
import com.web.entity.SysUrl;
import com.web.service.SysUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/31
 */
@Component
@RequiredArgsConstructor
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final SysUrlService sysUrlService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String requestUrl = filterInvocation.getRequestUrl();

        Optional<SysUrl> sysUrlOptional = sysUrlService.findByUrl(requestUrl);
        if (!sysUrlOptional.isPresent()){
            return null;
        }
        SysUrl sysUrl = sysUrlOptional.get();
        String[] objects = sysUrl.getRoles().stream().map(SysRole::getRoleCode).toArray(String[]::new);
        return SecurityConfig.createList(objects);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
