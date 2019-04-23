package com.insistbrave.api.interf

import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.TtxSession
import groovy.transform.CompileStatic

@CompileStatic
interface IApiHandler {

    /**
     * api处理接口
     * @param session
     * @param data
     * @param format
     * @return
     */
    ResponseMessage process(TtxSession session, String data ,String format)

}