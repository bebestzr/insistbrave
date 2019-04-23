package com.insistbrave.api.controller

import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.ResponseMessageFactory
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.general.service.ExceptionManager
import com.ittx.cbt.util.ObjectMapperFactory
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/15
 */
class blank1 {
    /**
     * 关闭入库单
     * 必须在调用前确定入库单可以关闭，方法内不再进行校验；仅处理关闭逻辑
     * @param sess
     * @param recieptId
     * @return
     */
    ResponseMessage close(TtxSession sess, Long recieptId){
        //更新入库单状态为关闭
        update(sess).withTableName("receipt_header")
                .execute((Map)["closed":GeneralConstants.YesOrNo.YES], (Map)["id": recieptId])

        //TODO: 清除未收货的在途库存

        this.afterClose(sess, recieptId)

        return ResponseMessageFactory.success()
    }

    /**
     * 入库单关闭的后续处理
     * @param sess
     * @param id
     */
    void afterClose(TtxSession sess, Long id){
        List<Map<String, Object>> rhs
        Long orderId
        String internalInstructionType

        rhs = template(sess).queryForList("select refOrderId, internalInstructionType from receipt_header where id = ?", id)
        if (!rhs)
            return

        //不同单据类型的特殊处理
        orderId = (Long) rhs[0].get("refOrderId")
        internalInstructionType = (String) rhs[0].get("internalInstructionType")
        if (StringUtils.isNotEmpty(internalInstructionType)){
            switch (internalInstructionType){
                case ReceiptConstants.InternalInstructionType.PURCHASE:
                    poSvc.tryClose(sess, orderId)
                    break
                case ReceiptConstants.InternalInstructionType.RETURN:
                    roSvc.afterReceiptClose(sess, orderId)
                    break
                case ReceiptConstants.InternalInstructionType.TRANSFER:
                    tohSvc.tryClose(sess, orderId)
                    break
                case ReceiptConstants.InternalInstructionType.WORK_ORDER:
                    woSvc.tryClose(sess, orderId)
                    break
                default:
                    break
            }
        }
    }

}
