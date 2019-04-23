package com.insistbrave.master.service

import com.insistbrave.master.domain.MasterConstants
import com.insistbrave.master.domain.StoreOnSaleSku
import com.insistbrave.master.domain.StoreSku
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.ResponseMessageFactory
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.service.BaseService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/28
 */
@Service
@CompileStatic
class StoreOnSaleSkuService extends BaseService<StoreOnSaleSku>{

    @Autowired
    StoreSkuService storeSkuService

    ResponseMessage saveOnsaleSkuFromTaobao(TtxSession session,StoreOnSaleSku onSaleSku,String skuMappingType = null){
        ResponseMessage rsp = ResponseMessageFactory.success(session)
        StoreOnSaleSku localOnSaleSku = null
        if (onSaleSku.platformSpuId && onSaleSku.platformSkuId) {
            localOnSaleSku = findFirstEntity(session, ['platformSkuId': onSaleSku.platformSkuId, 'platformSpuId': onSaleSku.platformSpuId, 'storeCode': onSaleSku.storeCode, 'companyCode': onSaleSku.companyCode])
        } else if (onSaleSku.platformSkuId) {
            localOnSaleSku = findFirstEntity(session, ['platformSkuId': onSaleSku.platformSkuId, 'storeCode': onSaleSku.storeCode, 'companyCode': onSaleSku.companyCode])
        } else {
            localOnSaleSku = findFirstEntity(session, ['platformSpuId': onSaleSku.platformSpuId, 'storeCode': onSaleSku.storeCode, 'companyCode': onSaleSku.companyCode])
        }
        //铺货
        mappingOnSaleSku(session,skuMappingType,onSaleSku)
        if(localOnSaleSku == null){
            onSaleSku.storeCode = onSaleSku.storeCode
            onSaleSku.companyCode = onSaleSku.companyCode
            onSaleSku.status = 1
            onSaleSku.stockShareRatio = 1
            rsp = createEntity(session, onSaleSku)
        }else {
            localOnSaleSku.platformSpuCode = onSaleSku.platformSpuCode
            localOnSaleSku.platformSkuCode = onSaleSku.platformSkuCode
            localOnSaleSku.listed = onSaleSku.listed
            localOnSaleSku.itemTitle = onSaleSku.itemTitle
            localOnSaleSku.onSaleQty = onSaleSku.onSaleQty
            localOnSaleSku.picUrl = onSaleSku.picUrl
            rsp = updateEntity(session,localOnSaleSku)
        }
        return rsp
    }

    StoreOnSaleSku mappingOnSaleSku(TtxSession session,String skuMappingType,StoreOnSaleSku storeOnSaleSku){
        if(MasterConstants.SkuMappingType.SPU_AND_SKU.equalsIgnoreCase(skuMappingType)){
            StoreSku storeSku = storeSkuService.findFirstEntity(session,[
                    "storeCode":storeOnSaleSku.storeCode,
                    "platformSpuCode":storeOnSaleSku.platformSpuCode,
                    "platformSkuCode":storeOnSaleSku.platformSkuCode,
                    "status":1
            ])
            if(storeSku){
                storeOnSaleSku.skuCode = storeSku.skuCode
                storeOnSaleSku.skuName = storeSku.skuName
                storeOnSaleSku.companyCode = storeSku.companyCode
            }
        }else if(MasterConstants.SkuMappingType.SKU.equalsIgnoreCase(skuMappingType)){
            List<Map<String,Object>> storeSkus = template(session).queryForList("selelct skuCode,skuName,companyCode from store_sku where storeCode = ? and status = 1 and (skuCode = ? or skuCode = ?)",storeOnSaleSku.storeCode,storeOnSaleSku.platformSpuCode,storeOnSaleSku.platformSkuCode)
            if(storeSkus.size()>0){
                storeOnSaleSku.skuCode = storeSkus[0].skuCode
                storeOnSaleSku.skuName = storeSkus[0].skuName
                storeOnSaleSku.companyCode = storeSkus[0].companyCode
            }
        }
        storeOnSaleSku
    }
}
