package com.vidcentral.api.application.member;

import static com.vidcentral.global.common.util.TokenConstant.*;
import static com.vidcentral.global.error.model.ErrorMessage.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vidcentral.api.application.auth.AuthorizationService;
import com.vidcentral.api.application.auth.JwtProviderService;
import com.vidcentral.api.application.media.MediaService;
import com.vidcentral.api.domain.auth.repository.TokenRepository;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.member.repository.MemberRepository;
import com.vidcentral.api.dto.request.auth.LoginRequest;
import com.vidcentral.api.dto.request.member.SignUpRequest;
import com.vidcentral.api.dto.response.auth.LoginResponse;
import com.vidcentral.api.dto.response.auth.TokenSaveResponse;
import com.vidcentral.api.dto.response.member.MemberInfoResponse;
import com.vidcentral.global.error.exception.BadRequestException;
import com.vidcentral.global.error.exception.ConflictException;
import com.vidcentral.global.error.exception.NotFoundException;
import com.vidcentral.support.MemberFixture;

import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock
	private JwtProviderService jwtProviderService;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private TokenRepository tokenRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		AuthorizationService authorizationService = new AuthorizationService(
			jwtProviderService,
			tokenRepository
		);

		MemberReadService memberReadService = new MemberReadService(
			passwordEncoder,
			memberRepository);

		MemberWriteService memberWriteService = new MemberWriteService(
			memberRepository,
			passwordEncoder);

		memberService = new MemberService(
			memberWriteService,
			memberReadService,
			authorizationService,
			mock(MediaService.class));
	}

	@DisplayName("[✅ SUCCESS] signUpMember: 성공적으로 사용자가 회원가입을 했습니다.")
	@Test
	void signUpMember_void_success() {
		// GIVEN
		SignUpRequest signUpRequest = MemberFixture.createSignUpRequest();

		given(memberRepository.existsMemberByEmail(any(String.class))).willReturn(false);
		given(memberRepository.existsMemberByNickname(any(String.class))).willReturn(false);
		given(passwordEncoder.encode(any(String.class))).willReturn("testEncodedPassword");

		// WHEN
		memberService.signUpMember(signUpRequest);

		// THEN
		verify(memberRepository).save(any(Member.class));
	}

	@DisplayName("[❎ FAILURE] signUpMember: 해당 이메일은 이미 존재하는 사용자 이메일입니다.")
	@Test
	void signUpMember_email_ConflictException_failure() {
		// GIVEN
		SignUpRequest signUpRequest = MemberFixture.createSignUpRequest();

		given(memberRepository.existsMemberByEmail(any(String.class))).willReturn(true);

		// WHEN & THEN
		assertThatThrownBy(() -> memberService.signUpMember(signUpRequest))
			.isInstanceOf(ConflictException.class)
			.hasMessage(FAILED_EMAIL_DUPLICATION_ERROR.getMessage());
	}

	@DisplayName("[❎ FAILURE] signUpMember: 해당 닉네임은 이미 존재하는 사용자 닉네임입니다.")
	@Test
	void signUpMember_nickname_ConflictException_failure() {
		// GIVEN
		SignUpRequest signUpRequest = MemberFixture.createSignUpRequest();

		given(memberRepository.existsMemberByNickname(any(String.class))).willReturn(true);

		// WHEN & THEN
		assertThatThrownBy(() -> memberService.signUpMember(signUpRequest))
			.isInstanceOf(ConflictException.class)
			.hasMessage(FAILED_NICKNAME_DUPLICATION_ERROR.getMessage());
	}

	@DisplayName("[✅ SUCCESS] loginMember: 성공적으로 사용자가 로그인을 했습니다.")
	@Test
	void loginMember_LoginResponse_success() {
		// GIVEN
		String accessToken = "testAccessToken";
		String refreshToken = "testRefreshToken";
		Member loginMember = MemberFixture.createMemberEntity();
		LoginRequest loginRequest = MemberFixture.createLoginRequest();

		given(memberRepository.findMemberByEmail(any(String.class))).willReturn(Optional.of(loginMember));
		given(passwordEncoder.matches(any(String.class), any(String.class))).willReturn(true);

		given(jwtProviderService.generateAccessToken(any(String.class), any(String.class))).willReturn(accessToken);
		given(jwtProviderService.generateRefreshToken(any(String.class))).willReturn(refreshToken);

		doNothing().when(tokenRepository).saveToken(any(String.class), any(TokenSaveResponse.class));

		// WHEN
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		LoginResponse actualLoginResponse = memberService.loginMember(httpServletResponse, loginRequest);

		// THEN
		assertThat(actualLoginResponse.accessToken()).isEqualTo(accessToken);
		assertThat(actualLoginResponse.refreshToken()).isEqualTo(refreshToken);

		verify(httpServletResponse).setHeader(ACCESS_TOKEN_HEADER, accessToken);
		verify(httpServletResponse).addCookie(any());
	}

	@DisplayName("[❎ FAILURE] loginMember: 존재하지 않는 사용자 이메일로 로그인 요청을 했습니다.")
	@Test
	void loginMember_NotFoundException_failure() {
		// GIVEN
		Member loginMember = MemberFixture.createMemberEntity();
		LoginRequest loginRequest = MemberFixture.createLoginRequest(loginMember);

		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

		given(memberRepository.findMemberByEmail(any(String.class))).willReturn(Optional.empty());

		// WHEN & THEN
		assertThatThrownBy(() -> memberService.loginMember(httpServletResponse, loginRequest))
			.isInstanceOf(NotFoundException.class)
			.hasMessage(FAILED_MEMBER_NOT_FOUND_ERROR.getMessage());
	}

	@DisplayName("[❎ FAILURE] loginMember: 잘못된 비밀번호로 로그인 요청을 했습니다.")
	@Test
	void loginMember_BadRequestException_failure() {
		// GIVEN
		Member loginMember = MemberFixture.createMemberEntity();
		LoginRequest loginRequest = MemberFixture.createLoginRequest(loginMember);

		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

		given(memberRepository.findMemberByEmail(any(String.class))).willReturn(Optional.of(loginMember));
		given(passwordEncoder.matches(any(String.class), any(String.class))).willReturn(false);

		// WHEN & THEN
		assertThatThrownBy(() -> memberService.loginMember(httpServletResponse, loginRequest))
			.isInstanceOf(BadRequestException.class)
			.hasMessage(FAILED_INVALID_PASSWORD_ERROR.getMessage());
	}

	@DisplayName("[✅ SUCCESS] searchMemberInfo: 성공적으로 사용자 정보 목록을 조회했습니다.")
	@Test
	void searchMemberInfo_MemberInfoResponse_success() {
		// GIVEN
		Member member = MemberFixture.createMemberEntity();

		// WHEN
		MemberInfoResponse actualMemberInfoResponse = MemberFixture.createMemberInfoResponse();

		// THEN
		assertThat(actualMemberInfoResponse.nickname()).isEqualTo(member.getNickname());
		assertThat(actualMemberInfoResponse.introduce()).isEqualTo(member.getIntroduce());
	}

	@DisplayName("[❎ FAILURE] searchMemberInfo: 존재하지 않는 사용자 이메일로 조회를 요청했습니다.")
	@Test
	void searchMemberInfo_NotFoundException_failure() {
		// GIVEN
		Long memberId = 1L;

		given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

		// WHEN & THEN
		assertThatThrownBy(() -> memberService.searchMemberInfo(memberId))
			.isInstanceOf(NotFoundException.class)
			.hasMessage(FAILED_MEMBER_NOT_FOUND_ERROR.getMessage());
	}
}
