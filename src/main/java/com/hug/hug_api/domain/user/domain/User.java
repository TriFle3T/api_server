package com.hug.hug_api.domain.user.domain;

import com.hug.hug_api.domain.diary.dto.DiaryDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Document(collection = "user")
@Data
@Builder
public class User implements UserDetails {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private int counter;

    private String name;
    private String password;

    private List<DiaryDto> diaries;

    private Set<GrantedAuthority> authorities;

    private boolean enabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
