package com.cy.own.configuration.security;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 重写记住我持久化方法
 */
public class MyJdbcTokenRepositoryImpl extends JdbcTokenRepositoryImpl {
    public static final String CREATE_TABLE_SQL = "create table persistent_logins (user_name varchar(64) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null)";
    public static final String DEF_TOKEN_BY_SERIES_SQL = "select user_name,series,token,last_used from persistent_logins where series = ?";
    public static final String DEF_INSERT_TOKEN_SQL = "insert into persistent_logins (user_name, series, token, last_used) values(?,?,?,?)";
    public static final String DEF_UPDATE_TOKEN_SQL = "update persistent_logins set token = ?, last_used = ? where series = ?";
    public static final String DEF_REMOVE_USER_TOKENS_SQL = "delete from persistent_logins where user_name = ?";
    private String tokensBySeriesSql = "select user_name,series,token,last_used from persistent_logins where series = ?";
    private String insertTokenSql = "insert into persistent_logins (user_name, series, token, last_used) values(?,?,?,?)";
    private String updateTokenSql = "update persistent_logins set token = ?, last_used = ? where series = ?";
    private String removeUserTokensSql = "delete from persistent_logins where user_name = ?";
    //是否自动创建表
    private boolean createTableOnStartup;

    public MyJdbcTokenRepositoryImpl() {
    }

    @Override
    protected void initDao() {
        if (this.createTableOnStartup) {
            this.getJdbcTemplate().execute("create table persistent_logins (user_name varchar(64) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null)");
        }

    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        this.getJdbcTemplate().update(this.insertTokenSql, new Object[]{token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate()});
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        this.getJdbcTemplate().update(this.updateTokenSql, new Object[]{tokenValue, lastUsed, series});
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            return (PersistentRememberMeToken)this.getJdbcTemplate().queryForObject(this.tokensBySeriesSql, new RowMapper<PersistentRememberMeToken>() {
                public PersistentRememberMeToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new PersistentRememberMeToken(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4));
                }
            }, new Object[]{seriesId});
        } catch (EmptyResultDataAccessException var3) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Querying token for series '" + seriesId + "' returned no results.", var3);
            }
        } catch (IncorrectResultSizeDataAccessException var4) {
            this.logger.error("Querying token for series '" + seriesId + "' returned more than one value. Series should be unique");
        } catch (DataAccessException var5) {
            this.logger.error("Failed to load token for series " + seriesId, var5);
        }

        return null;
    }

    @Override
    public void removeUserTokens(String user_name) {
        this.getJdbcTemplate().update(this.removeUserTokensSql, new Object[]{user_name});
    }

    @Override
    public void setCreateTableOnStartup(boolean createTableOnStartup) {
        this.createTableOnStartup = createTableOnStartup;
    }
}
