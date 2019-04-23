package com.insistbrave.shipment.domain

import com.ittx.cbt.domain.BaseEntity
import groovy.transform.CompileStatic
import org.apache.xalan.xsltc.cmdline.Compile

import java.time.LocalDateTime

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/16
 */
@CompileStatic
class ShippingContainerDetail extends BaseEntity{

    static DEFINE = define([
            table        : 'shipping_container_detail',
            idColumn     : 'id',
            cache        : false,
            cacheKey     : 'shipping_container_detail',
            autoIncrement: true,
            logHistory   : false
    ])

    @Override
    Object getEntityId() {
        return id
    }


    Long id
    Long containerId = 0
    String containerCode
    Long shipmentId = 0
    Long shipmentDetailId = 0
    String shipmentCode
    String facilityCode
    String clientCode

    Long refOrderId = 0
    String refOrderCode
    Long refOrderDetailId = 0

    String skuCode
    String skuName
    String companyCode
    String inventorySts

    Integer shipQty = 0
    String quantityUM = MasterConstants.QuantityUM.EACH
    Long inventoryId = 0

    Integer	totalWeight = 0
    Integer	totalVolume = 0
    Integer totalVolumeWeight = 0
    String 	weightUM = MasterConstants.WeightUM.G
    String 	volumeUM = MasterConstants.VolumeUM.CM3


    LocalDateTime created
    String createdBy
    LocalDateTime lastUpdated
    String lastUpdatedBy
    Integer version = 0
    String processStamp         //处理标记
    String userDef1
    String userDef2
    String userDef3
    String userDef4
    String userDef5
    String userDef6
    String userDef7
    String userDef8
}
