package com.insistbrave.general.service

import com.insistbrave.general.domain.InterfaceLog
import com.insistbrave.general.domain.InterfaceLogConstants
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.service.BaseService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/15
 */
@Service
@CompileStatic
class InterfaceLogService extends BaseService<InterfaceLog>{


    private static InterfaceLogService ils


    @PostConstruct
    void init(){
        ils = this
    }

    static Long log(TtxSession session,InterfaceLog log){
        ResponseMessage rsp = ils.createEntity(session,log)
        if(rsp.hasError()){
            return null
        }else {
            return (rsp.data as InterfaceLog).id
        }
    }

    static void logSuccess(TtxSession session,Long id,String msgReturn,String error){
        InterfaceLog log = ils.getEntity(session,id)
        log.msgReturn = msgReturn
        log.error = error
        log.status = InterfaceLogConstants.Status.SUCCESS
        ils.updateEntity(session,log)
    }

    static void logError(TtxSession session,Long id, String msgReturn,String error){
        InterfaceLog log = ils.getEntity(session,id)
        log.msgReturn = msgReturn
        log.error = error
        log.status = InterfaceLogConstants.Status.FAILURE
        ils.updateEntity(session,log)
    }

    static void logError(TtxSession session,Long id, String msgReturn){
        InterfaceLog log = ils.getEntity(session,id)
        log.msgReturn = msgReturn
        log.status = InterfaceLogConstants.Status.FAILURE
        ils.updateEntity(session,log)
    }
}
