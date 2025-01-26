package com.community.domain.reply.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GeneratePathUtilTest {

	@Test
	@DisplayName("최솟값은 4자리의 한글로 표현된다")
	void test1() {
		long l = 0;
		String s = GeneratePathUtil.changeCurrentMillsToHANGUALPATH(l);
		assertThat(s).isEqualTo("가가가가/");
	}

	@Test
	@DisplayName("최댓값도 4자리의 한글로 표현된다.")
	void test2() {
		long l = Long.MAX_VALUE;
		String s = GeneratePathUtil.changeCurrentMillsToHANGUALPATH(l);
		System.out.println("s = " + s);
		assertThat(s.matches("^[가-힣]{4}/$")).isTrue();
	}
}