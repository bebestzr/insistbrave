package com.insistbrave.api.service

import com.insistbrave.api.domain.ApiMessages
import com.insistbrave.api.interf.IApiHandler
import com.insistbrave.general.utils.ObjectMapperUtils
import com.insistbrave.master.domain.StoreOnSaleSku
import com.insistbrave.master.domain.StoreSettings
import com.insistbrave.master.service.StoreOnSaleSkuService
import com.insistbrave.master.service.StoreService
import com.insistbrave.master.service.StoreSettingsService
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.ResponseMessageFactory
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.service.BaseService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 在售商品保存
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/28
 */
@Service
@CompileStatic
class OnsaleSkuSaveApiHandlerService extends BaseService implements IApiHandler{

    @Autowired
    StoreService storeService

    @Autowired
    StoreOnSaleSkuService storeOnSaleSkuService

    @Autowired
    StoreSettingsService settingService

    @Override
    ResponseMessage process(TtxSession session, String data, String format) {
        ResponseMessage rsp
        StoreOnSaleSku storeOnSaleSku = ObjectMapperUtils.getObjectMapper(format).readValue(data,StoreOnSaleSku.class)
        if(storeOnSaleSku == null){
            return ResponseMessageFactory.error(session,ApiMessages.MSG_OAPI_0002)
        }
        StoreSettings storeSettings = settingService.getEntity(session,[storeCode: storeOnSaleSku.storeCode])
        rsp = storeOnSaleSkuService.saveOnsaleSkuFromTaobao(session,storeOnSaleSku,storeSettings.skuMappingType)
        if(rsp.hasError()){
            return rsp
        }
        return ResponseMessageFactory.success(session,"OnsaleSkuSaveApiHandlerService received",data)
    }
}
