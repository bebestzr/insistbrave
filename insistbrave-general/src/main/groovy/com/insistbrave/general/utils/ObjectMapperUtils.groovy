package com.insistbrave.general.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.insistbrave.general.domain.GeneralConstants
import com.ittx.cbt.util.ObjectMapperFactory
import groovy.transform.CompileStatic
import org.apache.commons.lang.StringUtils

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/16
 */
@CompileStatic
class ObjectMapperUtils {
    static ObjectMapper getObjectMapper(String format){
        if(StringUtils.equalsIgnoreCase(format,GeneralConstants.EdiDataFormat.XML)){
            return ObjectMapperFactory.XML
        }else {
            return ObjectMapperFactory.JSON
        }
    }
}
