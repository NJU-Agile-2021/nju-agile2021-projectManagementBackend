package com.nju.projectManagement.DO;

import java.util.ArrayList;
import java.util.Arrays;

public class UserDOWithBLOBs extends UserDO {
    private byte[] sslCipher;

    private byte[] x509Issuer;

    private byte[] x509Subject;

    private String authenticationString;

    private String userAttributes;

    public byte[] getSslCipher() {
        return sslCipher;
    }

    public void setSslCipher(byte[] sslCipher) {
        this.sslCipher = sslCipher;
    }

    public byte[] getX509Issuer() {
        return x509Issuer;
    }

    public void setX509Issuer(byte[] x509Issuer) {
        this.x509Issuer = x509Issuer;
    }

    public byte[] getX509Subject() {
        return x509Subject;
    }

    public void setX509Subject(byte[] x509Subject) {
        this.x509Subject = x509Subject;
    }

    public String getAuthenticationString() {
        return authenticationString;
    }

    public void setAuthenticationString(String authenticationString) {
        this.authenticationString = authenticationString == null ? null : authenticationString.trim();
    }

    public String getUserAttributes() {
        return userAttributes;
    }

    public void setUserAttributes(String userAttributes) {
        this.userAttributes = userAttributes == null ? null : userAttributes.trim();
    }

    public enum Column {
        user("User", "user", "CHAR", false),
        host("Host", "host", "CHAR", false),
        selectPriv("Select_priv", "selectPriv", "CHAR", false),
        insertPriv("Insert_priv", "insertPriv", "CHAR", false),
        updatePriv("Update_priv", "updatePriv", "CHAR", false),
        deletePriv("Delete_priv", "deletePriv", "CHAR", false),
        createPriv("Create_priv", "createPriv", "CHAR", false),
        dropPriv("Drop_priv", "dropPriv", "CHAR", false),
        reloadPriv("Reload_priv", "reloadPriv", "CHAR", false),
        shutdownPriv("Shutdown_priv", "shutdownPriv", "CHAR", false),
        processPriv("Process_priv", "processPriv", "CHAR", false),
        filePriv("File_priv", "filePriv", "CHAR", false),
        grantPriv("Grant_priv", "grantPriv", "CHAR", false),
        referencesPriv("References_priv", "referencesPriv", "CHAR", false),
        indexPriv("Index_priv", "indexPriv", "CHAR", false),
        alterPriv("Alter_priv", "alterPriv", "CHAR", false),
        showDbPriv("Show_db_priv", "showDbPriv", "CHAR", false),
        superPriv("Super_priv", "superPriv", "CHAR", false),
        createTmpTablePriv("Create_tmp_table_priv", "createTmpTablePriv", "CHAR", false),
        lockTablesPriv("Lock_tables_priv", "lockTablesPriv", "CHAR", false),
        executePriv("Execute_priv", "executePriv", "CHAR", false),
        replSlavePriv("Repl_slave_priv", "replSlavePriv", "CHAR", false),
        replClientPriv("Repl_client_priv", "replClientPriv", "CHAR", false),
        createViewPriv("Create_view_priv", "createViewPriv", "CHAR", false),
        showViewPriv("Show_view_priv", "showViewPriv", "CHAR", false),
        createRoutinePriv("Create_routine_priv", "createRoutinePriv", "CHAR", false),
        alterRoutinePriv("Alter_routine_priv", "alterRoutinePriv", "CHAR", false),
        createUserPriv("Create_user_priv", "createUserPriv", "CHAR", false),
        eventPriv("Event_priv", "eventPriv", "CHAR", false),
        triggerPriv("Trigger_priv", "triggerPriv", "CHAR", false),
        createTablespacePriv("Create_tablespace_priv", "createTablespacePriv", "CHAR", false),
        sslType("ssl_type", "sslType", "CHAR", false),
        maxQuestions("max_questions", "maxQuestions", "INTEGER", false),
        maxUpdates("max_updates", "maxUpdates", "INTEGER", false),
        maxConnections("max_connections", "maxConnections", "INTEGER", false),
        maxUserConnections("max_user_connections", "maxUserConnections", "INTEGER", false),
        plugin("plugin", "plugin", "CHAR", false),
        passwordExpired("password_expired", "passwordExpired", "CHAR", false),
        passwordLastChanged("password_last_changed", "passwordLastChanged", "TIMESTAMP", false),
        passwordLifetime("password_lifetime", "passwordLifetime", "SMALLINT", false),
        accountLocked("account_locked", "accountLocked", "CHAR", false),
        createRolePriv("Create_role_priv", "createRolePriv", "CHAR", false),
        dropRolePriv("Drop_role_priv", "dropRolePriv", "CHAR", false),
        passwordReuseHistory("Password_reuse_history", "passwordReuseHistory", "SMALLINT", false),
        passwordReuseTime("Password_reuse_time", "passwordReuseTime", "SMALLINT", false),
        passwordRequireCurrent("Password_require_current", "passwordRequireCurrent", "CHAR", false),
        sslCipher("ssl_cipher", "sslCipher", "LONGVARBINARY", false),
        x509Issuer("x509_issuer", "x509Issuer", "LONGVARBINARY", false),
        x509Subject("x509_subject", "x509Subject", "LONGVARBINARY", false),
        authenticationString("authentication_string", "authenticationString", "LONGVARCHAR", false),
        userAttributes("User_attributes", "userAttributes", "LONGVARCHAR", false);

        private static final String BEGINNING_DELIMITER = "\"";

        private static final String ENDING_DELIMITER = "\"";

        private final String column;

        private final boolean isColumnNameDelimited;

        private final String javaProperty;

        private final String jdbcType;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        public String getJavaProperty() {
            return this.javaProperty;
        }

        public String getJdbcType() {
            return this.jdbcType;
        }

        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        public static Column[] all() {
            return Column.values();
        }

        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}