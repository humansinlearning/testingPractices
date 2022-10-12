package com.d.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringHelperBeforeTest {
	//"", "A", "AA", "B", "BC"
	StringHelper helper = new StringHelper();

	@Test
	void testReplaceAInFirst2Positions() {
//		assertEquals("BCD", helper.replaceAInFirst2Positions("ABCD"));

	}
	
	//""=>false, "A"=>false, "AB"=>true, "ABC"=>false, "AAA"=>true, "ABCAB"=>true, "ABCDEBA"=>false
	//Red
	//Green
	//Refactor

	@Test
	void testAreFirstTwoAndLastTwoCharsTheSame() {
//		assertFalse(helper.areFirstTwoAndLastTwoCharsTheSame(""));
//		assertFalse(helper.areFirstTwoAndLastTwoCharsTheSame("A"));
//		assertTrue(helper.areFirstTwoAndLastTwoCharsTheSame("AB"));
//		assertFalse(helper.areFirstTwoAndLastTwoCharsTheSame("ABC"));
//		assertTrue(helper.areFirstTwoAndLastTwoCharsTheSame("AAA"));
//		assertTrue(helper.areFirstTwoAndLastTwoCharsTheSame("ABCAB"));
//		assertFalse(helper.areFirstTwoAndLastTwoCharsTheSame("ABCDEBA"));
	}

}
