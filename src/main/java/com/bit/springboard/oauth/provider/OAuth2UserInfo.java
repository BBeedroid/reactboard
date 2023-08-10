package com.bit.springboard.oauth.provider;

//여러 가지 소셜 로그인에 대응하기 위해 인터페이스로 선언한다.
public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
