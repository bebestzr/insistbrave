package com.insistbrave.master.domain

import com.insistbrave.general.domain.GeneralConstants
import com.ittx.cbt.domain.BaseEntity
import groovy.transform.CompileStatic

import java.time.LocalDateTime

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/28
 */
@CompileStatic
class StoreOnSaleSku extends BaseEntity{
    static DEFINE = define([
            table     : 'store_on_sale_sku',
            idColumn  : 'id',
            cache     : false,
            cacheKey  : 'store_on_sale_sku',
            autoIncrement: true,
            logHistory: true
    ])

    @Override
    Object getEntityId() {
        return id
    }

    Long id //ID
    String clientCode   //
    String companyCode  //货主编码
    String skuCode  //sku编码
    String skuName  //sku名称
    String storeCode    //店铺编码
    Integer bomType = MasterConstants.BomType.NOT_BOM   //bom的库存计算方式，按明细计算出成品数量

    Integer syncStock       //自动同步库存
    Integer autoOnline      //自动上架
    Integer autoOffline     //自动下架

    Integer safetyStock     //安全库存，低于此库存量自动下架，高于此库存量自动上架

    String platformSpuCode  //商品货号
    String platformSkuCode  //SKU货号
    String platformSkuId    //平台SKU编号
    String platformSpuId    //平台商品编号


    String itemTitle        //商品标题
    Integer onSaleQty       //前台售卖量
    Integer listPrice       //标价
    Integer listed = GeneralConstants.YesOrNo.YES //在售状态
    String picUrl   //主图片地址

    Integer virtualQty          //虚拟库存量，有虚拟库存的永远按照虚拟库存数量同步；
    Integer stockShareRatio     //同步库存时的系数，除100，实际同步的数量 = OMS店铺库存量 * 系数
    Integer availableQty        //OMS店铺库存量 = 统计店铺所属bizGroup的所有库存地点的库存总计

    //TODO: [VP]转移到单独的接口异常表中
    String syncListingResult    //SKU同步结果
    String syncStockResult  //库存同步结果

    Integer status  //状态
    LocalDateTime created
    String createdBy
    LocalDateTime lastUpdated
    String lastUpdatedBy
    Integer version
    String processStamp //处理标记
    String userDef1
    String userDef2
    String userDef3
    String userDef4
    String userDef5
    String userDef6
    String userDef7
    String userDef8
}
