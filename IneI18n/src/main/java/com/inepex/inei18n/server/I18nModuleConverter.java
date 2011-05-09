package com.inepex.inei18n.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import com.inepex.inei18n.shared.LocalizedString;

public class I18nModuleConverter {

	private static VelocityUtil velocityUtil;
	
	public final static String MODULE_VM = "vm/i18n/Module.vm";
	public final static String SERVER_MODULE_PROVIDER_VM = "vm/i18n/ServerModuleProvider.vm";
	public final static String DEFAULT_LANGUAGE = "en";
	final Pattern BRACETED_STIRNG_FINDER = Pattern.compile("\\{([^}]+)\\}");
	
	final static Logger logger = LoggerFactory.getLogger(I18nModuleConverter.class);
	
	final static String PER = "/";
	final static String QUOTE = "\"";
	
	final Class<?> moduleCalss;
	final String sourceFolder;
	final String moduleUri;
	final String moduleName;
	final ModuleProperties props;
	final String SEP;
	
	Map<String, LocalizedString> localizables;
	
	public I18nModuleConverter(Class<?> moduleClass) {
		this.moduleCalss = moduleClass;
		this.moduleName = moduleClass.getSimpleName();
		this.moduleUri = moduleClass.getPackage().getName().replace(".", PER);
		this.props = new ModuleProperties(moduleUri + PER + moduleName);
		this.SEP = props.csvSeparator;
		this.sourceFolder = props.sourceFolder;
		
		initVelocityUtilIfNeeded();
	}
	
	private void initVelocityUtilIfNeeded() {
		if (velocityUtil != null)
			return;
		
		velocityUtil = new VelocityUtil();
	}
	
	public I18nModuleConverter(Class<?> moduleClass, Map<String, LocalizedString> localizables) {
		this(moduleClass);
		this.localizables = localizables;
	}
	
	/**
	 * Only works in development environment!
	 * @throws IOException
	 */
	public void saveCsvToDefaultPath() throws IOException {
		saveCsvToPath(getDevCsvPath());
	}
	
	public void saveCsvToUserDefinedPath() throws IOException {
		String userDefinedPath = props.userCsvPath == null ? "" : props.userCsvPath;
		String userCsvFileName = userDefinedPath + moduleName + ".csv";
		saveCsvToPath(userCsvFileName);
	}
	
