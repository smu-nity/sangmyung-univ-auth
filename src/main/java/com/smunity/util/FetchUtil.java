package com.smunity.util;

import com.smunity.dto.AuthRequestDto;
import com.smunity.exception.AuthException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static com.smunity.exception.code.AuthErrorCode.AUTH_FETCH_FAILURE;
import static com.smunity.exception.code.AuthErrorCode.AUTH_INVALID_FORMAT;

public class FetchUtil {

    private static final String BASE_URL = "https://smul.smu.ac.kr/";

    protected static JSONObject fetchInfo(String username, String password) {
        JSONArray response = fetchData(AuthRequestDto.of(username, password), "UsrSchMng/selectStdInfo.do", "dsStdInfoList");
        return response.getJSONObject(0);
    }

    protected static JSONArray fetchCourses(String username, String password) {
        return fetchData(AuthRequestDto.of(username, password), "UsrRecMatt/list.do", "dsRecMattList");
    }

    private static JSONArray fetchData(AuthRequestDto requestDto, String url, String key) {
        JSONObject response = fetchData(requestDto, url);
        try {
            return response.getJSONArray(key);
        } catch (JSONException e) {
            throw new AuthException("Failed to get JSONArray for key '%s'. Response: %s".formatted(key, response), AUTH_INVALID_FORMAT);
        }
    }

    private static JSONObject fetchData(AuthRequestDto requestDto, String url) {
        Map<String, String> session = LoginUtil.login(requestDto);
        try {
            HttpURLConnection connection = createConnection(BASE_URL + url, session);
            connection.getOutputStream().write(createRequestData(requestDto));
            return readResponse(connection);
        } catch (IOException e) {
            throw new AuthException("Failed to fetch data from URL: '%s'.".formatted(url), AUTH_FETCH_FAILURE);
        }
    }

    private static HttpURLConnection createConnection(String url, Map<String, String> session) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        session.forEach((key, value) -> connection.addRequestProperty("Cookie", key + "=" + value));
        connection.setDoOutput(true);
        return connection;
    }

    private static byte[] createRequestData(AuthRequestDto requestDto) {
        return "@d#=@d1#&@d1#tp=dm&_AUTH_MENU_KEY=usrCPsnlInfoUpd-STD&@d1#strStdNo=".concat(requestDto.username()).getBytes();
    }

    private static JSONObject readResponse(HttpURLConnection connection) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null)
                response.append(line);
        }
        return new JSONObject(response.toString());
    }
}
