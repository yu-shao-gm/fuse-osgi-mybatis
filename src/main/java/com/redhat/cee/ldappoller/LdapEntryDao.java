package com.redhat.cee.ldappoller;

import com.redhat.cee.ldappoller.LdapEntryMapper;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class LdapEntryDao extends SqlSessionDaoSupport {

    public void insertLdapEntry(LdapEntry ldapEntry) {
        if (null == ldapEntry) {
            return ;
        }

        LdapEntryMapper mapper = getSqlSession().getMapper(LdapEntryMapper.class);
        mapper.insertLdapEntry(ldapEntry);
    }

}