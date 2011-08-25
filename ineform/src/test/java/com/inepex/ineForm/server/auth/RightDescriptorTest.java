package com.inepex.ineForm.server.auth;

import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.ALL;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.ALL_LISTING;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.ALL_MANIPULATION;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.LISTING;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.MANIPULATION_CREATE;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.MANIPULATION_DELETE;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.MANIPULATION_REFRESH;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.MANIPULATION_UNDELETE;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.RELATION_LISTING;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.inepex.ineFrame.server.auth.RightDescriptor;

public class RightDescriptorTest {

	@Test
	public void testAdmin() {
		// default right on anyting
		RightDescriptor rd = new RightDescriptor("ADMIN");
		assertArrayEquals(new String[]{"ADMIN"}, rd.getAllowedRolesForObjectRight("DoesntMatter", MANIPULATION_CREATE).toArray());
	
		// Denyal of right
		rd.add(rd.newRightFramgent("obj2").denyRight("ADMIN", MANIPULATION_CREATE));
		assertEquals(0, rd.getAllowedRolesForObjectRight("obj2", MANIPULATION_CREATE).size());
	}
	
	@Test
	public void testAggregatedGrant() {
		RightDescriptor rd = new RightDescriptor("ADMIN");

		rd.add(rd.newRightFramgent("obj1").grantRight("ROLE1", ALL)
										  .grantRight("ROLE2", ALL));

		assertArrayEquals(new String[]{"ADMIN", "ROLE1", "ROLE2"}, rd.getAllowedRolesForObjectRight("obj1", MANIPULATION_CREATE).toArray());
		assertArrayEquals(new String[]{"ADMIN", "ROLE1", "ROLE2"}, rd.getAllowedRolesForObjectRight("obj1", MANIPULATION_DELETE).toArray());
		assertArrayEquals(new String[]{"ADMIN", "ROLE1", "ROLE2"}, rd.getAllowedRolesForObjectRight("obj1", MANIPULATION_REFRESH).toArray());
		assertArrayEquals(new String[]{"ADMIN", "ROLE1", "ROLE2"}, rd.getAllowedRolesForObjectRight("obj1", MANIPULATION_UNDELETE).toArray());
		assertArrayEquals(new String[]{"ADMIN", "ROLE1", "ROLE2"}, rd.getAllowedRolesForObjectRight("obj1", LISTING).toArray());
		assertArrayEquals(new String[]{"ADMIN", "ROLE1", "ROLE2"}, rd.getAllowedRolesForObjectRight("obj1", RELATION_LISTING).toArray());
		
			
		rd.add(rd.newRightFramgent("obj2").denyRight("ADMIN", ALL_LISTING));
		assertEquals(0, rd.getAllowedRolesForObjectRight("obj2", LISTING).size());
		assertEquals(0, rd.getAllowedRolesForObjectRight("obj2", RELATION_LISTING).size());
		
	}
	
	@Test(expected=RuntimeException.class)
	public void aggregatorRightUsageException() {
		RightDescriptor rd = new RightDescriptor("ADMIN");
		rd.getAllowedRolesForObjectRight("NotImportant", ALL);
	}
	
	@Test
	public void testComplex() {
		RightDescriptor rd = new RightDescriptor("ADMIN");

		rd.add(rd.newRightFramgent("obj1").grantRight("ROLE1", ALL)
										  .grantRight("ROLE2", ALL)
										  .denyRight("ROLE1", ALL_LISTING)
										  .denyRight("ROLE2", ALL_MANIPULATION)
										  .grantRight("ROLE3", ALL)
										  .denyRight("ROLE3", MANIPULATION_DELETE)
										  .denyRight("ADMIN", LISTING));
		
		assertArrayEquals(new String[]{"ADMIN", "ROLE1", "ROLE3"}, rd.getAllowedRolesForObjectRight("obj1", MANIPULATION_CREATE).toArray());
		assertArrayEquals(new String[]{"ROLE3", "ROLE2"}, rd.getAllowedRolesForObjectRight("obj1", LISTING).toArray());
		assertArrayEquals(new String[]{"ADMIN", "ROLE1"}, rd.getAllowedRolesForObjectRight("obj1", MANIPULATION_DELETE).toArray());
	}
}
