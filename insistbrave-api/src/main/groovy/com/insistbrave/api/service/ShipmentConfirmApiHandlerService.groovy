package com.insistbrave.api.service

import com.insistbrave.api.domain.ApiMessages
import com.insistbrave.api.domain.ShipmentApiWrapper
import com.insistbrave.api.domain.ShippingContainerApiWrapper

import com.insistbrave.api.interf.IApiHandler
import com.insistbrave.general.utils.ObjectMapperUtils
import com.insistbrave.shipment.domain.ShippingContainerDetail
import com.insistbrave.shipment.domain.ShippingContainerHeader
import com.insistbrave.shipment.service.ShipmentHeaderService
import com.insistbrave.shipment.service.ShippingContainerService
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.ResponseMessageFactory
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.general.service.ExceptionManager
import org.springframework.beans.factory.annotation.Autowired

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/15
 */
class ShipmentConfirmApiHandlerService implements IApiHandler{

    @Autowired
    ShippingContainerService scSvc

    @Autowired
    ShipmentHeaderService shSvc


    @Override
    ResponseMessage process(TtxSession session, String data, String format) {
        ShipmentApiWrapper sw
        ResponseMessage rsp
        boolean hasError = false
        StringBuilder sbError = new StringBuilder()
        try{
            sw = ObjectMapperUtils.getObjectMapper(format).readValue(data,ShipmentApiWrapper.class)
            for(ShippingContainerApiWrapper scw: sw.containers){
                ShippingContainerHeader sch = scw.header.shippingContainerHeader
                List<ShippingContainerDetail> scds = getRawContainerDetails(scw.details)
                rsp = scSvc.create(session,sch,scds)
                //如保存出错，继续下一个出库箱
                if(rsp.hasError()){
                    hasError = true
                    sbError.append(rsp.msg)
                    continue
                }
                rsp = scSvc.dispatch(session,sch,scds,true)

                //如保存出错，继续下一个出库箱
                if(rsp.hasError()){
                    hasError = true
                    sbError.append(rsp.msg)
                }
            }
            rsp = shSvc.updateStatistics(session, sw.header.shipmentHeader.id)
            if(rsp.hasError()){
                //
            }
            if(!hasError){
                //尝试发运出库单
                return shSvc.tryDispatch(session,sw.header.shipmentHeader.id)
            }else {
                return ResponseMessageFactory.error(session,ApiMessages.MSG_OAPI_0000)
            }
        }catch (Exception e){
            ExceptionManager.logException(session,e)
            return ResponseMessageFactory.error(session,ApiMessages.MSG_OAPI_0000)
        }
    }
}
