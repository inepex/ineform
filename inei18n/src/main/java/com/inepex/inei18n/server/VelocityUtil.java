package com.inepex.inei18n.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

public class VelocityUtil {
	
	private VelocityEngine ve = null;
	private StringResourceRepository stringTemplateRepo = null;
		
	private final ClassLoader classLoader;
	
	public VelocityUtil(ClassLoader classLoader) {
		this.classLoader=classLoader;
	}
	
	public String getMessageFromTemplate(String templateName, VelocityContext context) {
		try {
			initVelocity();
			
			String rawTamplate = readTemplateToString(templateName);

			stringTemplateRepo.putStringResource(templateName, rawTamplate, "utf-8");
			
			Template template = ve.getTemplate(templateName, "utf-8");
			
			StringWriter sw = new StringWriter();
			
			template.merge(context, sw);
			
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String readTemplateToString(String fileName) {
		URL url = null;
		url = classLoader.getResource(fileName);

		StringBuffer content = new StringBuffer();
		
		if (url == null) {
			String error = "Template file could not be found: "
				+ fileName;
			throw new RuntimeException(error);
		}
		
		try {
			BufferedReader breader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			String strLine = "";
			while ((strLine = breader.readLine()) != null) {
				content.append(strLine).append("\n");
			}
			breader.close();
		} catch (Exception e) {
			throw new RuntimeException("Problem while loading file: " + fileName);
		}
		return content.toString();
	}
	
	private void initVelocity() throws Exception {
		if (ve != null)
			return;
		
		ve = new VelocityEngine();
		ve.addProperty(Velocity.RESOURCE_LOADER, "string");
		Velocity.addProperty("string.resource.loader.description",
		"Velocity StringResource loader");
		ve.addProperty("string.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.StringResourceLoader");
		ve.addProperty("string.resource.loader.repository.class",
				"org.apache.velocity.runtime.resource.util.StringResourceRepositoryImpl");
		ve.addProperty("string.resource.loader.repository.name", "repo");

		ve.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");

		ve.init();
		stringTemplateRepo = StringResourceLoader.getRepository("repo");
	}
}
