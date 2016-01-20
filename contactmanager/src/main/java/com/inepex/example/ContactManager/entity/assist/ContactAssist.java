package com.inepex.example.ContactManager.entity.assist;

import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.CompanyConsts;
import com.inepex.example.ContactManager.entity.kvo.ContactConsts;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressConsts;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberConsts;
import com.inepex.ineForm.shared.descriptorext.Assist;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.descriptor.fdesc.ListFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.PropFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore.Marker;

public class ContactAssist extends Assist {

    public static final String roFRD = "roFRD";

    public ContactAssist(DescriptorStore descStore) {
        super(descStore);
    }

    @Override
    protected void registerExtraDescriptors() {
        descStore.addNamedTypedDesc(
            Marker.registered,
            ContactConsts.descriptorName,
            roFRD,
            getROFormRDesc());
    }

    @Override
    public ValidatorDesc getValidatorDesc() {
        return new ValidatorDesc(
            ContactConsts.descriptorName,
            new String[] { /*
                            * hc: vd1
                            */

                /* hc */ });
    }

    @Override
    public ObjectDesc getObjectDesc() {
        ObjectDesc objDesc = new ObjectDesc(
            ContactConsts.descriptorName,
            new LongFDesc(
                ContactConsts.k_id, /* hc:d1 */
                CMI18n.contact_id()/* hc */)/* hc:d2_1 *//* hc */
                ,
            new StringFDesc(
                ContactConsts.k_name,
                /* hc:d2 */CMI18n.contact_name()/* hc */)/*
                                                          * hc : d2_2
                                                          *//* hc */
                    .mandatory(),
            new ListFDesc(
                ContactConsts.k_phone, /* hc:d3 */
                CMI18n.contact_phone()/* hc */,
                PhoneNumberConsts.descriptorName)/* hc:d2_3 *//* hc */
                ,
            new ListFDesc(
                ContactConsts.k_email, /* hc:d4 */
                CMI18n.contact_email()/* hc */,
                EmailAddressConsts.descriptorName)/* hc:d2_4 *//* hc */
                ,
            new RelationFDesc(ContactConsts.k_company, /* hc:d5 */CMI18n.contact_company()/* hc */
            , CompanyConsts.descriptorName)/* hc:d2_5 *//* hc */
            ,
            new PropFDesc(
                ContactConsts.k_propsUser,
                ContactConsts.k_propsUser,
                ContactConsts.props_user));

        objDesc.setDefaultOrderKey(getOrderKey());
        return objDesc;
    }

    @Override
    public TableRDesc getTableRDesc() {
        TableRDesc tableRDesc = new TableRDesc(ContactConsts.descriptorName);

        tableRDesc
            .getRootNode()
            .addChild(
                ContactConsts.k_name,
                new ColRDesc(/* hc:tdr1_2 */true/* hc */)/*
                                                          * hc : tdr2_2
                                                          *//* hc */)
            .addChild(
                ContactConsts.k_phone,
                new ColRDesc(/* hc:tdr1_3 *//* hc */)/*
                                                      * hc : tdr2_3
                                                      *//* hc */)
            .addChild(
                ContactConsts.k_email,
                new ColRDesc(/* hc:tdr1_4 *//* hc */)/*
                                                      * hc : tdr2_4
                                                      *//* hc */)
            .addChild(ContactConsts.k_note, new ColRDesc().setDisplayName("Note"));
        return tableRDesc;
    }

    public FormRDesc getROFormRDesc() {
        FormRDesc formRDesc = new FormRDesc(ContactConsts.descriptorName);

        formRDesc
            .getRootNode()
            .addChild(ContactConsts.k_id, new WidgetRDesc(/* hc:f1 */FWTypes.LABEL/* hc */))
            .addChild(ContactConsts.k_name, new WidgetRDesc(/* hc:f2 */FWTypes.LABEL/* hc */))
            .addChild(ContactConsts.k_phone, new WidgetRDesc(/* hc:f3 */FWTypes.LABEL/* hc */))
            .addChild(ContactConsts.k_email, new WidgetRDesc(/* hc:f4 */FWTypes.LABEL/* hc */));

        return formRDesc;
    }

    @Override
    public FormRDesc getFormRDesc() {
        FormRDesc formRDesc = new FormRDesc(
            ContactConsts.descriptorName/*
                                         * hc: frd_props
                                         */

        /* hc */);

        formRDesc
            .getRootNode()
            .addChild(ContactConsts.k_id, new WidgetRDesc(/* hc:f1 */FWTypes.LABEL/* hc */))
            .addChild(ContactConsts.k_name, new WidgetRDesc(/* hc:f2 */FWTypes.TEXTBOX/* hc */))
            .addChild(
                ContactConsts.k_phone,
                new WidgetRDesc(/* hc:f3 */FWTypes.RELATIONLIST/* hc */))
            .addChild(
                ContactConsts.k_email,
                new WidgetRDesc(/* hc:f4 */FWTypes.RELATIONLIST/* hc */))
            .addChild(ContactConsts.k_propsUser, new WidgetRDesc(FWTypes.PROPS));
        return formRDesc;
    }

    @Override
    public ObjectDesc getSearchObjectDesc() {
        return new ObjectDesc(
            ContactConsts.searchDescriptor,
            new LongFDesc(ContactConsts.s_id, /* hc:ds1 */"Id"/* hc */),
            new StringFDesc(ContactConsts.s_name, /* hc:ds2 */"Name"/* hc */),
            new RelationFDesc(ContactConsts.s_company, /* hc:ds5 */"Company"/* hc */
            , CompanyConsts.searchDescriptor));
    }

    @Override
    public FormRDesc getSearchFormRDesc() {
        FormRDesc searchFormRDesc = new FormRDesc(ContactConsts.searchDescriptor);

        searchFormRDesc
            .getRootNode()
            .dummy()
            .addChild(ContactConsts.s_id, new WidgetRDesc(/* hc:fs1 */FWTypes.LABEL/* hc */))
            .addChild(ContactConsts.s_name, new WidgetRDesc(/* hc:fs2 */FWTypes.TEXTBOX/* hc */))
            .addChild(ContactConsts.s_company, new WidgetRDesc(/* hc:fs5 */FWTypes.LISTBOX/* hc */))

        ;
        return searchFormRDesc;
    }

    public static String getOrderKey() {
        // displayname field
        String key = "";
        return key;
    }
}
