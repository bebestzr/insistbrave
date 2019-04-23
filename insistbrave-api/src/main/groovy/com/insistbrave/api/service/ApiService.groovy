package com.insistbrave.api.service

import com.insistbrave.api.domain.ApiMessages
import com.insistbrave.api.interf.IApiHandler
import com.insistbrave.general.service.InterfaceLogService
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.ResponseMessageFactory
import com.ittx.cbt.general.TtxSession
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/15
 */
@Service
@CompileStatic
class ApiService {

    @Autowired
    ApiHandlerFactory factory

    /**
     * 处理api请求
     * @param session
     * @param api
     * @param data
     * @param format
     * @param logId
     * @return
     */
    ResponseMessage process(TtxSession session, String api, String data, String format, long logId) {
        IApiHandler apiHandler
        ResponseMessage rsp

        apiHandler = factory.getService(api)
        
        if(apiHandler == null){
            return ResponseMessageFactory.error(session,ApiMessages.MSG_OAPI_0001,api)
        }
        rsp = apiHandler.process(session,data,format)
        
        if(rsp.hasError()){
            if(logId>0){
                InterfaceLogService.logError(session,logId,rsp.msg)
            }
        }
        return rsp
    }
}
