package cc.casually.htmlparse;

import cc.casually.htmlparse.http.HttpClient;
import cc.casually.htmlparse.http.Response;
import cc.casually.htmlparse.http.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * 將用戶信息发送至服务器
 */
public class SendDataToServer {

    /**
     * 将用户信息发送至指定服务器
     * @param sendDateInfo 发送信息类
     * @return
     */
    public static Map<String,String> sendDataInfo(SendDateInfo sendDateInfo){
        Request request = new Request();
        Map<String,String> result = new HashMap<String,String >();
        request.setUri(sendDateInfo.getUri());
        request.setParams(sendDateInfo.getParam());
        Response response =  HttpClient.post(request);
        System.out.println(sendDateInfo.getUri());
        System.out.println(request.getParamStr());
        System.out.println(response.getBodyStr());
        System.out.println(response.getStatus());

        if("".equals(response.getBodyStr())){
            result.put("state","error");
        }else{
            result = response.getBodyMap();
            result.put("state","success");
        }
        result.put("typecode",new Integer(response.getStatus()).toString());
        return result;
    }
}
