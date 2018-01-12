package cc.casually.htmlparse.http;

import cc.casually.htmlparse.util.ClientConfiguration;
import cc.casually.htmlparse.util.Util;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * 请求信息设置类
 */
public class Request {

    private HashMap<String, String> headers;
    private HashMap<String, String> params;
    private HashMap<String, Object> body;
    private URI uri;
    private HttpMethodName httpMethodName;
    private EBodyFormat bodyFormat;
    private String contentEncoding;
    private ClientConfiguration config;

    public Request() {
        headers = new HashMap<String, String>();
        params =  new HashMap<String, String>();
        body =  new HashMap<String, Object>();;
        httpMethodName = HttpMethodName.POST;
        bodyFormat = EBodyFormat.FORM_KV;
        contentEncoding = HttpCharacterEncoding.DEFAULT_ENCODING;
        config = null;
    }

    public Request(HashMap<String, String> header, HashMap<String, String> bodyParams) {
        headers = header;
        params = bodyParams;
    }

    /**
     * 获取请求头信息
     * @return
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /**
     * 返回头信息（字符串形式）
     * @return
     */
    public String getHeaderStr(){
        StringBuffer headerStr = new StringBuffer();
        for (String key:headers.keySet()) {
            headerStr.append(String.format("%s=%s,",key,headers.get(key)));
        }
        headerStr.deleteCharAt(headerStr.length()-1);
        return headerStr.toString();
    }

    /**
     * 设置请求头信息
     * @param headers
     */
    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    /**
     * 获取参数信息（HashMap<String,String>格式返回）
     * @return
     */
    public HashMap<String, String> getParams() {
        return params;
    }

    /**
     * 设置参数信息
     * @param params
     */
    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    /**
     * 获取body信息（返回格式HashMap<String,Object>）
     * @return
     */
    public HashMap<String, Object> getBody() {
        return body;
    }

    /**
     * 设置body信息
     * @param body
     */
    public void setBody(HashMap<String, Object> body) {
        this.body = body;
    }

    /**
     * 获取请求连接（返回URI格式）
     * @return
     */
    public URI getUri() {
        return uri;
    }

    /**
     * 设置请求连接
     * @param uri
     */
    public void setUri(URI uri) {
        this.uri = uri;
    }

    /**
     * 获取请求方式
     * @return
     */
    public HttpMethodName getHttpMethodName() {
        return httpMethodName;
    }

    /**
     * 设置请求方式
     * @param httpMethodName
     */
    public void setHttpMethodName(HttpMethodName httpMethodName) {
        this.httpMethodName = httpMethodName;
    }

    /**
     * 获取请求body格式
     * @return
     */
    public EBodyFormat getBodyFormat() {
        return bodyFormat;
    }

    /**
     * 设置请求body格式
     * @param bodyFormat
     */
    public void setBodyFormat(EBodyFormat bodyFormat) {
        this.bodyFormat = bodyFormat;
    }

    /**
     * 获取内容编码
     * @return
     */
    public String getContentEncoding() {
        return contentEncoding;
    }

    /**
     * 设置内容编码
     * @param contentEncoding
     */
    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    /**
     * 获取config
     * @return
     */
    public ClientConfiguration getConfig() {
        return config;
    }

    /**
     * 设置config
     * @param config
     */
    public void setConfig(ClientConfiguration config) {
        this.config = config;
    }

    /**
     * 添加头信息
     * @param key
     * @param value
     */
    public void addHeader(String key, String value) {
        headers.put(key, value);
        if (key.equals(Headers.CONTENT_ENCODING)) {
            this.contentEncoding = value;
        }
    }

    /**
     * 添加参数
     * @param key
     * @param value
     */
    public void addParam(String key, String value) {
        params.put(key, value);
    }

    /**
     * 添加body信息
     * @param key
     * @param value
     */
    public void addBody(String key, Object value) {
        body.put(key, value);
    }

    /**
     * 添加body信息
     * @param other
     */
    public void addBody(HashMap other) {
        body.putAll(other);
    }

    /**
     * get body content depending on bodyFormat
     * @return body content as String
     */
    public String getBodyStr() {
        ArrayList<String> arr = new ArrayList<String>();
        if (bodyFormat.equals(EBodyFormat.FORM_KV)) {
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                if (entry.getValue() == null || entry.getValue().equals("")) {
                    arr.add(Util.uriEncode(entry.getKey(), true));
                } else {
                    arr.add(String.format("%s=%s", Util.uriEncode(entry.getKey(), true),
                            Util.uriEncode(entry.getValue().toString(), true)));
                }
            }
            return Util.mkString(arr.iterator(), '&');
        }
        else if (bodyFormat.equals(EBodyFormat.RAW_JSON)) {
            JSONObject json = new JSONObject();
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }
            return json.toString();
        }
        return "";
    }

    /**
     * 获取参数信息（字符串格式）
     * @return
     */
    public String getParamStr() {
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            buffer.append(String.format("%s=%s&", entry.getKey(), entry.getValue()));
        }
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString();
    }

    /**
     * 返回参数JSON格式
     * @return
     */
    public JSONObject getParamJSON(){
        return new JSONObject(params);
    }

    /**
     * 设置请求连接
     * @param url
     */
    public void setUri(String url) {
        try {
            this.uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除所有参数
     */
    public void deleteParamAll(){
        Iterator iterator = params.keySet().iterator();
        String[] keys = new String[params.size()];
        int i = 0;
        while (iterator.hasNext()){
            Object key = iterator.next();
            if(key == null || "".equals(key)){
                break;
            }else {
                keys[i] = key.toString();
            }
            i++;
        }
        for (String str:keys){
            params.remove(str);
        }
    }

    /**
     * 删除指定参数
     * @param key
     */
    public void  deleteParam(String key){
        params.remove(key);
    }
}
