package cc.casually.htmlparse;

import java.util.HashMap;

/**
 * 发送微信用户信息实体类
 */
public class SendDateInfo {

    private String uri;
    private HashMap<String ,String> param;
    private String userName;
    private String userPhone;

    public SendDateInfo(String uri, HashMap<String, String> param, String userName, String userPhone) {
        this.uri = uri;
        this.param = param;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public SendDateInfo() {
        param = new HashMap<String,String>();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HashMap<String, String> getParam() {
        return param;
    }

    public void setParam(HashMap<String, String>  param) {
        this.param = param;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        this.param.put("userName",this.userName);
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        this.param.put("userPhone",this.userPhone);
    }

    public void addParam(String key,String value){
        this.param.put(key,value);
    }
}
