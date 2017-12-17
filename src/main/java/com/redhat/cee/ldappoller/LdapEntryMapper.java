package com.redhat.cee.ldappoller;

import com.redhat.cee.ldappoller.LdapEntry;

public interface LdapEntryMapper {
    void insertLdapEntry(LdapEntry ldapEntry);
}