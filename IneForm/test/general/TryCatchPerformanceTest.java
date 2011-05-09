package general;

import org.junit.Test;

public class TryCatchPerformanceTest {
	static int runningNum = 0;
	static int ONE_BILLION = 1000000000;
	
	@Test
	public void dummyTest() throws Exception {
		runningNum = 0;
		doDummyOperation();
	}
	
	@Test
	public void testWithoutTryCatch() throws Exception {
		runningNum = 0;
		for (int i = 0; i < ONE_BILLION; i++)
			doDummyOperation();
	}

	@Test
	public void testWithOneTryCatch() {
		runningNum = 0;
		try {
			for (int i = 0; i < ONE_BILLION; i++)
				doDummyOperation();
		} catch (Exception e) {
			doDummyOperation();
		}
	}
	
	@Test
	public void testWithTryCatch() {
		runningNum = 0;
		for (int i = 0; i < ONE_BILLION; i++)
			try {
				doDummyOperation();
			} catch (Exception e) {
				doDummyOperation();
			}
	}
	
	private void doDummyOperation() {
		runningNum +=3;
	}
}
