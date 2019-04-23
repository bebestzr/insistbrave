package com.insistbrave.master.domain

import com.ittx.cbt.domain.BaseEntity
import groovy.transform.CompileStatic

import java.time.LocalDateTime

/**
 * 店铺sku
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/28
 */
@CompileStatic
class StoreSku extends BaseEntity{

    static DEFINE = define([
            table     : 'store_sku',
            idColumn  : 'id',
            cache     : true,
            cacheKey  : 'store_sku',
            autoIncrement: true,
            logHistory: false
    ])

    @Override
    Object getEntityId() {
        return id
    }

    @Override
    String getCacheId(){
        return "$skuCode|$storeCode|$companyCode|$clientCode"
    }

    Long id
    String skuCode
    String skuName
    String storeCode
    String companyCode
    String clientCode
    Integer status

    String platformSpuCode  //商品货号
    String platformSkuCode   //SKU货号

    LocalDateTime created //
    String createdBy //
    LocalDateTime lastUpdated //
    String lastUpdatedBy //
    Integer version //
    String processStamp //处理标记
    String userDef1 // 自定义字段1
    String userDef2 // 自定义字段2
    String userDef3 // 自定义字段3
    String userDef4 // 自定义字段4
    String userDef5 // 自定义字段5
    String userDef6 // 自定义字段6
    String userDef7 // 自定义字段7
    String userDef8 // 自定义字段8
}
