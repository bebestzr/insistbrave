package com.insistbrave.order.domain

import com.insistbrave.master.domain.MasterConstants
import com.ittx.cbt.domain.BaseEntity
import groovy.transform.CompileStatic

import java.time.LocalDateTime

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/29
 */
@CompileStatic
class PurchaseOrderHeader extends BaseEntity{

    static DEFINE = define([
            table   : 'purchase_order_header',
            idColumn: 'id',
            cache   : false,
            cacheKey: 'purchase_order_header',
            autoIncrement: true,
            logHistory: false
    ])

    Long id
    String code
    String orderType
    String facilityCode
    String companyCode
    String clientCode

    String internalOrderType = OrderConstants.InternalOrderType.PURCHASE    //单据类型
    String bizChannel = OrderConstants.BizChannel.NA           //业务发生渠道

    String sourceAsnCode //供应商发货单编码

    Integer status = OrderConstants.PurchaseStatus.IN_POOL

    String vendorCode
    String vendorName
    String shipFromAttentionTo
    String shipFromCountry
    String shipFromState
    String shipFromCity
    String shipFromDistrict
    String shipFromAddress
    String shipFromPostalCode
    String shipFromPhone
    String shipFromMobile
    String shipFromFax
    String shipFromEmail

    Integer totalAmount = 0
    Integer totalLines = 0
    Integer totalQty = 0
    Integer totalWeight = 0
    Integer totalVolume = 0
    Integer totalVolumeWeight = 0
    Integer totalFulfillQty = 0     //累计实收数量
    Integer totalFulfillAmount = 0  //累计实收金额
    Integer totalFulfillWeight = 0
    Integer totalFulfillVolume = 0
    Integer totalFulfillVolumeWeight = 0
    String weightUM = MasterConstants.WeightUM.G    //重量单位
    String volumeUM = MasterConstants.VolumeUM.CM3  //体积单位

    String sourcePlatformCode //来源平台编码
    Long sourceOrderId //上位订单ID
    String sourceOrderCode //上位订单号

    String auditBy
    LocalDateTime auditAt
    String closedBy
    LocalDateTime closedAt

    String errorCode
    String errorDesc
    String holdCode
    String holdUser
    LocalDateTime holdAt

    String processStamp     //处理标记
    LocalDateTime created
    String createdBy
    LocalDateTime lastUpdated
    String lastUpdatedBy
    Integer version = 0
    String userDef1
    String userDef2
    String userDef3
    String userDef4
    String userDef5
    String userDef6
    String userDef7
    String userDef8

    @Override
    Object getEntityId() {
        return id
    }
}
