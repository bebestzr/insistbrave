package com.insistbrave.api.service

import com.fasterxml.jackson.databind.ser.Serializers
import com.insistbrave.api.domain.ApiMessages
import com.insistbrave.api.interf.IApiHandler
import com.insistbrave.general.utils.ObjectMapperUtils
import com.insistbrave.master.domain.Area
import com.insistbrave.master.service.AreaService
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.ResponseMessageFactory
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.general.service.ExceptionManager
import com.ittx.cbt.service.BaseService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/28
 */
@Service
@CompileStatic
class AreaSaveApiHandlerService extends BaseService implements IApiHandler{
    @Autowired
    AreaService areaService

    @Override
    ResponseMessage process(TtxSession session,String data,String format){
        Area area
        ResponseMessage rsp
        try{
            area = ObjectMapperUtils.getObjectMapper(format).readValue(data,Area.class)
            if(area == null){
                return ResponseMessageFactory.error(session,ApiMessages.MSG_OAPI_0002)
            }
            rsp = saveAreasFromTaobao(session,area)
        }catch (Exception e){
            ExceptionManager.logException(session,e)
            rsp = ResponseMessageFactory.error(session,ApiMessages.MSG_OAPI_0000)
        }
        rsp
    }

    ResponseMessage saveAreasFromTaobao(TtxSession session, Area area) {
        ResponseMessage rsp
        try{
            area.version = 0
            area.status = 1
            Area local = areaService.getEntity(session,area.code)
            if(local!=null){
                local.name = area.name
                local.level = area.level
                local.country = area.country
                local.state = area.state
                local.city = area.city
                local.district = area.district
                local.street = area.street
                local.parent = area.parent
                rsp = areaService.updateEntity(session,local)
            }else {
                rsp = areaService.createEntity(session,area)
            }

        }catch (Exception e){
            ExceptionManager.logException(session,e)
            return ResponseMessageFactory.error(session,ApiMessages.MSG_OAPI_0000)
        }
        rsp
    }
}
