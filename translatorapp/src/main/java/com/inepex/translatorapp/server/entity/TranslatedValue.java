package com.inepex.translatorapp.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.inepex.ineForm.annotations.Kvo_Fetch;
import com.inepex.ineForm.annotations.Kvo_Fetch.Mode;
import com.inepex.ineForm.annotations.Kvo_SearchParam;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "ROW_ID", "LANG_ID" }))
public class TranslatedValue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Kvo_SearchParam
    private Long id;

    private Long lastModTime = null;

    @ManyToOne
    @Kvo_Fetch(mode = Mode.idRelation)
    private User lastModUser = null;

    @Lob
    private String value;

    @ManyToOne
    @JoinColumn(nullable = false)
    @Kvo_SearchParam
    private Lang lang;

    @ManyToOne
    @JoinColumn(nullable = false)
    @Kvo_SearchParam
    @Kvo_Fetch(mode = Mode.idRelation)
    private ModuleRow row;

    public TranslatedValue() {}

    public TranslatedValue(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return value == null ? "" : value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLastModTime() {
        return lastModTime;
    }

    public void setLastModTime(Long lastModTime) {
        this.lastModTime = lastModTime;
    }

    public User getLastModUser() {
        return lastModUser;
    }

    public void setLastModUser(User lastModUser) {
        this.lastModUser = lastModUser;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public ModuleRow getRow() {
        return row;
    }

    public void setRow(ModuleRow row) {
        this.row = row;
    }
}
