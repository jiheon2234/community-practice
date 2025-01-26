package com.community.domain.user.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginBasicReq(

	@NotEmpty(message = "이메일필수!!!")
	@Email(message = "Invalid format")
	String email,

	@NotEmpty(message = "비밀번호필수!!!")
	@Size(min = 2, message = "비밀번호는 최소한 2자이상")
	String password

) {
}
