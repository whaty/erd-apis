package com.java2e.martin.erd.config;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Set;

/**
 * @author 狮少
 * @version 1.0
 * @date 2019/10/25
 * @describtion SecurityContextUtil
 * @since 1.0
 */
@UtilityClass
public class SecurityContextUtil {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Set<String> getAuthorities() {
        return AuthorityUtils.authorityListToSet(getAuthentication().getAuthorities());
    }

    public String getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {

            return ((UserDetails) principal).getUsername();

        }

        if (principal instanceof Principal) {

            return ((Principal) principal).getName();

        }

        return String.valueOf(principal);
    }
}
