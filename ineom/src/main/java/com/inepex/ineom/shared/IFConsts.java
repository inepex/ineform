package com.inepex.ineom.shared;

public class IFConsts {
	
	public final static String customDescriptorName = "***";
	
	public static final String KEY_ID = "id";
	public static final String KEY_ISDELETED = "isDeleted";
	public static final String KEY_ORDERNUM = "orderNum";
	
	public static final Long NEW_ITEM_ID = 0L;
	
	public static final String NO_TITLE = "No Title";

	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	public static final String enumValueSplitChar = "~";
	public final static String enumValues = "enumValues";
	
	public static final String exportServletUrl= "export";
	public static final String uploadServletUrl= "upload";	
	
	//imageupload properties
	public static String ALLOWED_EXTENSIONS = "allowedextensions";
	public static String IMAGE_PROPERTIES_PATH = "imagePropertiesPath";
	public static String MAX_REQUEST_SIZE = "maxrequestsize";
	public static String USEORIGINALFILENAME = "useoriginalfilename";
	public static String USEEXTENSION = "useextension";
	public static String RESIZEIMAGES = "resizeimages";		
	
	//These variables belong to the stay signed in functionality
	public static final String COOKIE_NEEDSTAYSIGNEDIN = "usrstsin_";
	public static final String COOKIE_STAYSIGNEDINUSERNAME= "ursmnfneui_";
	public static final String COOKIE_STAYSIGNEDINUUID= "tsstsui_";
	public static final String COOKIE_TRUE= "true";
	public static final String COOKIE_FALSE= "false";
	
	public static final String LANG = "lang";
	
	public static String buildEnumList(String ... items){
		StringBuilder sb = new StringBuilder();
		for (String item : items){
			sb.append(item)
			  .append(IFConsts.enumValueSplitChar);
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	
}
