package com.inepex.example.ineForm.entity.metaentity;

import com.inepex.example.ineForm.entity.Contact;
import com.inepex.example.ineForm.entity.ContactNatRel;
import com.inepex.example.ineForm.entity.Nationality;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2011-05-06T18:32:16")
@StaticMetamodel(ContactNatRel.class)
public class ContactNatRel_ { 

    public static volatile SingularAttribute<ContactNatRel, Long> id;
    public static volatile SingularAttribute<ContactNatRel, Nationality> nationality;
    public static volatile SingularAttribute<ContactNatRel, Long> orderNum;
    public static volatile SingularAttribute<ContactNatRel, Contact> contact;

}