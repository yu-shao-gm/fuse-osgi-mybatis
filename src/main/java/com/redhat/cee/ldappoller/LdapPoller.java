/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */

/*
 * Yu Shao <yshao@redhat.com>
 */

package com.redhat.cee.ldappoller;

import org.apache.camel.CamelContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

import com.redhat.cee.ldappoller.LdapEntry;
import com.redhat.cee.ldappoller.LdapEntryMapper;

public class LdapPoller {

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapPoller.class);

    private DataSource dataSource;
    private LdapEntryDao ldapEntryDao;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
     
    public LdapEntryDao getLdapEntryDao() {
        return ldapEntryDao;
    }

    public void setLdapEntryDao(LdapEntryDao ldapEntryDao) {
        this.ldapEntryDao = ldapEntryDao;
    }

    
    public Boolean ldapPoller(CamelContext camelContext) {
        
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Connection conn = null;
        Statement stmt = null;

        LOGGER.info("Ldap Poller Syncing ...  ");

        
        try {
         
            conn = dataSource.getConnection();
            DatabaseMetaData dbm = conn.getMetaData();
  
            ResultSet tables = dbm.getTables(null, null, "ldap_in_hydra", null);
  
            if (tables.next()) {
  
                LOGGER.info("Table ldap_in_hydra exists.");
                stmt = conn.createStatement();
                String sql = "select * from ldap_in_hydra where uid=\"pcormier\"";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String last = rs.getString("attribute_value");
                    LOGGER.info("Attribute Value: " + last);
                }
  
                rs.close();
                stmt.close();
                conn.close();

                LdapEntry newLdapEntry = new LdapEntry();

                newLdapEntry.setUid("abc");
                newLdapEntry.setAttribute("abc");
                newLdapEntry.setAttributeValue("abc");

                ldapEntryDao.insertLdapEntry(newLdapEntry);

                
                
                
            } else {
                LOGGER.info("Table ldap_in_hydra doesn't exists.");
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
              if (stmt != null)
                stmt.close();
              if (conn != null)
                conn.close();
            } catch (SQLException se) {
              se.printStackTrace();
            }
        }
        
        return true;
    }

    public void create() throws Exception {
        
    }
    
    public void destroy() throws Exception {
        
    }
}
