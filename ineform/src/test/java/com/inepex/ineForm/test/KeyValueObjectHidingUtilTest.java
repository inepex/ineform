package com.inepex.ineForm.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.inepex.ineForm.client.form.widgets.assist.NationalityAssist;
import com.inepex.ineForm.client.form.widgets.kvo.NationalityKVO;
import com.inepex.ineForm.server.util.JavaDateFormatter;
import com.inepex.ineFrame.server.KeyValueObjectFieldFilter;
import com.inepex.ineFrame.server.NumberUtilSrv;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineFrame.shared.util.date.DateFormatter;
import com.inepex.ineFrame.test.DefaultIneFrameClientSideTestBase;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.util.SharedUtil;

@RunWith(JukitoRunner.class)
public class KeyValueObjectHidingUtilTest extends DefaultIneFrameClientSideTestBase {

	public static class Module extends JukitoModule {
		@Override
		protected void configureTest() {
			install(new TestIneFormClientGuiceModule());
			bind(DateFormatter.class).to(JavaDateFormatter.class);
			bind(NumberUtil.class).to(NumberUtilSrv.class);
		}
	}
	
	final Long id = 100L; 
	final String name = "asfasdfasfdasdf12323";
	
	DescriptorStore descStore;
	AssistedObjectHandlerFactory handlerFactory;
	
	@Before
	public void init(DescriptorStore descriptorStore) {
		descStore = descriptorStore;
		handlerFactory = new AssistedObjectHandlerFactory(descStore);
	}
	
	@Test
	public void simpleDoNothingTest() {
		NationalityAssist.registerDescriptors(descStore);
		
		NationalityKVO kvo = new NationalityKVO();
		kvo.setId(id);
		kvo.setName(name);
		
		List<String> enabled_keys = SharedUtil.Li(NationalityKVO.k_name, "afdgsd", "sfdfds");
		
		AssistedObject result = KeyValueObjectFieldFilter.filterKvo(descStore, enabled_keys, kvo, handlerFactory);
		
		Assert.assertEquals(NationalityKVO.descriptorName, result.getDescriptorName());
		Assert.assertEquals(kvo.getDescriptorName(), result.getDescriptorName());
		
		Assert.assertEquals(id, result.getId());
		Assert.assertEquals(kvo.getId(), result.getId());
		
		Assert.assertEquals(name, handlerFactory.createHandler(result).getString(NationalityKVO.k_name));
		Assert.assertEquals(kvo.getName(), handlerFactory.createHandler(result).getString(NationalityKVO.k_name));
		
		Assert.assertEquals(kvo.getKeys(), result.getKeys());
	}
	
	@Test
	public void simpleClearNameTest() {
		NationalityAssist.registerDescriptors(descStore);
		
		NationalityKVO kvo = new NationalityKVO();
		kvo.setId(id);
		kvo.setName(name);
		
		List<String> enabled_keys = SharedUtil.Li("afdgsd", "sfdfds");
		
		AssistedObject result = KeyValueObjectFieldFilter.filterKvo(descStore,enabled_keys, kvo, handlerFactory);
		
		Assert.assertEquals(NationalityKVO.descriptorName, result.getDescriptorName());
		Assert.assertEquals(kvo.getDescriptorName(), result.getDescriptorName());
		
		Assert.assertEquals(id, result.getId());
		Assert.assertEquals(kvo.getId(), result.getId());
		
		
		Assert.assertNull(handlerFactory.createHandler(result).getString(NationalityKVO.k_name));
		List<String> resPlus = new ArrayList<String>(result.getKeys());
		Assert.assertTrue(!kvo.getKeys().equals(resPlus));
		resPlus.add(NationalityKVO.k_name);
		Assert.assertEquals(kvo.getKeys(), resPlus);
	}
	
	
	@Test
	public void copyNull() {
		NationalityAssist.registerDescriptors(descStore);
		
		NationalityKVO kvo = new NationalityKVO();
		kvo.setName(null);
		
		List<String> enabled_keys = SharedUtil.Li(NationalityKVO.k_name);
		
		AssistedObject result = 
			KeyValueObjectFieldFilter.filterKvo(descStore, enabled_keys, kvo, handlerFactory);
		
		Assert.assertNull(handlerFactory.createHandler(result).getString(NationalityKVO.k_name));
		Assert.assertTrue(result.getKeys().contains(NationalityKVO.k_name));
		Assert.assertEquals(2, result.getKeys().size());
	}
}
