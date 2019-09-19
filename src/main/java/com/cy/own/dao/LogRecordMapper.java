package com.cy.own.dao;

import com.cy.own.entity.LogRecord;

public interface LogRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(LogRecord record);

    int insertSelective(LogRecord record);

    LogRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LogRecord record);

    int updateByPrimaryKey(LogRecord record);
}