package com.inepex.ineFrame.client.kvo;

import junit.framework.Assert;

import com.google.gwt.json.client.JSONParser;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.TestUtil;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

//FIXME: it makes time out
public class KvoJsonParserGwtTest {//extends GWTTestCase {

//	{"place": {"iconVersion": 0, "name": "P\u00f3tkulcs", "hasEvents": true, "address": "Budapest VI. ker., Csengery u. 65\/B.", "lat": 47.5094469889, "lng": 19.0617620945, "id": 432010, "fsId": 1170369}, "event": {"info": "M\u00fcller Gy\u00f6rgy a Radio C legn\u00e9pszer\u0171bb lemezlovasa, akinek vil\u00e1gzenei reperto\u00e1rja p\u00e1ratlan.", "src": "est.hu", "name": "Ny\u00e1resti szieszt\u00e1z", "url": "http:\/\/est.hu\/esemeny\/3439059\/nyaresti_sziesztaz", "artists": [{"artist": {"id": 549005, "name": "DJ Kokalo"}}], "srcId": "3439059", "placeId": 432010, "link": "http:\/\/est.hu\/esemeny\/3439059\/nyaresti_sziesztaz", "time": "2011-06-08 19:00 Wed", "entry": 0, "id": 2893388}, "success": true}
	
	String jsonData = "{'stringField': 'hello', 'relField': {'longField': 3}, 'listField': [{'longField': 4}, {'longField': 5}]}";
	
	String jsonDataNullValue = "{'stringField': null}";

	public void testDefault(){
		jsonData = jsonData.replace("'", "\"");
		AssistedObject kvo = new KvoJsonParser(TestUtil.descriptorStore, JSONParser.parseStrict(jsonData).isObject(), "testKvo").parse();
		TestUtil.assertEquals(TestUtil.getTestKvo().getAssistedObject(), kvo, TestUtil.descriptorStore);
	}

	public void testNull(){
		jsonDataNullValue = jsonDataNullValue.replace("'", "\"");
		AssistedObject kvo = new KvoJsonParser(TestUtil.descriptorStore, JSONParser.parseStrict(jsonDataNullValue).isObject(), "testKvo").parse();
		AssistedObjectHandler ch = TestUtil.createObjectHandlerFactory().createHandler(kvo);
		
		Assert.assertTrue(ch.containsString("stringField"));
		Assert.assertNull(ch.getString("stringField"));
	}
}
