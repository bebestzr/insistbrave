package com.insistbrave.order.domain

import com.insistbrave.master.domain.MasterConstants
import com.ittx.cbt.domain.BaseEntity

import java.time.LocalDateTime

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/29
 */
class PurchaseOrderDetail extends BaseEntity{

    static DEFINE = define([
            table   : 'purchase_order_detail',
            idColumn: 'id',
            cache   : false,
            cacheKey: 'purchase_order_detail',
            autoIncrement: true,
            logHistory: false
    ])

	Long id
    Long purchaseOrderId
    String purchaseOrderCode
    String sourceLineNum
    String companyCode
    String clientCode
    String skuCode
    String skuName
    Integer requestQty = 0
    Integer openQty = 0         //剩余未收货数量，初始为0
    Integer fulfillQty = 0      //实际收货数量，初始为0
    Integer asnPendingQty = 0   //剩余可生成入库单的数量剩余可生成入库单的数量，初始为requestQty
    Integer returnableQty = 0   //剩余可退货的数量，初始为0
    String quantityUM = MasterConstants.QuantityUM.EACH
    Integer buyPrice = 0        //采购价格
    Integer discountPrice = 0   //折扣价
    Integer discount = 0        //折扣: 输入折扣后计算折扣价;输入折扣价反向计算折扣比例
    Integer totalAmount = 0     //总金额=折扣价* 数量
    Integer totalWeight = 0
    Integer totalVolume = 0
    Integer totalVolumeWeight = 0
    Integer fulfillAmount = 0
    Integer fulfillWeight = 0
    Integer fulfillVolume = 0
    String weightUM = MasterConstants.WeightUM.G    //重量单位
    String volumeUM = MasterConstants.VolumeUM.CM3  //体积单位

    /*
    String lot
    String batch
    LocalDate agingDate
    LocalDate expirationDate
    LocalDate mfgDate
    */
    String inventorySts = MasterConstants.InventoryStatus.AVAILABLE

    String processStamp     //处理标记
    LocalDateTime created
    String createdBy
    LocalDateTime lastUpdated
    String lastUpdatedBy
    Integer version
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
