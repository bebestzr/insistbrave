package com.insistbrave.shipment.domain

import com.insistbrave.master.domain.MasterConstants
import com.ittx.cbt.domain.BaseEntity
import groovy.transform.CompileStatic

import java.time.LocalDateTime

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/16
 */
@CompileStatic
class ShippingContainerHeader extends BaseEntity{

    static DEFINE = define([
            table        : 'shipping_container_header',
            idColumn     : 'id',
            cache        : false,
            cacheKey     : 'shipping_container_header',
            autoIncrement: true,
            logHistory   : false
    ])

    @Override
    Object getEntityId() {
        return id
    }

    Long id
    String code
    Long shipmentId
    String shipmentCode
    String clientCode
    String facilityCode
    String companyCode
    String containerType
    Integer status = ShipmentConstants.ContainerStatus.CREATED

    String waybillCode
    String carrierCode
    String carrierServiceCode

    Integer totalQty = 0
    Integer totalWeight = 0
    Integer totalVolume = 0
    Integer totalVolumeWeight = 0
    String weightUM = MasterConstants.WeightUM.G
    String volumeUM = MasterConstants.VolumeUM.CM3

    Integer containerIndex      //箱序号
    Integer containerCount      //总箱数

    String internalInstructionType  //系统内部类型 - 出入库(出库)
    String bizChannel = ShipmentConstants.BizChannel.NA  //业务发生渠道

    LocalDateTime shipAt        //实际发货时间

    Integer uploadFlag = ShipmentConstants.ContainerUploadFlag.CREATED //上传标识
    LocalDateTime uploadAt  //上传时间

    String errorCode
    String errorDesc

    String refOrderCode     //关联订单编码

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
