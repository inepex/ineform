package com.inepex.ineFrame.shared.auth;

import java.util.Map;
import java.util.Set;

import com.inepex.ineFrame.server.auth.AuthUser;
import com.inepex.ineom.shared.AssistedObjectHandler;

public class AssistedObjectAuthUser implements AuthUser {

    private AssistedObjectHandler user;
    private String displayNameKey;
    private String emailKey;

    public AssistedObjectAuthUser(AssistedObjectHandler user, String displayNameKey, String emailKey) {
        super();
        this.user = user;
        this.displayNameKey = displayNameKey;
        this.emailKey = emailKey;
    }

    @Override
    public Long getUserId() {
        return user.getId();
    }

    @Override
    public String getDisplayName() {
        return user.getString(displayNameKey);
    }

    @Override
    public String getUserAuthString() {
        return user.getString(emailKey);
    }

    @Override
    public Set<String> getAllowedRoles() {
        return null;
    }

    @Override
    public Map<String, String> getUserJsonProps() {
        return null;
    }

}
