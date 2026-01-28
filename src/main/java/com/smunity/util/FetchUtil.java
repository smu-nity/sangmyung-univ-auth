package com.smunity.util;

import com.smunity.dto.AuthRequestDto;
import com.smunity.exception.AuthServerException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;

import static com.smunity.exception.code.AuthErrorCode.SMU_FETCH_FAILURE;
import static com.smunity.exception.code.AuthErrorCode.SMU_FETCH_TIMEOUT;

public class FetchUtil {

    private static final String BASE_URL = "https://smul.smu.ac.kr/";
    private static final int TIMEOUT = 30000;

    public static JSONObject fetchInfo(String username, String password) {
        return fetchData(AuthRequestDto.of(username, password), "UsrSchMng/selectStdInfo.do");
    }

    public static JSONObject fetchCourses(String username, String password) {
        return fetchData(AuthRequestDto.of(username, password), "UsrRecMatt/list.do");
    }

    private static JSONObject fetchData(AuthRequestDto requestDto, String url) {
        try {
            HttpURLConnection connection = createConnection(BASE_URL + url, LoginUtil.login(requestDto));
            connection.getOutputStream().write(createRequestData(requestDto));
            return readResponse(connection);
        } catch (SocketTimeoutException e) {
            throw new AuthServerException("Fetch data timed out from URL: '%s'.".formatted(url), SMU_FETCH_TIMEOUT);
        } catch (IOException e) {
            throw new AuthServerException("Failed to fetch data from URL: '%s'.".formatted(url), SMU_FETCH_FAILURE);
        }
    }


    private static HttpURLConnection createConnection(String url, Map<String, String> session) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(TIMEOUT);
        connection.setReadTimeout(TIMEOUT);
        session.forEach((key, value) -> connection.addRequestProperty("Cookie", key + "=" + value));
        connection.setDoOutput(true);
        return connection;
    }

    private static byte[] createRequestData(AuthRequestDto requestDto) {
        return "@d#=@d1#&@d1#tp=dm&_AUTH_MENU_KEY=usrCPsnlInfoUpd-STD&@d1#strStdNo=".concat(requestDto.username()).getBytes();
    }

    private static JSONObject readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return new JSONObject(reader.lines().collect(Collectors.joining()));
        }
    }
}
