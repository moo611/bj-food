package com.example.bjfood.config.auth;

import com.example.bjfood.domain.SfUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class MyUserDetails implements UserDetails {

    private final SfUser user;

    public MyUserDetails(SfUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将用户角色转换为 GrantedAuthority
        // 这里假设 user.getRole() 返回的是用户角色名称
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // 如果需要，可以根据 user 实体类的字段做相应判断
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // 如果需要，可以根据 user 实体类的字段做相应判断
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 如果需要，可以根据 user 实体类的字段做相应判断
    }

    @Override
    public boolean isEnabled() {
//        return user.isEnabled();  // 通过 user 实体类判断用户是否启用
        return true;
    }
}
