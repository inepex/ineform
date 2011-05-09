package com.inepex.example.ineForm.entity.metaentity;

import com.inepex.example.ineForm.entity.Contact;
import com.inepex.example.ineForm.entity.Contact_ContactState;
import com.inepex.example.ineForm.enums.ContactState;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2011-05-06T18:32:16")
@StaticMetamodel(Contact_ContactState.class)
public class Contact_ContactState_ { 

    public static volatile SingularAttribute<Contact_ContactState, Long> id;
    public static volatile SingularAttribute<Contact_ContactState, ContactState> state;
    public static volatile SingularAttribute<Contact_ContactState, Long> orderNum;
    public static volatile SingularAttribute<Contact_ContactState, Contact> contact;

}