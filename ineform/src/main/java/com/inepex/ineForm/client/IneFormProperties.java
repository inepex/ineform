package com.inepex.ineForm.client;

import com.inepex.ineForm.shared.types.FormUnitT;
import com.inepex.ineForm.shared.types.PanelWidgetT;

public class IneFormProperties {

	/**
	 * temporary variable for button replacing 
	 */
	public static boolean OLD_STYLE_COMPATIBILITY = true;
	
	public static boolean showIds;

	public static String DEFAULT_TextBoxWidth = "585px";
	public static String DEFAULT_TextAreaHeigth = "150px";
	
	public static String DEFAULT_RichTextBoxWidth = "500px";
	public static String DEFAULT_RichTextAreaHeigth = "350px";
	
	public static int DEFAULT_ListBoxWidth_inPx = 320;
	public static String DEFAULT_FormLabelWidth = "200px";
		
	public static FormUnitT defFormUnitType = FormUnitT.SIMPLETABLEFORM;
	
	public static String INETABLE_DEFAULT_DATETIMEFORMAT="yyyy.MM.dd HH:mm";
	public static String INETABLE_DEFAULT_SHORT_DATETIMEFORMAT ="yyyy.MM.dd";
	public static String INETABLE_DEFAULT_HOURMIN ="HH:mm";
	public static String INETABLE_DEFAULT_HOURMINSEC ="HH:mm:ss";
	public static String INETABLE_DEFAULT_SEC_DATETIMEFORMAT="yyyy.MM.dd HH:mm:ss";
	
	public static PanelWidgetT defPanelWidgetType = PanelWidgetT.FLOWPANEL;
	
	/**
	 * IneForm uses these strings for parse and show dates in its date component... A special textbox uses these strings for completing typed text, correct it and manage selection on it.
	 * So if you want to change these strings, be sure that:
	 * 
	 *    -there aren't numbers in the date string
	 *    -there are alphabetic characters according to com.google.gwt.i18n.client.DateTimeFormat's rules, but this components don't support extra strings
	 *    (you can only use: y M d h H m s S k K)
	 *    -there can be everywhere extra non-alphabetic chars (like: -, ., ...)
	 *    
	 *    "y.M.d H:m" is ok
	 *    "yyyy.MM.dd HH:mm" is ok too
	 *    "y.M.d H:m 01" is not ok
	 *    "y.M.d H::+!,,,m" is ok, but users are going to hate you, if they must every time type this char sequence into the textbox
	 *    
	 *     
	 *  On the other hand, if you use a date string, which widget never use textbox, you can write into everything what is correct according to com.google.gwt.i18n.client.DateTimeFormat
	 *  	"hh 'o''clock' a, zzzz"	its ok
	 *  	"yyyyy.MMMMM.dd GGG hh:mm aaa" ok too
	 *  	"yyyy.MM.dd G 'at' HH:mm:ss vvvv" ok too    
	 *   
	 */
	public static String YMD_HM ="yyyy.MM.dd HH:mm";
	public static String OOO_HM ="HH:mm";
	public static String OOO_OM ="mm";
	public static String OOO_HO ="HH";
	public static String OOO_HMS ="HH:mm:ss";	
	public static String YMD_OO ="yyyy.MM.dd";
	public static String YMO_OO ="yyyy.MM";
	public static String YOO_OO ="yyyy";
	public static String OMO_OO = "MM";
}	
