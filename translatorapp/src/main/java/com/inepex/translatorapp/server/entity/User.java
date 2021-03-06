package com.inepex.translatorapp.server.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.inepex.ineForm.annotations.Kvo_Fetch;
import com.inepex.ineForm.annotations.Kvo_Fetch.Mode;
import com.inepex.ineForm.annotations.Kvo_SearchParam;
import com.inepex.ineForm.annotations.Kvo_Transparent;
import com.inepex.ineFrame.server.auth.AuthUser;

@Entity
@Table(name = "TransUser")
public class User implements AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Kvo_SearchParam
    private Long id;

    @Kvo_SearchParam
    @Column(nullable = false, unique = true)
    private String email;

    @Kvo_Transparent
    @Column(nullable = false)
    private String password;

    private String role;

    @Kvo_Fetch(mode = Mode.fullObject)
    @OneToMany(mappedBy = "user", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<UserLang> translates = new ArrayList<>();

    public User() {}

    public User(Long id) {
        this.id = id;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Long getUserId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return email;
    }

    @Override
    public String getUserAuthString() {
        return email;
    }

    @Override
    public Set<String> getAllowedRoles() {
        if (role == null || role.length() < 1)
            return Collections.emptySet();

        return new HashSet<>(Arrays.asList(role));
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserLang> getTranslates() {
        return translates;
    }

    public void setTranslates(List<UserLang> translates) {
        this.translates = translates;
    }

    @Override
    public Map<String, String> getUserJsonProps() {
        return null;
    }
}
