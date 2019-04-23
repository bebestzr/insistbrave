package com.insistbrave.api.service

import com.insistbrave.api.domain.ApiMessages
import com.insistbrave.api.interf.IApiHandler
import com.insistbrave.receipt.domain.ReceiptConstans
import com.insistbrave.receipt.service.ReceiptHeaderService
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.ResponseMessageFactory
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.general.service.ExceptionManager
import com.ittx.cbt.service.BaseService
import com.ittx.cbt.util.ObjectMapperFactory
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/12/4
 */
@Service
@CompileStatic
class ReceiptCloseApiHandlerService extends BaseService implements IApiHandler{
    @Autowired
    ReceiptHeaderService rhSvc

    @Override
    ResponseMessage process(TtxSession session, String data, String format) {
        Map params
        String receiptCode
        List<Map<String,Object>> rhs
        Long receiptId
        Integer status
        try {
            params = ObjectMapperFactory.JSON.readValue(data,Map.class)
            if(params.containsKey("receiptCode")){
                receiptCode = params.get("receiptCode") as String
            }else{
                return ResponseMessageFactory.error(ApiMessages.MSG_GNRL_0009,"Receipt Code")
            }

            rhs = template(session).queryForList("select id,status from receipt_header where code = ?",receiptCode)
            if(rhs == null || rhs.size()<1){
                return ResponseMessageFactory.error(ApiMessages.MSG_GNRL_0010,"入库单",receiptCode)
            }
            receiptId = rhs[0].get("id") as Long
            status = rhs[0].get("status") as Integer

            if(status == ReceiptConstans.ReceiptStatus.CLOSED){
                return ResponseMessageFactory.success(session)
            }

            return  rhSvc.close(session,receiptId)
        } catch (Exception e) {
            ExceptionManager.logException(session,e)
            return ResponseMessageFactory.error(session,ApiMessages.MSG_OAPI_0000)
        }
    }
}
