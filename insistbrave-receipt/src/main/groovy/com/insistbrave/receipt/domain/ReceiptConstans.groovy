package com.insistbrave.receipt.domain
/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/12/4
 */
class ReceiptConstans {
    class InternalInstructionType{

        static final String PURCHASE = "PURCHASE"
        static final String TRANSFER = "TRANSFER"
        static final String WORK_ORDER = "WORK_ORDER"
        static final String RETURN = "RETURN"
        static final String OTHER = "OTHER"
    }
    class ReceiptStatus{
        final static Integer CLOSED = 100 //订单池
    }
}
