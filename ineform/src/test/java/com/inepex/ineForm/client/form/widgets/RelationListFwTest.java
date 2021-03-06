package com.inepex.ineForm.client.form.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.server.util.JavaDateFormatter;
import com.inepex.ineForm.test.TestIneFormClientGuiceModule;
import com.inepex.ineFrame.server.NumberUtilSrv;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineFrame.shared.util.date.DateFormatter;
import com.inepex.ineFrame.test.DefaultIneFrameClientSideTestBase;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

@RunWith(JukitoRunner.class)
public class RelationListFwTest extends DefaultIneFrameClientSideTestBase {

    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
            install(new TestIneFormClientGuiceModule());
            bind(DateFormatter.class).to(JavaDateFormatter.class);
            bind(NumberUtil.class).to(NumberUtilSrv.class);
        }
    }

    RelationTestData data;
    RelationList relationList;

    FormContext formCtx;
    DescriptorStore descStore;
    AssistedObjectHandlerFactory handlerFactory;

    @Before
    public void before(FormContext formCtx, DescriptorStore descStore) {
        this.formCtx = formCtx;
        this.descStore = descStore;
        handlerFactory = new AssistedObjectHandlerFactory(descStore);

        data = new RelationTestData(descStore);

        relationList = new RelationList(
            formCtx.descStore,
            data.fieldDesc.getRelatedDescriptorType(),
            true); // TODO:
                   // test
                   // for
                   // ordering
                   // =
                   // false

        relationList.setRelations(data.relations);
    }

    @Test
    public void createTest() {
        assertRelations(relationList.getRelations(), data.rel1, data.rel3, data.rel2);
        assertEquals(0, relationList.getChanges().size());
    }

    @Test
    public void moveTest() {
        relationList.move(data.rel1, 2);
        assertRelations(relationList.getRelations(), data.rel3, data.rel2, data.rel1);

        assertChange(3, 0, false, 3L, 0L);
        assertChange(3, 1, false, 2L, 1L);
        assertChange(3, 2, false, 1L, 2L);

    }

    @Test
    public void moveUpTest() {
        relationList.moveUp(data.rel3);
        assertRelations(relationList.getRelations(), data.rel3, data.rel1, data.rel2);

        assertChange(2, 0, false, 3L, 0L);
        assertChange(2, 1, false, 1L, 1L);
    }

    @Test
    public void moveDownTest() {
        relationList.moveDown(data.rel3);
        assertRelations(relationList.getRelations(), data.rel1, data.rel2, data.rel3);

        assertChange(2, 0, false, 2L, 1L);
        assertChange(2, 1, false, 3L, 2L);
    }

    @Test
    public void moveDownInvalidTest() {
        relationList.moveDown(data.rel2);
        assertRelations(relationList.getRelations(), data.rel1, data.rel3, data.rel2);

        assertChange(0, 0, false, 0L, 0L);
    }

    @Test
    public void addNewTest() {
        relationList.add(data.newRel);
        relationList.change(data.newRel);
        assertRelations(relationList.getRelations(), data.rel1, data.rel3, data.rel2, data.newRel);

        assertChange(1, 0, false, IFConsts.NEW_ITEM_ID, 3L);
    }

    @Test
    public void deleteTest() {
        relationList.delete(data.rel1);
        assertRelations(relationList.getRelations(), data.rel3, data.rel2);

        assertChange(3, 0, false, 3L, 0L);
        assertChange(3, 1, false, 2L, 1L);
        assertChange(3, 2, true, 1L, 0L);
    }

    @Test
    public void manyChangeTest() {
        relationList.add(data.newRel); // 1,3,2,new
        relationList.move(data.rel1, 3); // 3,2,new,1
        relationList.moveUp(data.rel1); // 3,2,1,new
        relationList.delete(data.rel2); // 3,1,new

        assertRelations(relationList.getRelations(), data.rel3, data.rel1, data.newRel);
        assertEquals(3, relationList.getChanges().size());

    }

    @Test
    public void addRemoveTest() {
        relationList.add(data.newRel);
        relationList.delete(data.newRel);

        assertRelations(relationList.getRelations(), data.rel1, data.rel3, data.rel2);
        assertEquals(0, relationList.getChanges().size());

    }

    private void assertRelations(List<Relation> list, Relation... expected) {
        for (int i = 0; i < list.size(); i++) {
            assertEquals(expected[i], list.get(i));
        }
    }

    private void assertChange(int size, int itemNr, boolean isKvoNull, long id, long order) {
        assertEquals(size, relationList.getChanges().size());
        if (relationList.getChanges().size() > itemNr) {
            Relation changedRel = relationList.getChanges().get(itemNr);
            if (isKvoNull)
                assertNull(changedRel.getKvo());
            else
                assertNotNull(changedRel.getKvo());
            if (!isKvoNull) {
                assertEquals(id, changedRel.getKvo().getId().longValue());
                assertEquals(
                    order,
                    handlerFactory
                        .createHandler(changedRel.getKvo())
                        .getLong(IFConsts.KEY_ORDERNUM)
                        .longValue());
            }
        }
    }

}
