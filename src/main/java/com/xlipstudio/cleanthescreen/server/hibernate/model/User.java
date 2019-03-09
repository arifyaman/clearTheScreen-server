package com.xlipstudio.cleanthescreen.server.hibernate.model;

import com.xlipstudio.cleanthescreen.server.hibernate.model.sub.BaseEntity;
import com.xlipstudio.cleanthescreen.server.hibernate.model.sub.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5733840493659566553L;


    private String name;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String userKey;


    private Date lastLogin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
