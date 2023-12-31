package com.bit.springboard.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
    //자원 서버에서 제공한 원시 데이터 형식
    /*
    * {
    *   kakao_account: {
    *       profile: {
    *           nickname: ,
    *       } ,
    *       email: ,
    *   }
    * }
    * */
    Map<String, Object> attributes;

    //게시판에서 사용할 profile_nickname, account_email
    //kakao_accoutn 키로 사용할 정보가 담긴 객체를 가져와서 담아준다.
    Map<String, Object> properties;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.properties = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getProviderId() {
        return this.attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return this.properties.get("email").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> profile = (Map<String, Object>) properties.get("profile");

        return profile.get("nickname").toString();
    }
}
