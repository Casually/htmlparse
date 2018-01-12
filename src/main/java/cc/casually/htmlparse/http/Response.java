package cc.casually.htmlparse.http;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 请求回复类
 */
public class Response {

    private Map<String, List<String>> header;
    private byte[] body;
    private String charset;
    private int status;

    public Response(){
        status = 0;
        charset = "UTF-8";
    }

    /**
     * 获取头信息
     * @return
     */
    public Map<String, List<String>> getHeader() {
        return header;
    }

    /**
     * 返回头信息（字符串形式）
     * @return
     */
    public String getHeaderStr(){
        StringBuffer headerStr = new StringBuffer();
        for (String key:header.keySet()) {
            headerStr.append(String.format("%s=%s,",key,header.get(key)));
        }
        headerStr.deleteCharAt(headerStr.length()-1);
        return headerStr.toString();
    }

    /**
     * 设置头信息
     * @param header
     */
    public void setHeader(Map<String, List<String>> header) {
        this.header = header;
    }

    /**
     * 获取body信息（字节码）
     * @return
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * 设置body信息
     * @param body
     */
    public void setBody(byte[] body) {
        this.body = body;
    }


    public void setBody(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            inputStream.close();
            setBody(outStream.toByteArray());
        }
    }
    /**
     * 获取编码
     * @return
     */
    public String getCharset() {
        return charset;
    }

    /**
     * 设置编码
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * 获取返回码
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * 设置返回码
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 获取body信息（字符串）
     * @return
     */
    public String getBodyStr() {
        if (body == null) {
            return "";
        }
        try {
            return new String(body, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(body);
        }
    }

    /**
     * 获取body信息（json格式会抛json解析异常）
     * @return
     */
    public JSONObject getBodyJson() {
        return new JSONObject(getBodyStr());
    }

    /**
     * 获取body信息（Map<String,String>格式）
     * @return
     */
    public Map<String,String> getBodyMap(){
        JSONObject jsonObject = new JSONObject(getBodyStr());
        Map<String,String> result = new HashMap<String, String>();
        Iterator iterator = jsonObject.keys();
        while (iterator.hasNext()){
            Object key = iterator.next();
            if(key == null || "".equals(key)){
                break;
            }else {
                Object value = jsonObject.get(key.toString()) != null?jsonObject.get(key.toString()):"";
                result.put(key.toString(),value.toString());
            }
        }
        return result;
    }
}
