package com.insistbrave.receipt.domain

import com.ittx.cbt.domain.BaseEntity

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/12/4
 */

class ReceiptHeader extends BaseEntity{

    static DEFINE = define([
            table        : 'receipt_header',
            idColumn     : 'id',
            cache        : false,
            cacheKey     : 'receipt_header',
            autoIncrement: true,
            logHistory   : false
    ])

    //敏感数据
    static secrets = [
            "shipToAttentionTo",
            "shipToAddress",
            "shipToPhone",
            "shipToMobile",
            "shipToFax",
            "shipToEmail"]

    @Override
    Object getEntityId() {
        return id
    }
    Long id
    String clientCode
    String facilityCode     //库存地点
    String companyCode      //货主编码
    String code              //入库单号
    String receiptType      //入库类型

    String internalInstructionType  //系统内部类型 - 出入库(入库)
    String bizChannel = ReceiptConstants.BizChannel.NA  //业务发生渠道

    Long refOrderId         //关联单据ID，如采购单号，调拨单号等
    String refOrderCode     //关联订单号
    String refOrderType = ReceiptConstants.InternalInstructionType.PURCHASE     //字典：采购、销售退货、加工入、调拨入

    String sourceAsnCode //供应商发货单编码（目前仅用于采购）

    Integer closed = GeneralConstants.YesOrNo.NO        //已关闭
    String closedBy         //关闭用户
    Integer status = ReceiptConstants.ReceiptStatus.READY_FOR_WMS      //入库状态
    Integer allowOverReceive = GeneralConstants.YesOrNo.NO          //允许超收

    String shipFromCode         //供应商编码
    String shipFromAddressCode  //供应商地址编码
    String shipFromAttentionTo  //发货人
    String shipFromAddress      //地址
    String shipFromCountry      //国家
    String shipFromState        //省
    String shipFromCity         //城市
    String shipFromDistrict     //区
    String shipFromPostalCode   //邮编
    String shipFromPhone        //电话
    String shipFromMobile       //手机
    String shipFromFax          //传真
    String shipFromEmail        //电子邮箱

    LocalDate scheduledArriveOn     //计划到货日期

    Integer totalLines = 0      //总行数
    Integer totalQty = 0        //总数量
    Integer totalAmount         //入库单总金额
    Integer totalWeight = 0     //总重量
    Integer totalVolume = 0     //总体积
    Integer totalVolumeWeight = 0   //总材积
    Integer totalFulfillQty = 0     //累计实收数量
    Integer totalFulfillAmount = 0  //累计实收金额
    Integer totalFulfillWeight = 0
    Integer totalFulfillVolume = 0
    Integer totalFulfillVolumeWeight = 0
    Integer totalCases = 0      //总整箱数
    Integer totalContainers = 0 //总拼箱数
    Integer closeAtQty = 0      //WMS关单时总收货数量
    String quantityUM = MasterConstants.QuantityUM.EACH

    String weightUM = MasterConstants.WeightUM.G    //重量单位
    String volumeUM = MasterConstants.VolumeUM.CM3  //体积单位
    String holdCode             //锁定原因编码
    String holdUser             //锁定用户
    LocalDateTime checkInFrom   //开始收货时间
    LocalDateTime checkInTo     //结束收货时间
    LocalDateTime closedAt      //关单时间
    String note                 //备注


    String sourcePlatformCode //来源平台编码
    Long sourceOrderId //上位订单ID
    String sourceOrderCode //上位订单号

    LocalDateTime created       //创建时间
    String createdBy            //创建人
    LocalDateTime lastUpdated   //更新时间
    String lastUpdatedBy        //更新人
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
