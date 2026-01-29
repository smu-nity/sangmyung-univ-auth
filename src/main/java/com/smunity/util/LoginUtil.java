package com.smunity.util;

import com.smunity.dto.AuthRequestDto;
import com.smunity.exception.AuthClientException;
import com.smunity.exception.AuthServerException;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import static com.smunity.exception.code.AuthErrorCode.*;

public class LoginUtil {

    private static final String LOGIN_URL = "https://smsso.smu.ac.kr/Login.do";
    private static final String BASE_URL = "https://smul.smu.ac.kr/";
    private static final int TIMEOUT = 30000;

    protected static Map<String, String> login(AuthRequestDto requestDto) {
        try {
            Connection.Response loginResponse = executeLogin(requestDto);
            return getSessionCookies(loginResponse);
        } catch (HttpStatusException e) {
            throw new AuthClientException(AUTH_PASSWORD_EXPIRED);
        } catch (SocketTimeoutException e) {
            throw new AuthServerException(SMU_LOGIN_TIMEOUT, e);
        } catch (IOException e) {
            throw new AuthServerException(SMU_LOGIN_FAILURE, e);
        }
    }

    private static Connection.Response executeLogin(AuthRequestDto requestDto) throws IOException {
        Connection.Response response = Jsoup.connect(LOGIN_URL)
                .timeout(TIMEOUT)
                .data("user_id", requestDto.username())
                .data("user_password", requestDto.password())
                .method(Connection.Method.POST)
                .execute();
        if (response.url().toString().equals(LOGIN_URL))
            throw new AuthClientException(AUTH_UNAUTHORIZED);
        return response;
    }

    private static Map<String, String> getSessionCookies(Connection.Response loginResponse) throws IOException {
        Connection.Response response = Jsoup.connect(BASE_URL + "index.do")
                .timeout(TIMEOUT)
                .method(Connection.Method.GET)
                .cookies(loginResponse.cookies())
                .execute();
        if (!response.url().toString().equals(BASE_URL))
            throw new AuthClientException(AUTH_EXCEEDED_LOGIN_ATTEMPTS);
        return response.cookies();
    }
}
