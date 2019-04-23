package com.insistbrave.api.domain

import com.insistbrave.general.domain.GeneralMessage
import groovy.transform.CompileStatic

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/15
 */
@CompileStatic
class ApiMessages extends GeneralMessage{
    
    static  String MSG_OAPI_0000 = "MSG_OAPI_0000" //系统接收到消息，但处理过程发生异常
    static  String MSG_OAPI_0001 = "MSG_OAPI_0001" //为注册的api类型：{0}
    static  String MSG_OAPI_0002 = "MSG_OAPI_0002" //无法将入参转化为接口需要的标准对象
}
