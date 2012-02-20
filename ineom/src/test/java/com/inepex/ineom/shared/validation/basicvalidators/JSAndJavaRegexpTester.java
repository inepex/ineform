package com.inepex.ineom.shared.validation.basicvalidators;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

/**
 * Class to test Java and JS javascript  
 *
 */
public class JSAndJavaRegexpTester {

	public static Result testMatch(String regexp, String string) {
		if(string==null || regexp==null)
			throw new IllegalArgumentException();
		
		boolean js = testJS(regexp, string);
		boolean java = testJava(regexp, string); 
		
		if(js) {
			if (java) {
				return Result.ACCEPTED;
			} else {
				return Result.JAVA_FAILED;
			}
		} else if(java) {
			return Result.JS_FAILED;
		} else {
			return Result.INVALID;
		}
	}

	public static boolean testJS(String regexp, String string) {
		return false;
	}

	public static boolean testJava(String regexp, String string) {
		return string.matches(regexp);
	}
	
	@Test
	public void main() throws ScriptException {
        // create a script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();
        // create a JavaScript engine
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        // evaluate JavaScript code from String
        engine.eval("print('Hello, World')");
    }
	
	public static enum Result {
		ACCEPTED, JS_FAILED, JAVA_FAILED, INVALID;
	}
}
