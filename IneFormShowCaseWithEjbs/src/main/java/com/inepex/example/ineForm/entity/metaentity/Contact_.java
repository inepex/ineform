package com.inepex.example.ineForm.entity.metaentity;

import com.inepex.example.ineForm.entity.Contact;
import com.inepex.example.ineForm.entity.ContactAddresDetail;
import com.inepex.example.ineForm.entity.ContactCTypeRel;
import com.inepex.example.ineForm.entity.ContactNatRel;
import com.inepex.example.ineForm.entity.Contact_ContactRole;
import com.inepex.example.ineForm.entity.Contact_ContactState;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2011-05-06T18:32:16")
@StaticMetamodel(Contact.class)
public class Contact_ { 

    public static volatile SingularAttribute<Contact, String> lastName;
    public static volatile ListAttribute<Contact, Contact_ContactState> states;
    public static volatile SingularAttribute<Contact, ContactAddresDetail> addressDetail;
    public static volatile SingularAttribute<Contact, Long> id;
    public static volatile ListAttribute<Contact, ContactNatRel> nationalities;
    public static volatile SingularAttribute<Contact, String> address;
    public static volatile SingularAttribute<Contact, Boolean> happy;
    public static volatile ListAttribute<Contact, Contact_ContactRole> roles;
    public static volatile SingularAttribute<Contact, String> profilePhoto;
    public static volatile SingularAttribute<Contact, Long> numOfAccess;
    public static volatile ListAttribute<Contact, ContactCTypeRel> contactTypes;
    public static volatile SingularAttribute<Contact, Long> createDate;
    public static volatile SingularAttribute<Contact, String> firstName;

}