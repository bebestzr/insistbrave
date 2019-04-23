package com.insistbrave.api.domain

import groovy.transform.CompileStatic

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/15
 */

@CompileStatic
class ApiConstants {

    /**
     * api类型名称
     */
    class ApiType{
        static final IBE_SHIPMENT_CONFIRM = 'ibe.shipment.confirm'
        static final IBE_SALESORDER_CREATE = "ibe.salesorder.create"
        static final IBE_RETURNORDER_CREATE = 'ibe.returnorder.create'
        static final IBE_PURCHASEORDER_CREATE = 'ibe.purchaseorder.create'
        static final IBE_ONSALE_SKU_SAVE = 'ibe.onsale.sku.save'
    }

    /**
     * 内容格式
     */
    class ContentFormat{
        static final String XML = "XML"
        static final String JSON = "JSON"
    }

    /**
     * 接口模式
     */
    class ActionMode{
        static final String NEW ="NEW"
        static final String SAVE = "SAVE"
    }

}
