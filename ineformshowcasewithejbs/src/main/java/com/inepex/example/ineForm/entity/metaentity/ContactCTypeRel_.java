package com.inepex.example.ineForm.entity.metaentity;

import com.inepex.example.ineForm.entity.Contact;
import com.inepex.example.ineForm.entity.ContactCTypeRel;
import com.inepex.example.ineForm.entity.ContactType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2011-05-06T18:32:16")
@StaticMetamodel(ContactCTypeRel.class)
public class ContactCTypeRel_ { 

    public static volatile SingularAttribute<ContactCTypeRel, Long> id;
    public static volatile SingularAttribute<ContactCTypeRel, ContactType> contactType;
    public static volatile SingularAttribute<ContactCTypeRel, Long> orderNum;
    public static volatile SingularAttribute<ContactCTypeRel, Contact> contact;

}