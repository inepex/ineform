package com.inepex.translatorapp.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.inepex.ineForm.annotations.Kvo_SearchParam;

@Entity
public class Lang {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Kvo_SearchParam
    private Long id;

    @Kvo_SearchParam
    @Column(nullable = false, unique = true)
    private String isoName;

    @Column(nullable = false)
    private String countryCode;

    public Lang() {

    }

    public Lang(Long id) {
        this.id = id;
    }

    public Lang(String isoName, String countryCode) {
        this.isoName = isoName;
        this.countryCode = countryCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsoName() {
        return isoName;
    }

    public void setIsoName(String isoName) {
        this.isoName = isoName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return isoName;
    }
}
