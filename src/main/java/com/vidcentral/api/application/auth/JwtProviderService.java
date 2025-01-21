package com.vidcentral.api.application.auth;

import static com.vidcentral.global.common.util.GlobalConstant.*;
import static com.vidcentral.global.common.util.TokenConstant.*;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.member.repository.MemberRepository;
import com.vidcentral.global.common.util.CookieUtils;
import com.vidcentral.global.config.TokenConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtProviderService {

	private final TokenConfig tokenConfig;
	private final MemberRepository memberRepository;

	public String generateAccessToken(String email, String nickname) {
		final Date issuedDate = new Date();
		final Date expiredDate = new Date(issuedDate.getTime() + tokenConfig.getAccessTokenExpire());

		return buildJwt(issuedDate, expiredDate)
			.claim(MEMBER_EMAIL, email)
			.claim(MEMBER_NICKNAME, nickname)
			.compact();
	}

	public String generateRefreshToken(String email) {
		final Date issuedDate = new Date();
		final Date expiredDate = new Date(issuedDate.getTime() + tokenConfig.getRefreshTokenExpire());

		return buildJwt(issuedDate, expiredDate)
			.claim(MEMBER_EMAIL, email)
			.compact();
	}

	@Transactional
	public String reGenerateToken(String refreshToken, HttpServletResponse httpServletResponse) {
		final Claims claims = parseClaimsByToken(refreshToken);
		final String memberEmail = claims.get(MEMBER_EMAIL, String.class);
		final Member member = memberRepository.findMemberByEmail(memberEmail)
			.orElseThrow(() -> new IllegalArgumentException("[❎ ERROR] 요청하신 사용자를 찾을 수 없습니다."));

		validateRefreshToken(refreshToken, member.getRefreshToken());

		final String newAccessToken = generateAccessToken(member.getEmail(), member.getNickname());
		final String newRefreshToken = generateRefreshToken(member.getEmail());
		member.updateRefreshToken(newRefreshToken);

		httpServletResponse.setHeader(ACCESS_TOKEN_HEADER, newAccessToken);

		final Cookie refreshTokenCookie = CookieUtils.generateRefreshTokenCookie(REFRESH_TOKEN_COOKIE, newRefreshToken);
		httpServletResponse.addCookie(refreshTokenCookie);

		return newAccessToken;
	}

	public String extractToken(String header, HttpServletRequest httpServletRequest) {
		final String token = httpServletRequest.getHeader(header);

		if (token == null || !token.startsWith(BEARER)) {
			log.warn("[✅ LOGGER] {}는 NULL이거나 BEARER가 아닙니다.", header);
			return null;
		}

		return token.replaceFirst(BEARER, BLANK).trim();
	}

	public AuthMember extractAuthMemberByAccessToken(String accessToken) {
		final Claims claims = parseClaimsByToken(accessToken);
		final String memberEmail = claims.get(MEMBER_EMAIL, String.class);
		final String memberNickname = claims.get(MEMBER_NICKNAME, String.class);

		return AuthMember.createAuthMember(memberEmail, memberNickname);
	}

	public boolean isUsable(String token) {
		try {
			Jwts.parser()
				.verifyWith(tokenConfig.getSecretKey())
				.build()
				.parseSignedClaims(token);

			return true;
		} catch (ExpiredJwtException expiredJwtException) {
			log.warn("[✅ LOGGER] JWT 토큰이 만료되었습니다.");
		} catch (IllegalArgumentException illegalArgumentException) {
			log.warn("[✅ LOGGER] JWT 토큰이 존재하지 않습니다.");
			throw new IllegalArgumentException("[❎ ERROR] JWT 토큰이 존재하지 않습니다.");
		} catch (Exception exception) {
			log.warn("[✅ LOGGER] 유효하지 않은 토큰입니다.");
			throw new IllegalArgumentException("[❎ ERROR] 유효하지 않은 JWT 토큰입니다.");
		}

		return false;
	}

	private JwtBuilder buildJwt(Date issuedDate, Date expiredDate) {
		return Jwts.builder()
			.issuer(tokenConfig.getIss())
			.issuedAt(issuedDate)
			.expiration(expiredDate)
			.signWith(tokenConfig.getSecretKey());
	}

	private Claims parseClaimsByToken(String token) {
		return Jwts.parser()
			.verifyWith(tokenConfig.getSecretKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	private void validateRefreshToken(String reGenerateRefreshToken, String savedRefreshToken) {
		if (!reGenerateRefreshToken.equals(savedRefreshToken)) {
			log.warn("[✅ LOGGER] 유효하지 않은 리프레시 토큰입니다.");
			throw new IllegalArgumentException("[❎ ERROR] JWT 토큰이 존재하지 않습니다.");
		}
	}
}
