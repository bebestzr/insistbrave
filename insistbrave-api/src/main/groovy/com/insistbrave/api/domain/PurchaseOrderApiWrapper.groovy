package com.insistbrave.api.domain

import groovy.transform.CompileStatic

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/29
 */
@CompileStatic
class PurchaseOrderApiWrapper {
    PurchaseOrderHeaderApiWrapper header
    List<PurchaseOrderDetailApiWrapper> details
    String actionMode = ApiConstants.ActionMode.SAVE

}
