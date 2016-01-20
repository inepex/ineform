package com.inepex.example.ContactManager.entity.assist;

import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.UserConsts;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class UserAssist extends Assist {

    public UserAssist(DescriptorStore descStore) {
        super(descStore);
    }

    @Override
    protected void registerExtraDescriptors() {}

    @Override
    public ValidatorDesc getValidatorDesc() {
        return new ValidatorDesc(
            UserConsts.descriptorName,
            new String[] { /*
                            * hc:vd1
                            */

                /* hc */ });
    }

    @Override
    public ObjectDesc getObjectDesc() {
        ObjectDesc objDesc = new ObjectDesc(
            UserConsts.descriptorName,
            new LongFDesc(
                UserConsts.k_id,
                /* hc:d1 */CMI18n.user_id()/* hc */)/*
                                                     * hc : d2_1
                                                     *//* hc */
                ,
            new StringFDesc(
                UserConsts.k_firstName,
                /* hc:d2 */CMI18n.user_firstName()/* hc */)/*
                                                            * hc : d2_2
                                                            *//* hc */
                    .mandatory(),
            new StringFDesc(
                UserConsts.k_lastName,
                /* hc:d3 */CMI18n.user_lastName()/* hc */)/*
                                                           * hc : d2_3
                                                           *//* hc */
                    .mandatory(),
            new StringFDesc(
                UserConsts.k_email,
                /* hc:d4 */CMI18n.user_email()/* hc */)/*
                                                        * hc : d2_4
                                                        *//* hc */
                    .mandatory());

        objDesc.setDefaultOrderKey(getOrderKey());
        return objDesc;
    }

    @Override
    public TableRDesc getTableRDesc() {
        TableRDesc tableRDesc = new TableRDesc(UserConsts.descriptorName);

        tableRDesc
            .getRootNode()
            .addChild(
                UserConsts.k_id,
                new ColRDesc(/* hc:tdr1_1 */true/* hc */)/*
                                                          * hc : tdr2_1
                                                          *//* hc */)
            .addChild(
                UserConsts.k_firstName,
                new ColRDesc(/* hc:tdr1_2 */true/* hc */)/*
                                                          * hc : tdr2_2
                                                          *//* hc */)
            .addChild(
                UserConsts.k_lastName,
                new ColRDesc(/* hc:tdr1_3 */true/* hc */)/*
                                                          * hc : tdr2_3
                                                          *//* hc */)
            .addChild(
                UserConsts.k_email,
                new ColRDesc(/* hc:tdr1_4 */true/* hc */)/*
                                                          * hc : tdr2_4
                                                          *//* hc */);
        return tableRDesc;
    }

    @Override
    public FormRDesc getFormRDesc() {
        FormRDesc formRDesc = new FormRDesc(
            UserConsts.descriptorName/*
                                      * hc:frd_props
                                      */

        /* hc */);

        formRDesc
            .getRootNode()
            .addChild(UserConsts.k_id, new WidgetRDesc(/* hc:f1 */FWTypes.LABEL/* hc */))
            .addChild(UserConsts.k_firstName, new WidgetRDesc(/* hc:f2 */FWTypes.TEXTBOX/* hc */))
            .addChild(UserConsts.k_lastName, new WidgetRDesc(/* hc:f3 */FWTypes.TEXTBOX/* hc */))
            .addChild(UserConsts.k_email, new WidgetRDesc(/* hc:f4 */FWTypes.TEXTBOX/* hc */));
        return formRDesc;
    }

    @Override
    public ObjectDesc getSearchObjectDesc() {
        return new ObjectDesc(
            UserConsts.searchDescriptor,
            new LongFDesc(UserConsts.s_id, /* hc:ds1 */"Id"/* hc */),
            new StringFDesc(UserConsts.s_firstName, /* hc:ds2 */"FirstName"/* hc */),
            new StringFDesc(UserConsts.s_lastName, /* hc:ds3 */"LastName"/* hc */),
            new StringFDesc(UserConsts.s_email, /* hc:ds4 */"Email"/* hc */));
    }

    @Override
    public FormRDesc getSearchFormRDesc() {
        FormRDesc searchFormRDesc = new FormRDesc(UserConsts.searchDescriptor);

        searchFormRDesc
            .getRootNode()
            .dummy()
            .addChild(UserConsts.s_id, new WidgetRDesc(/* hc:fs1 */FWTypes.LABEL/* hc */))
            .addChild(UserConsts.s_firstName, new WidgetRDesc(/* hc:fs2 */FWTypes.TEXTBOX/* hc */))
            .addChild(UserConsts.s_lastName, new WidgetRDesc(/* hc:fs3 */FWTypes.TEXTBOX/* hc */))
            .addChild(UserConsts.s_email, new WidgetRDesc(/* hc:fs4 */FWTypes.TEXTBOX/* hc */));
        return searchFormRDesc;
    }

    public static String getOrderKey() {
        // displayname field
        String key = "";
        return key;
    }
}
