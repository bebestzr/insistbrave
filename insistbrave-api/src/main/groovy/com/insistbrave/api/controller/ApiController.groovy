package com.insistbrave.api.controller

import com.insistbrave.api.service.ApiService
import com.insistbrave.general.domain.InterfaceLog
import com.insistbrave.general.service.InterfaceLogService
import com.ittx.ais.model.AisMessage
import com.ittx.ais.service.AisMessageSender
import com.ittx.cbt.controller.BaseController
import com.ittx.cbt.general.ResponseMessage
import com.ittx.cbt.general.ResponseMessageFactory
import com.ittx.cbt.general.TtxSession
import com.ittx.cbt.general.service.ExceptionManager
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
 * api 控制类
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/15
 */
@RestController
@CompileStatic
@RequestMapping('/api/insistbrave')
class ApiController extends BaseController{
    @Autowired
    AisMessageSender msgSender

    @Autowired
    ApiService apiSvc

    private final String CHARSET_NAME = "UTF-8"

    @RequestMapping(value = "in",method =RequestMethod.POST )
    ResponseMessage inbound(HttpServletRequest request,@RequestParam Map<String,String> params){
        String api , data , format , logIdInString
        TtxSession session
        Long logId = 0L

        //如果不是通过params传递，需要解读request.body并构建params
        String body = getStreamAsString(request.inputStream,CHARSET_NAME)
        if(params == null || params.size() == 0){
            Map<String,String> bodymap = getParamsFromRequestBody(body,CHARSET_NAME)
            if(bodymap !=null && bodymap.size() >0){
                //简写的三元表达式：params不为null则等于params，为null则new一个map
                params =params?:new HashMap<String,String>()
                params.putAll(bodymap)
            }
        }
        session = getSession()
        api = params.get("api") //api类型
        data = params.get("body")//接口内容
        format = params.get("format")//返回格式：XML,JSON(默认)
        logIdInString = params.get('logId') //EDI记录的接口日志id

        //如果是异步，压消息队列，结束处理
        if(params.containsKey('async')){
            if(Boolean.valueOf(params.get("async"))){
                AisMessage msg = new AisMessage()
                msg.sess = session
                msg.queue = "ofs.async.api"
                msg.msgBody = data
                msg.params =(Map) [api: api, format: format, logId: logId]
                msgSender.sendMessage(msg)
                return ResponseMessageFactory.success(session)
            }
        }
        //同步处理
        if(!StringUtils.isEmpty(logIdInString)){
            logId = Long.parseLong(logIdInString)
        }
        return apiSvc.process(session,api,data,format,logId)
    }


    @RequestMapping(value = "log",method = RequestMethod.POST)
    ResponseMessage log(HttpServletRequest request,@RequestBody InterfaceLog ilog){
        TtxSession session = getSession()
        ResponseMessage res
        try{
            if(ilog.id == null || ilog.id ==0L){
                Long id = InterfaceLogService.log(session,ilog)
                res = ResponseMessageFactory.success(session,id)
            }else if(ilog.status == 900){
                InterfaceLogService.logSuccess(session,ilog.id,ilog.msgReturn,ilog.error)
                res = ResponseMessageFactory.success(session)
            }else{
                InterfaceLogService.logError(session,ilog.id,ilog.msgReturn,ilog.error)
                res = ResponseMessageFactory.success(session)
            }
        }catch (Throwable t){
            ExceptionManager.logException(session,t)
            res = ResponseMessageFactory.error(session,t.message)
        }
        res
    }




    private static Map getParamsFromRequestBody(String requestBody, String charset) {
        if(StringUtils.isEmpty(requestBody)){
            return [:]
        }
        Map map = [:]
        requestBody.split('&').each{
            String[] arr = (it as String).split('=')//split 拆分字符串会剔除空字符串
            if(arr && arr.length >1){
                //URLDecoder.decode(String s, String enc)
                //使用指定的编码机制对 application/x-www-form-urlencoded 字符串解码。
                map[arr[0]] = arr.length >1 ? URLDecoder.decode(arr[1],charset) : ''
            }
        }
        map
    }
/**
     * 从流中读取字符串
     * @param stream
     * @param charset
     * @return
     * @throws IOException
     */
    private static String getStreamAsString(InputStream stream, String charset) throws IOException{
        try{
            //输入流是读read ，输出流是写 write 输入流 是从流中读取数据，通常是硬盘中的数据变成程序中的对象。输出流是从流中写出数据，是程序中的数据到硬盘中的文件
            Reader reader = new InputStreamReader(stream,charset)
            StringBuilder response = new StringBuilder()
            final char[] buff = new char[1024]
            int read
            while((read = reader.read(buff))>0){
                response.append(buff,0,read)
            }
            return response.toString()
        }finally {
            if(stream !=null){
                stream.close()
            }
        }
    }
}
