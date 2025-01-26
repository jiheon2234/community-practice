package com.community.domain.reply.entity;

public class GeneratePathUtil {

	private static final int HANGUL_START = '가';
	private static final int HANGUL_COUNT = '힣' - '가' + 1; //55203-44032 + 1 = 11,172

	/**
	 * @param q currentTimeMillis
	 * @return 총 5글자 반환 (한글 4글자로 구성된 문자열 + /)
	 */
	public static String changeCurrentMillsToHANGUALPATH(long q) {
		char last = (char)(HANGUL_START + (q % HANGUL_COUNT));
		q /= HANGUL_COUNT;
		char third = (char)(HANGUL_START + (q % HANGUL_COUNT));
		q /= HANGUL_COUNT;
		char second = (char)(HANGUL_START + (q % HANGUL_COUNT));
		q /= HANGUL_COUNT;
		char first = (char)((HANGUL_START + q % HANGUL_COUNT)); // 중복될가능성이 있지만 이때가면 지구멸망

		return "" + first + second + third + last + "/";
	}
}
