package com.insistbrave.api.domain

import groovy.transform.CompileStatic

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/16
 */
@CompileStatic
class ShipmentApiWrapper {

    ShipmentHeaderApiWrapper header
    List<ShipmentDetailApiWrapper> details
    List<ShippingContainerApiWrapper> containers

}
