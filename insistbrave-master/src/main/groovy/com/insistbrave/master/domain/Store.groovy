package com.insistbrave.master.domain

import com.ittx.cbt.domain.BaseEntity

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/28
 */
class Store extends BaseEntity{

    static DEFINE = define([
            table     : 'store',
            idColumn  : 'code',
            cache     : true,
            cacheKey  : 'store',
            logHistory: false
    ])

    @Override
    Object getEntityId() {
        return code
    }

    String code    //店铺编码
    String name    //店铺名称
    String clientCode
    String companyCode      //所属商家
    String storeUrl         //网店地址
    String channelCode      //渠道

    String platformCode = MasterConstants.OnlinePlatform.TM //销售平台
    String storePlatformId  //  平台店铺id
    String ownerAccount     //	店主账号
    String ownerName        //	店主名称
    String ownerNickName    //	店主昵称

    Integer status    //状态


    LocalDate openDate    //	开店日期
    LocalDate closeDate    //	关店日期
    String country    //	国家
    String state    //	省
    String city    //	市
    String district    //	区
    String address    //	地址
    String phone    //	电话
    String mobile    //	手机
    String zip    //	邮编
    String email


    LocalDateTime created    //
    String createdBy    //
    LocalDateTime lastUpdated    //
    String lastUpdatedBy    //
    Integer version    //
    String processStamp     //处理标记
    String userDef1    //自定义字段1
    String userDef2    //自定义字段2
    String userDef3    //自定义字段3
    String userDef4    //自定义字段4
    String userDef5    //自定义字段5
    String userDef6    //自定义字段6
    String userDef7    //自定义字段7
    String userDef8    //自定义字段8
}
