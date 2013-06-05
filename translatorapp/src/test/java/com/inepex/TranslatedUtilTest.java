package com.inepex;

import org.junit.Test;

import com.inepex.translatorapp.server.handler.TranslatedUtil;

import static org.junit.Assert.*;

public class TranslatedUtilTest {
	
	@Test
	public void wellFormattedTest() {
		assertTrue(TranslatedUtil.isWellFormatted(null));
		assertTrue(TranslatedUtil.isWellFormatted(""));
		assertTrue(TranslatedUtil.isWellFormatted("....."));
		assertTrue(TranslatedUtil.isWellFormatted("....()....."));
		assertTrue(TranslatedUtil.isWellFormatted("....(......)....."));
		assertTrue(TranslatedUtil.isWellFormatted("....(..)..[]...{...}..'.'......<a>....<b>......"));
		
		assertFalse(TranslatedUtil.isWellFormatted("....(.."));
		assertFalse(TranslatedUtil.isWellFormatted("....(..(....))."));
		assertFalse(TranslatedUtil.isWellFormatted("...'."));
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void invalidCase() {
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid(".....{p.....", ".....{p....."));
	}
	
	@Test
	public void basicsParams() {
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid(null, null));
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid(null, "txt"));
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid("txt", null));
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid("txt", "szv"));
	}
	
	@Test
	public void oneParam() {
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid("..... ..{param}... ....", "___{param}____"));
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid("..... ..{param}", "___{param}"));
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid("{param}", "{param}"));
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid("{param}... ....", "{param}____"));
		
		assertFalse(TranslatedUtil.areParamsOfTranslatedValid("..{param}... ....", "_____"));
	}
	
	@Test
	public void moreParams() {
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid("{p}{r}", "{p}{r}"));
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid("..{p}....{r}...", "___{p}____{r}___"));
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid("..{p}....{r}...", "___{r}____{p}___"));
		
		assertFalse(TranslatedUtil.areParamsOfTranslatedValid("..{p}....{r}...", "___{p}_______"));
	}
	
	@Test
	public void multipleParam() {
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid("..{p}....{p}...", "___{p}____{p}___"));
		assertTrue(TranslatedUtil.areParamsOfTranslatedValid("..{p}...{p}.{p}...", "___{p}__{p}_{p}___"));
		
		assertFalse(TranslatedUtil.areParamsOfTranslatedValid("..{p}...{p}..{p}...", "___{p}__{p}____"));
		assertFalse(TranslatedUtil.areParamsOfTranslatedValid("..{p}........", "___{p}__{p}____"));
	}
	
	@Test
	public void extraParam() {
		assertFalse(TranslatedUtil.areParamsOfTranslatedValid("..........", "______{k}____"));
		assertFalse(TranslatedUtil.areParamsOfTranslatedValid("..{p}........", "___{p}__{k}____"));
	}
}
