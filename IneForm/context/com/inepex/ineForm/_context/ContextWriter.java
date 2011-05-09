package com.inepex.ineForm._context;

import java.io.FileWriter;
import java.io.IOException;

public class ContextWriter {	
	public static void main(String[] args) {
		try {
			String ineFormDevDir = "";
			String ctxName = "";
			if (args.length > 1) {
				ctxName = args[0];
				ineFormDevDir = args[1];
			} else {
				System.out.println("Not enough parameter provided! Please look at source for usage!");
				return;
			}
			FileWriter fw = new FileWriter(ineFormDevDir + "src/com/inepex/" +
					"ineForm/_context/Context.java", false);
			fw.append("package com.inepex.ineForm._context;");
			fw.append("public class Context {");
			fw.append("	public static final String current = " + "\"" + ctxName + "\";");
			fw.append("}");
			fw.close();
			
			System.out.println("Context file created");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
