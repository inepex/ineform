package com.inepex.example.ContactManager.entity.assist;

import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberConsts;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberTypeConsts;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class PhoneNumberAssist extends Assist {

    public PhoneNumberAssist(DescriptorStore descStore) {
        super(descStore);
    }

    @Override
    protected void registerExtraDescriptors() {}

    @Override
    public ValidatorDesc getValidatorDesc() {
        return new ValidatorDesc(
            PhoneNumberConsts.descriptorName,
            new String[] { /*
                            * hc : vd1
                            */

                /* hc */ });
    }

    @Override
    public ObjectDesc getObjectDesc() {
        ObjectDesc objDesc = new ObjectDesc(
            PhoneNumberConsts.descriptorName,
            new LongFDesc(
                PhoneNumberConsts.k_id,
                /* hc:d1 */CMI18n.phoneNumber_id()/* hc */)/*
                                                            * hc : d2_1
                                                            *//* hc */
                ,
            new StringFDesc(
                PhoneNumberConsts.k_number,
                /* hc:d2 */CMI18n.phoneNumber_number()/* hc */)/*
                                                                * hc : d2_2
                                                                *//* hc */
                    .mandatory(),
            new RelationFDesc(PhoneNumberConsts.k_type, /* hc:d3 */CMI18n.phoneNumber_type()/* hc */
            , PhoneNumberTypeConsts.descriptorName)/* hc:d2_3 *//* hc */
                .mandatory());

        objDesc.setDefaultOrderKey(getOrderKey());
        return objDesc;
    }

    @Override
    public TableRDesc getTableRDesc() {
        TableRDesc tableRDesc = new TableRDesc(PhoneNumberConsts.descriptorName);

        tableRDesc
            .getRootNode()
            .addChild(
                PhoneNumberConsts.k_id,
                new ColRDesc(/* hc:tdr1_1 */true/* hc */)/*
                                                          * hc : tdr2_1
                                                          *//* hc */)
            .addChild(
                PhoneNumberConsts.k_number,
                new ColRDesc(/* hc:tdr1_2 */true/* hc */)/*
                                                          * hc : tdr2_2
                                                          *//* hc */)
            .addChild(
                PhoneNumberConsts.k_type,
                new ColRDesc(/* hc:tdr1_3 */true/* hc */)/*
                                                          * hc : tdr2_3
                                                          *//* hc */);
        return tableRDesc;
    }

    @Override
    public FormRDesc getFormRDesc() {
        FormRDesc formRDesc = new FormRDesc(
            PhoneNumberConsts.descriptorName/*
                                             * hc: frd_props
                                             */

        /* hc */);

        formRDesc
            .getRootNode()
            .addChild(PhoneNumberConsts.k_number, new WidgetRDesc(/* hc:f2 */FWTypes.PHONE/* hc */))
            .addChild(
                PhoneNumberConsts.k_type,
                new WidgetRDesc(/* hc:f3 */FWTypes.LISTBOX/* hc */));
        return formRDesc;
    }

    @Override
    public ObjectDesc getSearchObjectDesc() {
        return new ObjectDesc(
            PhoneNumberConsts.searchDescriptor,
            new LongFDesc(PhoneNumberConsts.s_id, /* hc:ds1 */"Id"/* hc */),
            new StringFDesc(PhoneNumberConsts.s_number, /* hc:ds2 */"Number"/* hc */),
            new RelationFDesc(PhoneNumberConsts.s_type, /* hc:ds3 */"Type"/* hc */
            , PhoneNumberTypeConsts.searchDescriptor));
    }

    @Override
    public FormRDesc getSearchFormRDesc() {
        FormRDesc searchFormRDesc = new FormRDesc(PhoneNumberConsts.searchDescriptor);

        searchFormRDesc
            .getRootNode()
            .dummy()
            .addChild(PhoneNumberConsts.s_id, new WidgetRDesc(/* hc:fs1 */FWTypes.LABEL/* hc */))
            .addChild(
                PhoneNumberConsts.s_number,
                new WidgetRDesc(/* hc:fs2 */FWTypes.TEXTBOX/* hc */))
            .addChild(
                PhoneNumberConsts.s_type,
                new WidgetRDesc(/* hc:fs3 */FWTypes.LISTBOX/* hc */))

        ;
        return searchFormRDesc;
    }

    public static String getOrderKey() {
        // displayname field
        String key = "";
        return key;
    }
}
