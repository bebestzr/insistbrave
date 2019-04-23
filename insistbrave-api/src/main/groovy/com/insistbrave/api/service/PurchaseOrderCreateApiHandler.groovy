package com.insistbrave.api.service

import com.insistbrave.api.domain.ApiConstants
import com.insistbrave.api.domain.ApiMessages
import com.insistbrave.api.domain.PurchaseOrderApiWrapper
import com.insistbrave.api.interf.IApiHandler
import com.insistbrave.order.service.PurchaseOrderService
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.ResponseMessageFactory
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.general.service.ExceptionManager
import com.ittx.cbt.service.BaseService
import com.ittx.cbt.util.ObjectMapperFactory
import groovy.transform.CompileStatic
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/29
 */
@Service
@CompileStatic
class PurchaseOrderCreateApiHandler extends BaseService implements IApiHandler{
    @Autowired
    PurchaseOrderService poSvc
    @Override
    ResponseMessage process(TtxSession session, String data, String format) {
        PurchaseOrderApiWrapper pow
        ResponseMessage rsp

        try{
            pow = ObjectMapperFactory.getObjectMapper(format).readValue(data,PurchaseOrderApiWrapper.class)
            if(pow == null){
                return ResponseMessageFactory.error(session,ApiMessages.MSG_OAPI_0002)
            }

            if(StringUtils.isEmpty(pow.actionMode)){
                pow.actionMode = ApiConstants.ActionMode.SAVE
            }
            if(StringUtils.equalsIgnoreCase(pow.actionMode,ApiConstants.ActionMode.SAVE)){
                rsp = poSvc.save(session,pow.header.purchaseOrderHeader,pow.details.collect{it.purchaseOrderDetail})

            }else {
                rsp = poSvc.create(session,pow.header.purchaseOrderHeader,pow.details.collect{it.purchaseOrderDetail})
            }

            if(rsp.hasError()){
                return rsp
            }
            return ResponseMessageFactory.success(session)
        }catch (Exception e){
            ExceptionManager.logException(session,e)
            return ResponseMessageFactory.error(session,ApiMessages.MSG_OAPI_0000)
        }
    }
}
