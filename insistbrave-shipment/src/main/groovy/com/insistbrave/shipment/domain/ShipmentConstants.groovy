package com.insistbrave.shipment.domain
/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/16
 */
class ShipmentConstants {

    class ContainerStatus{
        static final int CREATED = 100 //初始状态
        static final int DISPATCHED = 900 //成功
        static final int ERROR = 999 //失败
    }

    //业务发生渠道
    class BizChannel{
        static final String NA = "NA"
        static final String B2B = "B2B"
        static final String B2C = "B2C"
    }

    /**
     * 出库记录上传标志
     */
    class ContainerUploadFlag{
        static final int CREATED = 100 //初始状态
        static final int DISPATCHED = 900 //成功
        static final int ERROR = 999 //失败
    }
}
