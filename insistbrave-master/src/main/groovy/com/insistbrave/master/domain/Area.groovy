package com.insistbrave.master.domain

import com.ittx.cbt.domain.BaseEntity

import java.time.LocalDateTime

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/28
 */
class Area extends BaseEntity{

    static DEFINE = define([
            table     : 'area',
            idColumn  : 'code',
            cache     : false,
            cacheKey  : 'area',
            logHistory: false
    ])

    @Override
    Object getEntityId() {
        return code
    }

    String code
    String name
    String country
    String state
    String city
    String district
    String street
    String fullName
    String level = ConfigConstants.AreaLevel.COUNTRY
    String parent

    Integer status
    LocalDateTime created
    String createdBy
    LocalDateTime lastUpdated
    String lastUpdatedBy
    Integer version
    String processStamp     //处理标记
    String userDef1
    String userDef2
    String userDef3
    String userDef4
    String userDef5
    String userDef6
    String userDef7
    String userDef8
}
