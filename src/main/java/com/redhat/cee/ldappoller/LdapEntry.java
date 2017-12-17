package com.redhat.cee.ldappoller;

import java.util.Date;

public class LdapEntry {
    private Integer id;
    private String uid;
    private String attribute;
    private String attribute_value;
    private Date modifytime;

    public LdapEntry(){} 

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAttribute() {
        return attribute;
    }
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeValue() {
        return attribute_value;
    }
    public void setAttributeValue(String attribute_value) {
        this.attribute_value = attribute_value;
    }

    public Date getModifytime() {
        return modifytime;
    }
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }
}
