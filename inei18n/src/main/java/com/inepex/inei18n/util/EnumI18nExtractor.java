package com.inepex.inei18n.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.inepex.inei18n.server.VelocityUtil;

public class EnumI18nExtractor {
	
	public static void generateI18nAccessHelpersForEnums(ClassLoader classLoader, String i18nName, List<EnumClassWithPostfix> enumList) {
		VelocityContext ctx = new VelocityContext();
		List<EnumDescriptor> enums = new ArrayList<EnumDescriptor>();
		for (EnumClassWithPostfix clazzWithPostfix : enumList) {
			EnumDescriptor ed = new EnumDescriptor();
			ed.setName(clazzWithPostfix.clazz.getSimpleName());
			ed.postFix = clazzWithPostfix.postFix;
			ed.consts = new ArrayList<String>();
			for (Object enumClass : clazzWithPostfix.clazz.getEnumConstants()) {
				ed.consts.add(enumClass.toString());
			}
			enums.add(ed);
		}

		VelocityUtil vu = new VelocityUtil(classLoader);
		ctx.put("i18nName", i18nName);
		ctx.put("enums", enums);
		
		String generated = vu.getMessageFromTemplate("vm/i18n/EnumHelper.vm", ctx);
		
		System.out.println(generated);
		
	}
	
	public static class EnumDescriptor {
		public String i18nName;
		public String name;
		public String nameL1;
		public String postFix;
		public List<String> consts;
		
		public String getName() {
			return name;
		}
		public List<String> getConsts() {
			return consts;
		}
		
		public String getI18nName() {
			return i18nName;
		}
		public String getNameL1() {
			return nameL1;
		}
		public void setName(String name) {
			this.name = name;
			this.nameL1 = name.substring(0,1).toLowerCase() + name.substring(1);
		}
		public String getPostFix() {
			return postFix;
		}
		
	}
	
	public static class EnumClassWithPostfix {
		Class<?> clazz;
		String postFix = null;
		
		public EnumClassWithPostfix(Class<?> clazz) {
			super();
			this.clazz = clazz;
		}
		
		public EnumClassWithPostfix(Class<?> clazz, String postFix) {
			super();
			this.clazz = clazz;
			this.postFix = postFix;
		}
		public Class<?> getClazz() {
			return clazz;
		}
		public String getPostFix() {
			return postFix;
		}
		
		
	}

 	
}
