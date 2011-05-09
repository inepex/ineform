package com.inepex.example.ineForm.entity.metaentity;

import com.inepex.example.ineForm.entity.Contact;
import com.inepex.example.ineForm.entity.Contact_ContactRole;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2011-05-06T18:32:16")
@StaticMetamodel(Contact_ContactRole.class)
public class Contact_ContactRole_ { 

    public static volatile SingularAttribute<Contact_ContactRole, Long> id;
    public static volatile SingularAttribute<Contact_ContactRole, Long> orderNum;
    public static volatile SingularAttribute<Contact_ContactRole, String> role;
    public static volatile SingularAttribute<Contact_ContactRole, Contact> contact;

}