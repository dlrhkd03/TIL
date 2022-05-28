package pang.spring.databasepractice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pang.spring.databasepractice.exception.UserErrorCode;

@Getter
@Setter
@AllArgsConstructor
public class UserErrorResponse {
    private UserErrorCode userErrorCode;
    private String message;
}
