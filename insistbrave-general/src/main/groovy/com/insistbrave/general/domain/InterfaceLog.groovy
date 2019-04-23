package com.insistbrave.general.domain

import com.ittx.cbt.domain.BaseEntity
import groovy.transform.CompileStatic

import java.time.LocalDateTime

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/15
 */
@CompileStatic
class InterfaceLog extends BaseEntity{

    static DEFINE = define([
            table:'interface_log',
            idColumn: 'id',
            cacheKey: 'interface_log',
            cache: false,
            autoIncrement: true,
            logHistory: false
    ])

    @Override
    Object getEntityId() {
        return null
    }

    Long id
    String msgFrom
    String msgTo
    String msgType // 类型
    String msgSubType // 子类型
    String msgSubject // 主题, 单号
    String msgReceive
    String msgReturn
    Integer status
    String error
    LocalDateTime created
    String	createdBy
    LocalDateTime lastUpdated
    String	lastUpdatedBy
    Integer	version	//
    String	userDef1	//	自定义字段1
    String	userDef2	//	自定义字段2
    String	userDef3	//	自定义字段3
    String	userDef4	//	自定义字段4
    String	userDef5	//	自定义字段5
    String	userDef6	//	自定义字段6
    String	userDef7	//	自定义字段7
    String	userDef8	//	自定义字段8


}
