# sangmyung-univ-auth ![Java versions](https://img.shields.io/badge/Java-17-007396?style=round-square&logo=java&logoColor=white) ![License](https://img.shields.io/badge/license-MIT-green) ![Release](https://img.shields.io/badge/release-1.0.3-red)

**상명대학교 학생 인증 라이브러리**

## Features

- 상명대학교 학생 여부를 간편하게 확인하는 라이브러리입니다.
- 학생 인증 방식은 상명대학교 포털 SSO 인증 방식입니다.

## Installation

### Gradle (Short)

```gradle
implementation 'kr.co.smunity:sangmyung-univ-auth:1.0.3'
```

### Maven

```xml

<dependency>
    <groupId>kr.co.smunity</groupId>
    <artifactId>sangmyung-univ-auth</artifactId>
    <version>1.0.3</version>
</dependency>
```

## Usage

```java
import com.smunity.AuthManager;
import com.smunity.dto.AuthResponseDto;
import com.smunity.exception.AuthException;

public class AuthExample {

    public static void main(String[] args) {
        try {
            // 학번과 비밀번호로 인증
            String username = "201911019";
            String password = "your_password";

            // 인증 실행
            AuthResponseDto responseDto = AuthManager.authenticate(username, password);

            // 인증 성공: AuthResponseDto[username=201911019, name=최현민, email=hyunmin-choi@naver.com, department=컴퓨터과학전공, secondDepartment=null, isDoubleMajor=false]
            System.out.println("인증 성공: " + responseDto);
        } catch (AuthException e) {
            // 인증 실패: Username and password do not match.
            System.out.println("인증 실패: " + e.getMessage());
        }
    }
}
```

## Sangmyung University Auth API

- **Request**
    - **URL**
      ```text 
      https://smunity.co.kr/api/v1/auth
      ```
    - **Method**
      `POST`
    - **Body**
      ```json
      {
          "username": "<학번>",
          "password": "<비밀번호>"
      }
      ```

- **Response**
    - **Success**: `200 OK`
      ```json
      {
          "username": "201911019",
          "name": "최현민",
          "email": "hyunmin-choi@naver.com",
          "department": "컴퓨터과학전공",
          "secondDepartment": null,
          "isDoubleMajor": false
      }
      ```
    - **Fail**: `401 Unauthorized`
      ```json
      {
          "code": "AUTH001",
          "message": "학번 및 비밀번호가 일치하지 않습니다."
      }
      ```
