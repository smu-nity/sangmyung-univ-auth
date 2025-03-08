package com.smunity.util;

import com.smunity.dto.AuthRequestDto;
import com.smunity.exception.AuthException;
import com.smunity.exception.code.AuthErrorCode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

public class LoginUtil {

    private static final String LOGIN_URL = "https://smsso.smu.ac.kr/Login.do";
    private static final String BASE_URL = "https://smul.smu.ac.kr/";

    protected static Map<String, String> login(AuthRequestDto requestDto) {
        try {
            Connection.Response loginResponse = executeLogin(requestDto);
            return getSessionCookies(loginResponse);
        } catch (IOException e) {
            throw new AuthException("Failed to Login.", AuthErrorCode.AUTH_LOGIN_FAIL);
        }
    }

    private static Connection.Response executeLogin(AuthRequestDto requestDto) throws IOException {
        Connection.Response response = Jsoup.connect(LOGIN_URL)
                .data("user_id", requestDto.username())
                .data("user_password", requestDto.password())
                .method(Connection.Method.POST)
                .execute();
        if (response.url().toString().equals(LOGIN_URL))
            throw new AuthException("ID and password do not match.", AuthErrorCode.AUTH_UNAUTHORIZED);
        return response;
    }

    private static Map<String, String> getSessionCookies(Connection.Response loginResponse) throws IOException {
        Connection.Response response = Jsoup.connect(BASE_URL + "index.do")
                .method(Connection.Method.GET)
                .cookies(loginResponse.cookies())
                .execute();
        if (!response.url().toString().equals(BASE_URL))
            throw new AuthException("Login failed, exceeded 5 attempts.", AuthErrorCode.AUTH_EXCEEDED_LOGIN_ATTEMPTS);
        return response.cookies();
    }
}
