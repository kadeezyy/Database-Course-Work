/*
 * This file is generated by jOOQ.
 */
package com.example.musicplatform.entity.tables.pojos;


import com.example.musicplatform.entity.enums.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CustomUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private Role roleId;
    private String username;
    private String password;
    private String email;
    private LocalDateTime creationDate;

    public CustomUser() {}

    public CustomUser(CustomUser value) {
        this.id = value.id;
        this.roleId = value.roleId;
        this.username = value.username;
        this.password = value.password;
        this.email = value.email;
        this.creationDate = value.creationDate;
    }

    public CustomUser(
        UUID id,
        Role roleId,
        String username,
        String password,
        String email,
        LocalDateTime creationDate
    ) {
        this.id = id;
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.creationDate = creationDate;
    }

    /**
     * Getter for <code>public.custom_user.id</code>.
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Setter for <code>public.custom_user.id</code>.
     */
    public CustomUser setId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Getter for <code>public.custom_user.role_id</code>.
     */
    public Role getRoleId() {
        return this.roleId;
    }

    /**
     * Setter for <code>public.custom_user.role_id</code>.
     */
    public CustomUser setRoleId(Role roleId) {
        this.roleId = roleId;
        return this;
    }

    /**
     * Getter for <code>public.custom_user.username</code>.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Setter for <code>public.custom_user.username</code>.
     */
    public CustomUser setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Getter for <code>public.custom_user.password</code>.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter for <code>public.custom_user.password</code>.
     */
    public CustomUser setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Getter for <code>public.custom_user.email</code>.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Setter for <code>public.custom_user.email</code>.
     */
    public CustomUser setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Getter for <code>public.custom_user.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    /**
     * Setter for <code>public.custom_user.creation_date</code>.
     */
    public CustomUser setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final CustomUser other = (CustomUser) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.roleId == null) {
            if (other.roleId != null)
                return false;
        }
        else if (!this.roleId.equals(other.roleId))
            return false;
        if (this.username == null) {
            if (other.username != null)
                return false;
        }
        else if (!this.username.equals(other.username))
            return false;
        if (this.password == null) {
            if (other.password != null)
                return false;
        }
        else if (!this.password.equals(other.password))
            return false;
        if (this.email == null) {
            if (other.email != null)
                return false;
        }
        else if (!this.email.equals(other.email))
            return false;
        if (this.creationDate == null) {
            if (other.creationDate != null)
                return false;
        }
        else if (!this.creationDate.equals(other.creationDate))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.roleId == null) ? 0 : this.roleId.hashCode());
        result = prime * result + ((this.username == null) ? 0 : this.username.hashCode());
        result = prime * result + ((this.password == null) ? 0 : this.password.hashCode());
        result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = prime * result + ((this.creationDate == null) ? 0 : this.creationDate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CustomUser (");

        sb.append(id);
        sb.append(", ").append(roleId);
        sb.append(", ").append(username);
        sb.append(", ").append(password);
        sb.append(", ").append(email);
        sb.append(", ").append(creationDate);

        sb.append(")");
        return sb.toString();
    }
}
