package com.ittx.ofs.api.controller

import com.ittx.ais.model.AisMessage
import com.ittx.ais.service.AisMessageSender
import com.ittx.cbt.controller.BaseController
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.ResponseMessageFactory
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.general.service.ExceptionManager
import com.ittx.ofs.api.service.ApiService
import com.ittx.ofs.general.domain.InterfaceLog
import com.ittx.ofs.general.service.InterfaceLogService
import groovy.transform.CompileStatic
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * OPEN API 控制类，API入口
 *
 * @author 逄占春
 * @since 0.0.1
 * @date 2018-03-27
 */
@RestController
@RequestMapping('/api/ofs')
@CompileStatic
class ApiController extends BaseController {

    @Autowired
    ShippingContainerService scSvc
    @Autowired
    ShipmentHeaderService shSvc

    @Override
    ResponseMessage process(TtxSession sess, String data, String format) {
        ShipmentApiWrapper sw
        ResponseMessage rsp
        boolean hasError = false
        StringBuilder sbError = new StringBuilder()

        sw = ObjectMapperUtils.getObjectMapper(format).readValue(data, ShipmentApiWrapper.class)
        for(ShippingContainerApiWrapper scw : sw.containers){
            ShippingContainerHeader sch = scw.header.shippingContainerHeader
            List<ShippingContainerDetail> scds = getRawContainerDetails(scw.details)
            rsp = scSvc.create(sess, sch, scds)
            //如保存出错，继续下一个出库箱
            if (rsp.hasError()){
                hasError = true
                sbError.append(rsp.msg)
                continue
            }

            rsp = scSvc.dispatch(sess, sch, scds, true)
            //如保存出错，继续下一个出库箱
            if (rsp.hasError()){
                hasError = true
                sbError.append(rsp.msg)
            }
        }
        //每完成一个出库单，尝试更新出库单发货状态
        //如果出库箱都确认成功的话，出库单发货确认成功与否，与wms无关，所以这里的错误可以忽略
        rsp = shSvc.updateStatistics(sess, sw.header.shipmentHeader.id)
        if (rsp.hasError()){
            //TODO: 错误处理
        }
        //尝试发运出库单
        return shSvc.tryDispatch(sess, sw.header.shipmentHeader.id)
    }

    /**
     * 带拆单流程的发货确认，目前不使用
     * @param sess
     * @param data
     * @param format
     * @return
     */
    ResponseMessage processWithSplit(TtxSession sess, String data, String format) {
        ShipmentApiWrapper sw
        List<ShipmentApiWrapper> sws
        ResponseMessage rsp
        boolean hasError = false
        StringBuilder sbError = new StringBuilder()

        sw = ObjectMapperUtils.getObjectMapper(format).readValue(data, ShipmentApiWrapper.class)
        //拆分出库单
        sws = splitByShipment(sess, sw)

        //循环出库单，保存出库箱并确认
        for(ShipmentApiWrapper newSw : sws){
            for(ShippingContainerApiWrapper scw : newSw.containers){
                ShippingContainerHeader sch = scw.header.shippingContainerHeader
                List<ShippingContainerDetail> scds = getRawContainerDetails(scw.details)
                rsp = scSvc.create(sess, sch, scds)
                //如保存出错，继续下一个出库箱
                if (rsp.hasError()){
                    hasError = true
                    sbError.append(rsp.msg)
                    continue
                }

                //获取出库单明细行上的inventoryId

                rsp = scSvc.dispatch(sess, sch, scds, true)
                //如保存出错，继续下一个出库箱
                if (rsp.hasError()){
                    hasError = true
                    sbError.append(rsp.msg)
                }
            }
            //每完成一个出库单，尝试更新出库单发货状态
            //如果出库箱都确认成功的话，出库单发货确认成功与否，与wms无关，所以这里的错误可以忽略
            shSvc.dispatch(sess, newSw.header.shipmentHeader.id)
        }

        if (hasError)
            return ResponseMessageFactory.error(sess, sbError.toString())
        else
            return ResponseMessageFactory.success()

    }

    List<ShipmentApiWrapper> splitByShipment(TtxSession sess, ShipmentApiWrapper sw){
        return []
    }

    /**
     * 从原始结构中抽出出库箱明细列表
     * @param details
     * @return
     */
    private static List<ShippingContainerDetail> getRawContainerDetails(List<ShippingContainerDetailApiWrapper> details){
        List<ShippingContainerDetail> scds = new ArrayList<ShippingContainerDetail>()
        for(ShippingContainerDetailApiWrapper scdw : details){
            scds.add(scdw.shippingContainerDetail)
        }
        return scds
    }
}
