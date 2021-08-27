package com.fakebook.dongheon.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
	private static final String BAD_CREDENTIAL_MESSAGE = "아이디 혹은 비밀번호를 확인해주세요";

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		if (exception instanceof BadCredentialsException) {
			alertMessage(response, BAD_CREDENTIAL_MESSAGE);
		}
	}

	private static void alertMessage(HttpServletResponse response, String message) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('" + message + "');location.href='/'; </script>");
		out.flush();
	}
}
