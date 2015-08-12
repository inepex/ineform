package com.inepex.ineForm.client.form.widgets;

import java.util.ArrayList;
import java.util.List;

import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.datamanipulator.ValueRangeResultCallback;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.assist.ContactAssist;
import com.inepex.ineForm.client.form.widgets.assist.ContactNatRelAssist;
import com.inepex.ineForm.client.form.widgets.assist.NationalityAssist;
import com.inepex.ineForm.client.form.widgets.kvo.ContactKVO;
import com.inepex.ineForm.client.form.widgets.kvo.ContactNatRelKVO;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptor.fdesc.ListFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class RelationTestData {

    public static ValueRangeProvider valueRangeProvider = new ValueRangeProvider() {

        @Override
        public void getRelationValueRange(FDesc fieldDesc, ValueRangeResultCallback callback) {
            ArrayList<Relation> relations = new ArrayList<Relation>();
            relations.add(new Relation(1L, "1L"));
            relations.add(new Relation(2L, "2L"));
            relations.add(new Relation(3L, "3L"));
            callback.onValueRangeResultReady(relations);
        }
    };

    Relation rel1;
    Relation rel2;
    Relation rel3;
    Relation newRel;
    ListFDesc fieldDesc;
    List<Relation> relations;

    FormContext formCtx;

    public RelationTestData(DescriptorStore descStore) {
        create(descStore);
    }

    public void create(DescriptorStore descStore) {
        ContactAssist.registerDescriptors(descStore);
        ContactNatRelAssist.registerDescriptors(descStore);
        NationalityAssist.registerDescriptors(descStore);

        fieldDesc = (ListFDesc) ContactAssist.getObjectDesc().getField(ContactKVO.k_nationalities);

        createRelations();
        relations = new ArrayList<Relation>();
        relations.add(rel1);
        relations.add(rel2);
        relations.add(rel3);

    }

    private void createRelations() {
        ContactNatRelKVO kvo1 = new ContactNatRelKVO();
        kvo1.setId(1L);
        kvo1.setOrderNum(0L);
        kvo1.setNationality(new Relation(1L, "1L"));

        ContactNatRelKVO kvo2 = new ContactNatRelKVO();
        kvo2.setId(2L);
        kvo2.setOrderNum(2L);
        kvo2.setNationality(new Relation(2L, "2L"));

        ContactNatRelKVO kvo3 = new ContactNatRelKVO();
        kvo3.setId(3L);
        kvo3.setOrderNum(1L);
        kvo3.setNationality(new Relation(3L, "3L"));

        ContactNatRelKVO newRelKvo = new ContactNatRelKVO();
        newRelKvo.setId(0L);

        rel1 = new Relation(kvo1);
        rel1.setDisplayName("1L");
        rel2 = new Relation(kvo2);
        rel2.setDisplayName("2L");
        rel3 = new Relation(kvo3);
        rel3.setDisplayName("3L");

        newRel = new Relation(newRelKvo);
        newRel.setDisplayName("0L");
    }
}
