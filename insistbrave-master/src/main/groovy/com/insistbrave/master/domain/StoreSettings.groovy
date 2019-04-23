package com.insistbrave.master.domain

import com.insistbrave.general.domain.GeneralConstants
import com.ittx.cbt.domain.BaseEntity
import groovy.transform.CompileStatic

import java.time.LocalDateTime

/**
 * 店铺参数
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/28
 */
@CompileStatic
class StoreSettings extends BaseEntity{

    static DEFINE = define([
            table     : 'store_settings',
            idColumn  : 'id',
            cache     : true,
            cacheKey  : 'store_settings',
            autoIncrement: true,
            logHistory: false
    ])

    @Override
    Object getEntityId() {
        return id
    }

    Long id
    String storeCode
    String companyCode
    String clientCode
    String auditMasterCode  //默认审单主表编码
    Integer autoAudit = GeneralConstants.YesOrNo.NO //自动审单
    Integer autoWave = GeneralConstants.YesOrNo.YES //自动加入波次；如NO，则等待用户根据"审单主表"创建审单波次

    String skuMappingType = MasterConstants.SkuMappingType.SPU_AND_SKU //铺货类型
    Integer stockRatio = 100 //可售库存比例，使用时除100
    String bizGroupCode     //业务分组，可售比例

    Integer autoGenStoreSku = GeneralConstants.YesOrNo.NO   //在sku建立时，是否自动生成店铺sku；

    Integer autoConsolidate = GeneralConstants.YesOrNo.NO //自动合单

    String name
    Integer status

    // 新增字段 用于合、拆单
    String defaultMergeField //默认合单字段
    String referenceMergeField //参考合单字段
    Integer isMerge = 1 //是否合单
    Integer isLimit = 0 //是否限定
    Integer maxVolume = 0 // 最大体积
    Integer maxWeight = 0 // 最大重量
    Integer maxLine = 0 //最大行数
    Integer maxAmount = 0 // 最大数量

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
