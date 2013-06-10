package com.inepex.translatorapp.server.handler;

import org.junit.Test;

import com.inepex.translatorapp.server.handler.TranslatorAppUtil;

import static org.junit.Assert.*;

public class TranslatorAppUtilTest {
	
	@Test
	public void wellFormattedTest() {
		assertTrue(TranslatorAppUtil.isWellFormatted(null));
		assertTrue(TranslatorAppUtil.isWellFormatted(""));
		assertTrue(TranslatorAppUtil.isWellFormatted("....."));
		assertTrue(TranslatorAppUtil.isWellFormatted("....()....."));
		assertTrue(TranslatorAppUtil.isWellFormatted("....(......)....."));
		assertTrue(TranslatorAppUtil.isWellFormatted("....(..)..[]...{...}..'.'......<a>....<b>......"));
		
		assertFalse(TranslatorAppUtil.isWellFormatted("....(.."));
		assertFalse(TranslatorAppUtil.isWellFormatted("....(..(....))."));
		assertFalse(TranslatorAppUtil.isWellFormatted("...'."));
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void invalidCase() {
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid(".....{p.....", ".....{p....."));
	}
	
	@Test
	public void basicsParams() {
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid(null, null));
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid(null, "txt"));
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid("txt", null));
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid("txt", "szv"));
	}
	
	@Test
	public void oneParam() {
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid("..... ..{param}... ....", "___{param}____"));
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid("..... ..{param}", "___{param}"));
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid("{param}", "{param}"));
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid("{param}... ....", "{param}____"));
		
		assertFalse(TranslatorAppUtil.areParamsOfTranslatedValid("..{param}... ....", "_____"));
	}
	
	@Test
	public void moreParams() {
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid("{p}{r}", "{p}{r}"));
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid("..{p}....{r}...", "___{p}____{r}___"));
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid("..{p}....{r}...", "___{r}____{p}___"));
		
		assertFalse(TranslatorAppUtil.areParamsOfTranslatedValid("..{p}....{r}...", "___{p}_______"));
	}
	
	@Test
	public void multipleParam() {
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid("..{p}....{p}...", "___{p}____{p}___"));
		assertTrue(TranslatorAppUtil.areParamsOfTranslatedValid("..{p}...{p}.{p}...", "___{p}__{p}_{p}___"));
		
		assertFalse(TranslatorAppUtil.areParamsOfTranslatedValid("..{p}...{p}..{p}...", "___{p}__{p}____"));
		assertFalse(TranslatorAppUtil.areParamsOfTranslatedValid("..{p}........", "___{p}__{p}____"));
	}
	
	@Test
	public void extraParam() {
		assertFalse(TranslatorAppUtil.areParamsOfTranslatedValid("..........", "______{k}____"));
		assertFalse(TranslatorAppUtil.areParamsOfTranslatedValid("..{p}........", "___{p}__{k}____"));
	}
}
