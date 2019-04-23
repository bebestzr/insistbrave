package com.insistbrave.master.domain

import groovy.transform.CompileStatic

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/28
 */
@CompileStatic
class MasterConstants {

    //重量单位
    class WeightUM{
        static final String G = "G"
        static final String KG = "KG"
    }

    //体积单位
    class VolumeUM{
        static final String CM3 = "CM3"
        static final String M3 = "M3"
    }

    //BOM类型
    class BomType{
        static final Integer NOT_BOM = 0
        static final Integer ACTUAL_BOM = 1
        static final Integer VIRTUAL_BOM = 2
        static final Integer BOM_DETAIL = 3
    }

    class SkuMappingType{
        static final String SPU_AND_SKU = "SPU_AND_SKU"
        static final String SKU = "SKU"
    }
}
