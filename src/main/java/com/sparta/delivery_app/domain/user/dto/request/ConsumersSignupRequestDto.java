package com.sparta.delivery_app.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@AllArgsConstructor
public class ConsumersSignupRequestDto {

    @Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일의 입력 값이 없습니다.")
    @Length(max = 255, message = "이메일 입력 범위를 초과하였습니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자 조합 8자리 이상이어야 합니다.")
    @NotBlank(message = "비밀번호의 입력 값이 없습니다.")
    @Length(min = 8, max = 200, message = "비밀번호 입력 조건을 맞춰주세요")
    private String password;

    @NotBlank(message = "이름을 입력해 주세요")
    @Length(min = 2, max = 10, message = "성함이 회원가입 조건에 맞지 않습니다.")
    private String name;
    @NotBlank(message = "닉네임을 입력해 주세요")
    @Length(min = 3, max = 20, message = "닉네임이 회원가입 조건에 맞지 않습니다.")
    private String nickName;

    @NotBlank(message = "주소를 입력해 주세요")
    private String address;
}
