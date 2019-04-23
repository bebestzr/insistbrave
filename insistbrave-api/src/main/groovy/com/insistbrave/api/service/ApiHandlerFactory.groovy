package com.insistbrave.api.service

import com.insistbrave.api.domain.ApiConstants
import com.insistbrave.api.interf.IApiHandler
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.context.WebApplicationContext

/**
 * API 处理工厂，根据api类型，返回对应的处理者
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/15
 */
@Service
@CompileStatic
class ApiHandlerFactory {
    @Autowired
    WebApplicationContext ctx
    
    
    IApiHandler getService(String api){
        switch (api){
            case ApiConstants.ApiType.IBE_SHIPMENT_CONFIRM:
                return ctx.getBean(ShipmentConfirmApiHandlerService.class)
            default:
                break
        }
        
    }
    

}