	private void saveCsvToPath(String nameWithPath) throws IOException {
		BufferedWriter defaultOut = null;
		try {
			String csvContent = getLocalizablesInCsvFormat();
			defaultOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nameWithPath),"UTF8"));
			defaultOut.write(csvContent);
			defaultOut.close();
			logger.info("Csv file saved to '" + nameWithPath + "'");
		} catch (IOException e) {
			logger.error("ERROR while saving file '" + nameWithPath + "'", e);
			throw e;
		}		
	}
	
	public String getLocalizablesInCsvFormat() {
		StringBuilder sb = new StringBuilder();
		for (LocalizedString localizable : localizables.values()) {
			sb.append(escapeAndQuote(localizable.getKey())).append(SEP);
			sb.append(escapeAndQuote(localizable.getDescription())).append(SEP);
			
			for (String lang : props.languages) {
				sb.append(escapeAndQuote(localizable.getLocalizedMap().get(lang)));
				sb.append(SEP);
			}
			sb.delete(sb.length()-SEP.length(), sb.length());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private String getServerPackagePath() {
		return "./" + sourceFolder + "/" + props.serverPackage.replace(".", PER) + "/";
	}
	
	private String getModulesPath() {
		return "./" + sourceFolder + "/" + moduleUri + "/";
	}
	
	private String getDevCsvPath() {
		return  getModulesPath() + moduleName + ".csv";
	}
	
	
	/**
	 * Should only be used in development environment!
	 */
	public void loadDataFromDefaultCsvDev() {
		loadFromCsv(getDevCsvPath());
	}
	
	public void loadDataFromDefaultCsvRuntime() {
		try {
			String moduleCsvResourceName = moduleUri + "/" + moduleName + ".csv";
			InputStream stream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(moduleCsvResourceName);
			if (stream == null) {
				throw new FileNotFoundException("Template file could not be found: " + moduleCsvResourceName);
			}		
			
			loadCsvFromStream(stream);
			stream.close();
		} catch (IOException e) {
			logger.error("IOException while loading csv with Runime parameters", e);
		}
	}
	
	private void loadFromCsv(String csvNameWithPath) {
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(csvNameWithPath);
			loadCsvFromStream(fstream);
			fstream.close();
		} catch (FileNotFoundException e) {
			logger.error("The csv file '" + csvNameWithPath + "' is not found!", e);
		} catch (IOException e) {
			logger.error("IOException while loading csv", e);
		}

			
	}
	
	private void loadCsvFromStream(InputStream stream) {
		localizables = new TreeMap<String, LocalizedString>();
		String strLine;
		String duplicates = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			while ((strLine = br.readLine()) != null) {
				String[] fields = strLine.split(SEP);

				if (localizables.get(fields[0]) != null) {
					duplicates += fields[0] + "\n";
				} else {
					String key = deEscapeAndDeQuote(fields[0]);
					
					LocalizedString localizable = new LocalizedString(key
													, deEscapeAndDeQuote(fields[1]));
					int col = 2;
					for (String language : props.languages) {
						String raw = "";
						if(fields.length <= col || fields[col] == null)
							raw = "**" + fields[2] + "**";
						else
							raw = fields[col];
						
						localizable.getLocalizedMap().put(language, deEscapeAndDeQuote(raw));
						col++;
					}

					localizables.put(key, localizable);
				}
			}
			
			logger.info("Loading of I18n module '" + moduleName + "' finished succesfully");
			
			if (!duplicates.equals("")) {
				logger.info("Duplicated keys: \n" + duplicates);
			}

		} catch (IOException e) {
			logger.error("IOException while loading csv", e);
		}		
	}
	
	String getModuleFileName() {
		return getModulesPath() + moduleName + ".java";
	}
	
	public void generateModuleFile() {
		generateFileFromTemplate(getModuleFileName()
				, MODULE_VM
				, getModuleFileCreatorContext());
	}
	
	public VelocityContext getModuleFileCreatorContext() {
		VelocityContext context = new VelocityContext();
		context.put("package", moduleCalss.getPackage().getName());
		context.put("className", moduleName);
		context.put("localizables", localizables.values());
		
		// TODO: now one parameter cannot appear twice in the description 
		Map<String, Set<String>> map_paramstmp = new LinkedHashMap<String, Set<String>>();
		for (String key : localizables.keySet()){
			Set<String> params = new HashSet<String>();
			String description = localizables.get(key).getLocalizedMap().get(DEFAULT_LANGUAGE);
			if (description == null)
				continue;
			Matcher m = BRACETED_STIRNG_FINDER.matcher(description);
			while (m.find()) {
			   params.add(m.group().replace("{","").replace("}",""));
			}
			
			map_paramstmp.put(key, params);
		}
		
		context.put("paramsbykey", map_paramstmp);
		
		return context;
	}
	
	String getServerFileName() {
		return getServerPackagePath() 
				+ "Server" 
				+ moduleName 
				+ "Provider.java";
	}
	
	public void generateServerModuleProviderFile() {
		generateFileFromTemplate(getServerFileName()
				, SERVER_MODULE_PROVIDER_VM
				, getServerModuleProviderFileContext());
	}
	
	public VelocityContext getServerModuleProviderFileContext() {
		VelocityContext context = new VelocityContext();
		context.put("package", props.serverPackage);
		context.put("className", moduleName);
		context.put("modulePackage", moduleCalss.getPackage().getName());
		return context;
	}	
	
	private void generateFileFromTemplate(String fileName, String templateName, VelocityContext context) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
										fos , "UTF8"));
			
			generateFileToWriter(writer, templateName, context);
			
			writer.flush();
			writer.close();
			logger.info(fileName + " generated successfully!");
		} catch (Exception e) {
			logger.error("Error while generating " + fileName, e);
		}		
	}
	
	public void generateFileToWriter(Writer writer, String templateName, VelocityContext context) 
			throws ResourceNotFoundException, ParseErrorException, Exception {
		String mergedTemplate = velocityUtil.getMessageFromTemplate(templateName, context);
		writer.write(mergedTemplate);
	}
	
	
	private String escapeAndQuote(String str) {
		if (str == null)
			return QUOTE + QUOTE;
		return QUOTE + str.replace("\n", "\\n") + QUOTE;
	}

	private String deEscapeAndDeQuote(String str) {
		return str.replace("\"", "").replace("\\n", "\n");
	}

	public Collection<LocalizedString> getLocalizables() {
		return localizables.values();
	}
	
	public Map<String, LocalizedString> getLocalizablesMap() {
		return localizables;
	}
	
}
