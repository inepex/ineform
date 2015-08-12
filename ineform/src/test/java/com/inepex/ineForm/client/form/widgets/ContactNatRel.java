package com.inepex.ineForm.client.form.widgets;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.inepex.ineForm.annotations.Kvo_SortDefault;
import com.inepex.ineForm.annotations.Kvo_Transparent;

public class ContactNatRel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @Kvo_Transparent
    private Contact contact;
    @ManyToOne
    @Kvo_SortDefault
    private Nationality nationality;
    private Long orderNum;

    public ContactNatRel() {}

    public ContactNatRel(Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

}
