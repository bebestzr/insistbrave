package com.insistbrave.api.service

import com.insistbrave.api.interf.IApiHandler
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.service.BaseService
import groovy.transform.CompileStatic
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
class InventoryAdjustApiHandler extends BaseService implements  IApiHandler{
    @Override
    ResponseMessage process(TtxSession session, String data, String format) {
        return null
    }
}
