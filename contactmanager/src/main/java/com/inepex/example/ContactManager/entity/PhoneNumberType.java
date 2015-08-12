package com.inepex.example.ContactManager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.inepex.ineForm.annotations.Kvo_SearchParam;

@Entity
public class PhoneNumberType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Kvo_SearchParam
    private Long id;

    @Kvo_SearchParam
    @Column(nullable = false)
    private String name;

    public PhoneNumberType() {}

    public PhoneNumberType(Long id) {
        this.id = id;
    }

    public PhoneNumberType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
