package com.insistbrave.receipt.service

import com.insistbrave.general.domain.GeneralConstants
import com.insistbrave.receipt.domain.ReceiptConstans
import com.insistbrave.receipt.domain.ReceiptHeader
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.ResponseMessageFactory
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.service.BaseService
import groovy.transform.CompileStatic
import org.apache.commons.lang.StringUtils
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
class ReceiptHeaderService extends BaseService<ReceiptHeader>{



    ResponseMessage close(TtxSession session, long rId) {
        update(session).withTableName("receipt_header").execute((Map)["closed":GeneralConstants.YesOrNo.YES],(Map)["id":rId])
        this.afterClose(session,rId)
        return ResponseMessageFactory.success()
    }

    void afterClose(TtxSession session, long receiptId) {
        List<Map<String,Object>> rhs
        Long orderId
        String internalInstrutionType
        rhs = template(session).queryForList("select refOrderId,internalInstructionType from receipt_header where id = ?",receiptId)
        if(!rhs){
            return
        }

        orderId = (Long)rhs[0].get("refOrderId")
        internalInstrutionType = (String)rhs[0].get("internalInstructionType")
        if(StringUtils.isNotEmpty(internalInstrutionType)){
            switch (internalInstrutionType){
                case ReceiptConstans.InternalInstructionType.PURCHASE:

                    break
                case ReceiptConstans.InternalInstructionType.PURCHASE:

                    break
                case ReceiptConstans.InternalInstructionType.PURCHASE:

                    break
                case ReceiptConstans.InternalInstructionType.PURCHASE:

                    break
                default:
                    break

            }
        }
    }
}
