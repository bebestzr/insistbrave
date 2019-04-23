package com.insistbrave.order.service

import com.insistbrave.general.domain.GeneralConstants
import com.insistbrave.order.domain.OrderMessages
import com.insistbrave.order.domain.PurchaseOrderDetail
import com.insistbrave.order.domain.PurchaseOrderHeader
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.ResponseMessageFactory
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.general.service.ExceptionManager
import com.ittx.cbt.service.BaseService
import groovy.transform.CompileStatic
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
class PurchaseOrderService extends BaseService{

    @Autowired
    PurchaseOrderHeaderService pohSvc
    @Autowired
    PurchaseOrderDetailService podSvc

    ResponseMessage save(TtxSession session, PurchaseOrderHeader poh, List<PurchaseOrderDetail> pods) {
        if(pohSvc.exists(session,(Map)["clientCode":poh.clientCode,"companyCode":poh.companyCode,"sourceOrderCode":poh.sourceOrderCode])){
            return ResponseMessageFactory.success(session,poh.id)
        }else{
            return this.create(session,poh,pods)
        }
    }

    ResponseMessage create(TtxSession session, PurchaseOrderHeader poh, List<PurchaseOrderDetail> pods) {
        ResponseMessage rsp
        try {
            rsp = pohSvc.createEntity(session,poh)
            if(rsp.hasError()){
                return rsp
            }

            for (PurchaseOrderDetail pod:pods) {
                pod.purchaseOrderId = poh.id
                pod.purchaseOrderCode = poh.code
                rsp = podSvc.createEntity(session,pod)
                if(rsp.hasError()){
                    this.rollbackCreation(session,poh.id)
                    return rsp
                }
            }
            return ResponseMessageFactory.success(session,poh.id)
        } catch (Exception e) {
            ExceptionManager.logException(session,e)
            return ResponseMessageFactory.error(session,OrderMessages.MSG_PURC_0001,e.message)

        } finally {
            session.params.put(GeneralConstants.SESSION_PARAM_BATCH_MODE,false)
        }
    }

    ResponseMessage rollbackCreation(TtxSession session, long pohId) {
        template(session).update("delete from purchase_order_detail where purchaseOrderId = ?",pohId)
        template(session).update("delete from purchase_order_header where id = ?",pohId)
        return ResponseMessageFactory.success()
    }
}
