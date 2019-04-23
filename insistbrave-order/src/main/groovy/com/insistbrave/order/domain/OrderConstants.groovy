package com.insistbrave.order.domain
/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/29
 */
class OrderConstants {


    class InternalOrderType{
        static final String SALES           = "SALES"           //销售
        static final String EXCHANGE        = "EXCHANGE"        //换货
        static final String REISSUE         = "REISSUE"         //补发
        static final String RETURN          = "RETURN"          //销退
        static final String PURCHASE        = "PURCHASE"        //采购
        static final String PURCHASE_RETURN = "PURCHASE_RETURN" //采退
        static final String TRANSFER        = "TRANSFER"        //调拨
        static final String WORK_ORDER      = "WORK_ORDER"      //加工
        static final String OTHER           = "OTHER"           //其他
    }

    class PurchaseStatus{
        static final Integer IN_POOL = 100   //订单池
        static final Integer RECV_PENDING = 300   //审核中
        static final Integer RECV_IN_PROC = 700   //收货中
        static final Integer CLOSED = 900   //入库完成
    }

    class BizChannel{
        static final String B2C = "B2C"
        static final String B2B = "B2B"
        static final String NA  = "NA"
    }
}
