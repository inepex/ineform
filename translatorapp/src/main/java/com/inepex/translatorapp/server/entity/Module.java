package com.inepex.translatorapp.server.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.inepex.ineForm.annotations.Kvo_Fetch;
import com.inepex.ineForm.annotations.Kvo_Fetch.Mode;
import com.inepex.ineForm.annotations.Kvo_SearchParam;

@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Kvo_SearchParam
    private Long id;

    @Kvo_SearchParam
    @Column(nullable = false, unique = true)
    private String name;

    @Kvo_Fetch(mode = Mode.idRelation)
    @OneToMany(mappedBy = "module", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<ModuleRow> rows = new ArrayList<>();

    @Kvo_Fetch(mode = Mode.fullObject)
    @OneToMany(mappedBy = "module", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<ModuleLang> langs = new ArrayList<>();

    public Module() {

    }

    public Module(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return name;
    }

    public List<ModuleRow> getRows() {
        return rows;
    }

    public void setRows(List<ModuleRow> rows) {
        this.rows = rows;
    }

    public List<ModuleLang> getLangs() {
        return langs;
    }

    public void setLangs(List<ModuleLang> langs) {
        this.langs = langs;
    }
}
