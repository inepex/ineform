package com.inepex.inei18n.util;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.inepex.inei18n.util.CsvMerge.MergeResult;
import com.inepex.inei18n.util.CsvMerge.ParsedCsv;

public class CsvMergeApplicationTest {
	
	@Test
	public void parseCsvTest() {
		try {
			CsvMerge.parseCsv(new ArrayList<String>(), ";");
			Assert.fail("List length must be checked");
		} catch (IllegalArgumentException ex) {
			//check passed
		}
		
		try {
			CsvMerge.parseCsv(Arrays.asList("line1"), "");
			Assert.fail("Separator must be checked");
		} catch (IllegalArgumentException ex) {
			//check passed
		}
		
		try {
			CsvMerge.parseCsv(Arrays.asList("line1;desc;", "line2;desc;", "line1;again;"), ";");
			Assert.fail("Key duplication must be checked");
		} catch (RuntimeException ex) {
			//check passed
		}
		
		try {
			CsvMerge.parseCsv(Arrays.asList("line1;desc;", "it's;a;long;line"), ";");
			Assert.fail("Inconsistent column count must be checked");
		} catch (RuntimeException ex) {
			//check passed
		}
		
		ParsedCsv parsedCsv = CsvMerge.parseCsv(Arrays.asList(
						"key1;desc1;engval;huval;",
						"key2;desc2;engval;huval", // no closing ;
						"key3;desc3;engval;huval;"), ";");
		
		//column count
		Assert.assertEquals(parsedCsv.columnCount, 4);
		
		//parsed lines
		Assert.assertEquals(parsedCsv.parsedLines.size(), 3);
		Assert.assertTrue(parsedCsv.parsedLines.keySet().contains("key1"));
		Assert.assertTrue(parsedCsv.parsedLines.keySet().contains("key2"));
		Assert.assertTrue(parsedCsv.parsedLines.keySet().contains("key3"));
		
		//third line
		Assert.assertEquals(parsedCsv.parsedLines.get("key3")[0], "key3");
		Assert.assertEquals(parsedCsv.parsedLines.get("key3")[1], "desc3");
		Assert.assertEquals(parsedCsv.parsedLines.get("key3")[2], "engval");
		Assert.assertEquals(parsedCsv.parsedLines.get("key3")[3], "huval");
		Assert.assertEquals(parsedCsv.parsedLines.get("key3").length, 4);
	}
	
	@Test
	public void mergeTest() {
		try  {
			CsvMerge.merge(
							//target
							CsvMerge.parseCsv(Arrays.asList(
								"a;a1;a2;a3;",
								"b;b1;b2;b3;",
								"e;te1;te2;te3;"), ";"),
							3,
							
							//patch
							CsvMerge.parseCsv(Arrays.asList(
									"c;c1;c2;c3;",
									"d;d1;d2;d3;",
									"e;pe1;pe2;pe3;"), ";"),
							4,
		
							//dummy cell
							"dummy");
			Assert.fail("Patch column must exist");
		} catch (IllegalArgumentException e) {
			//check passed
		}
		
		MergeResult res1 = CsvMerge.merge(
							//target
							CsvMerge.parseCsv(Arrays.asList(
								"aa;a1;a2;a3;",
								"xx;t1;t2;t3",
								"bb;b1;b2;b3;",
								"zz;t1;t2;t3",
								"ee;t1;t2;t3;"), ";"),
							2,
							
							//patch
							CsvMerge.parseCsv(Arrays.asList(
									"zz;p1;p2;p3;",
									"dd;d1;d2;d3;",
									"xx;p1;p2;p3;",
									"ee;p1;p2;p3;"), ";"),
							3,
			
							//dummy cell
							"dummy");
		
		Assert.assertEquals(res1.keyMatchLines.size(), 3);
		Assert.assertTrue(res1.keyMatchLines.contains("ee"));
		Assert.assertTrue(res1.keyMatchLines.contains("xx"));
		Assert.assertTrue(res1.keyMatchLines.contains("zz"));
		
		Assert.assertEquals(res1.resultLines.size(), 5);
		Assert.assertEquals(res1.resultLines.get(0)[0], "aa");
		Assert.assertEquals(res1.resultLines.get(0)[1], "a1");
		Assert.assertEquals(res1.resultLines.get(0)[2], "a2");
		Assert.assertEquals(res1.resultLines.get(0)[3], "a3");
		
		Assert.assertEquals(res1.resultLines.get(1)[0], "bb");
		Assert.assertEquals(res1.resultLines.get(1)[1], "b1");
		Assert.assertEquals(res1.resultLines.get(1)[2], "b2");
		Assert.assertEquals(res1.resultLines.get(1)[3], "b3");
		
		Assert.assertEquals(res1.resultLines.get(2)[0], "ee");
		Assert.assertEquals(res1.resultLines.get(2)[1], "t1");
		Assert.assertEquals(res1.resultLines.get(2)[2], "p3");
		Assert.assertEquals(res1.resultLines.get(2)[3], "t3");
		
		Assert.assertEquals(res1.resultLines.get(3)[0], "xx");
		Assert.assertEquals(res1.resultLines.get(3)[1], "t1");
		Assert.assertEquals(res1.resultLines.get(3)[2], "p3");
		Assert.assertEquals(res1.resultLines.get(3)[3], "t3");
		
		Assert.assertEquals(res1.resultLines.get(4)[0], "zz");
		Assert.assertEquals(res1.resultLines.get(4)[1], "t1");
		Assert.assertEquals(res1.resultLines.get(4)[2], "p3");
		Assert.assertEquals(res1.resultLines.get(4)[3], "t3");
		
		
		MergeResult res2 = CsvMerge.merge(
				//target
				CsvMerge.parseCsv(Arrays.asList(
					"aa;t1;t2;t3;"), ";"),
				3,
				
				//patch
				CsvMerge.parseCsv(Arrays.asList(
						"aa#p1#p2#p3"), "#"),
				0,

				//dummy cell
				"dummy");
		
		Assert.assertEquals(res2.resultLines.get(0).clone()[0], "aa");
		Assert.assertEquals(res2.resultLines.get(0).clone()[1], "t1");
		Assert.assertEquals(res2.resultLines.get(0).clone()[2], "t2");
		Assert.assertEquals(res2.resultLines.get(0).clone()[3], "aa");
		
		MergeResult res3 = CsvMerge.merge(
				//target
				CsvMerge.parseCsv(Arrays.asList(
					"aa;t1;t2;t3;"), ";"),
				7,
				
				//patch
				CsvMerge.parseCsv(Arrays.asList(
						"aa#p1#p2#p3"), "#"),
				2,

				//dummy cell
				"dummy");
		
		Assert.assertEquals(res3.resultLines.get(0).length, 8);
		Assert.assertEquals(res3.resultLines.get(0).clone()[0], "aa");
		Assert.assertEquals(res3.resultLines.get(0).clone()[1], "t1");
		Assert.assertEquals(res3.resultLines.get(0).clone()[2], "t2");
		Assert.assertEquals(res3.resultLines.get(0).clone()[3], "t3");
		Assert.assertEquals(res3.resultLines.get(0).clone()[4], "dummy");
		Assert.assertEquals(res3.resultLines.get(0).clone()[5], "dummy");
		Assert.assertEquals(res3.resultLines.get(0).clone()[6], "dummy");
		Assert.assertEquals(res3.resultLines.get(0).clone()[7], "p2");
	}
}
