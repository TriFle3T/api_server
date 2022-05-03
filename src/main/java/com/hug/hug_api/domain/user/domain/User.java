package com.hug.hug_api.domain.user.domain;

import com.hug.hug_api.domain.diary.Diary;
import com.hug.hug_api.domain.result.MainScreenResult;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Document(collection = "user")
@Data
public class User implements UserDetails {

    @Id
    private String id;

    private String email;
    private String nickname;
    private String password;

    private List<Diary> diaries;

    private List<MainScreenResult> result;

    private Set<GrantedAuthority> authorities;

    private boolean enabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
