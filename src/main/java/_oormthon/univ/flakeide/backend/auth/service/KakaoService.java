package _oormthon.univ.flakeide.backend.auth.service;

import _oormthon.univ.flakeide.backend.auth.api.dto.Token;
import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.UserType;
import _oormthon.univ.flakeide.backend.auth.domain.repository.UserRepository;
import _oormthon.univ.flakeide.backend.auth.api.dto.KakaoTokenResDto;
import _oormthon.univ.flakeide.backend.auth.api.dto.KakaoUserInfo;
import io.jsonwebtoken.Claims;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class KakaoService {

    @Autowired
    private final KakaoProperties kakaoProperties;
    @Autowired
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    private final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
    private final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";

    public KakaoService(KakaoProperties kakaoProperties, UserRepository userRepository, TokenProvider tokenProvider) {
        this.kakaoProperties = kakaoProperties;
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public String getAccessToken(String code) {
        KakaoTokenResDto kakaoTokenResDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .path("/oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", kakaoProperties.getClientId())
                .queryParam("code", code)
                .queryParam("scope", "account_email")
                .build(true))
            .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
            .retrieve()
            .bodyToMono(KakaoTokenResDto.class)
            .block();
        return kakaoTokenResDto.getAccessToken();
    }

    public KakaoUserInfo getUserInfo(String accessToken) {

        KakaoUserInfo userInfo = WebClient.create(KAUTH_USER_URL_HOST)
            .get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .path("/v2/user/me")
                .build(true))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
            .retrieve()
            .bodyToMono(KakaoUserInfo.class)
            .block();

        return userInfo;
    }

    @Transactional
    public Token loginOrSignUp(String kakaoAccessToken) {
        KakaoUserInfo userInfo = getUserInfo(kakaoAccessToken);
        Long id = userInfo.getId();

        User user = userRepository.findById(id).orElseGet(() ->
            userRepository.save(User.builder()
                .id(id)
                .userType(UserType.USER)
                .name(userInfo.getKakaoAccount().getProfile().getNickname())
                .email(userInfo.getKakaoAccount().getEmail())
                .build())

        );

        return tokenProvider.createToken(user);
    }

    @Transactional
    public User signUpSnowflake(String token) {
        Claims claims = tokenProvider.parseJwt(token);
        Long id = Long.valueOf(claims.getSubject());

        User user = userRepository.findById(id).orElseThrow();
        user.updateUserType(UserType.SNOWFLAKE);
        return user;
    }

    @Transactional
    public User signUpSnowPine(String token) {
        Claims claims = tokenProvider.parseJwt(token);
        Long id = Long.valueOf(claims.getSubject());

        User user = userRepository.findById(id).orElseThrow();
        user.updateUserType(UserType.SNOW_PINE);
        return user;
    }
}
