package com.vidcentral.global.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EnableConfigurationProperties(TokenConfig.class)
@TestPropertySource(properties = {
	"jwt.iss=test_issuer",
	"jwt.secret-access-key=c29tZSByYW5kb20gc3RyaW5nIHN0cmluZwAAc29tZSByYW5kb20gc3RyaW5nIHN0cmluZwAA",
	"jwt.access-token-expire=300000",
	"jwt.refresh-token-expire=604800000"
})
class TokenConfigTest {

	@Autowired
	TokenConfig tokenConfig;

	@DisplayName("[✅ SUCCESS] TokenConfig 생성자의 프로퍼티 값이 올바르게 설정되었습니다.")
	@Test
	void constructor_tokenConfigProperties_success() {
		assertEquals("test_issuer", tokenConfig.getIss());
		assertEquals(300000L, tokenConfig.getAccessTokenExpire());
		assertEquals(604800000L, tokenConfig.getRefreshTokenExpire());
		assertNotNull(tokenConfig.getSecretKey());
	}
}
