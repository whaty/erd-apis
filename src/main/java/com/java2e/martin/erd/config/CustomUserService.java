package com.java2e.martin.erd.config;

import com.java2e.martin.erd.entity.SysPermission;
import com.java2e.martin.erd.entity.SysUser;
import com.java2e.martin.erd.mapper.SysPermissionMapper;
import com.java2e.martin.erd.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 狮少
 * @version 1.0
 * @date 2020/11/10
 * @describtion CustomUserService
 * @since 1.0
 */
@Service
public class CustomUserService implements UserDetailsService { //自定义UserDetailsService 接口

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUser user = sysUserMapper.findByUserName(username);
        if (user != null) {
            List<SysPermission> permissions = sysPermissionMapper.findByAdminUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (SysPermission permission : permissions) {
                if (permission != null && permission.getName()!=null) {

                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }

}
