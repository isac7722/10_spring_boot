package com.ohgiraffers.securitysession.auth.model;

import com.ohgiraffers.securitysession.user.model.dto.LoginUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class AuthDetails implements UserDetails {

    private LoginUserDTO loginUserDTO;

    public AuthDetails(LoginUserDTO loginUserDTO) {
        this.loginUserDTO = loginUserDTO;
    }

    public LoginUserDTO getLoginUserDTO() {
        return loginUserDTO;
    }

    public void setLoginUserDTO(LoginUserDTO loginUserDTO) {
        this.loginUserDTO = loginUserDTO;
    }



    @Override // 권한 있는지 없는지 체크하는 메소드
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        loginUserDTO.getRole().forEach(role -> authorities.add(() -> role));

        return authorities;
    }

    @Override // 사용자의 패스워드 정보를 반환하는 메소드
    public String getPassword() {
        return loginUserDTO.getPassword();
    }


    // 사용자의 아이디를 반환하는 메소드
    // UsernamePasswordAuthenticationToken과 사용자의 아이디를 받는다
    @Override
    public String getUsername() {
        return loginUserDTO.getUserName();
    }


    // 계정 만료 여부를 표현하는 메서드
    // false면 해당 계정을 사용할 수 없다
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    // 계정이 잠겨 있는 상태를 확인하는 메서드
    // 오랜기간 비접속으로 휴면처리
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    // 탈퇴 계정 여부를 표현하는 메서드
    // false이면 해당 계정을 사용할 수 없다
    // 보통 데이터 삭제는 즉시하는 것이 아닌 일정 기간 보관후 삭제를 한다
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 비활성화 여부로 사용자가 사용할 수 없는 상테
    // false이면 계정을 사용할 수 없다
    @Override
    public boolean isEnabled() {
        return true;
    }
}
