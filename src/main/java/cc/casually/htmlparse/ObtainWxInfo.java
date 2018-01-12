package cc.casually.htmlparse;

import cc.casually.htmlparse.http.HttpMethodName;
import cc.casually.htmlparse.http.HttpCharacterEncoding;
import cc.casually.htmlparse.http.HttpClient;
import cc.casually.htmlparse.http.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * 获取微信信息
 */
public class ObtainWxInfo {

    private String appsecret;
    private String appid;
    private String openId;
    private String refreshToken;
    private String accessToken;
    private Request request = new Request();

    public ObtainWxInfo( String appid, String appsecret) {
        this.appid = appid;
        this.appsecret = appsecret;
        this.openId = "";
        this.refreshToken = "";
        this.accessToken = "";
    }

    public Map<String,String> getWxInfo(HttpServletRequest request){

        Map<String,String> result = getOpenIdANDRefreshToken(request);
        if(result != null || result.size() != 0){
            this.openId = result.get("openid");
            this.refreshToken = result.get("refresh_token");
            System.out.println(this.openId);
            System.out.println(this.refreshToken);
        }
        result = getAccessToken();
        if(result != null || result.size() != 0){
            this.accessToken = result.get("access_token");
            System.out.println(this.accessToken);
        }

        return getUserInfo();
    }

    /**
     * 获取openid
     * @param request
     * @return
     */
    public Map<String,String> getOpenIdANDRefreshToken(HttpServletRequest request){
        String code = request.getParameter("code");
        this.request.setUri("https://api.weixin.qq.com/sns/oauth2/access_token");
        this.request.addParam("appid",this.appid);
        this.request.addParam("secret",this.appsecret);
        this.request.addParam("code",code);
        this.request.addParam("grant_type","authorization_code");
        this.request.setHttpMethodName(HttpMethodName.GET);
        this.request.setContentEncoding(HttpCharacterEncoding.ENCODE_GBK);
        Map<String,String> result = HttpClient.httpGet(this.request).getBodyMap();
        return result;
    }

    /**
     * 获取access_token
     * @return
     */
    public Map<String ,String> getAccessToken(){
        this.request.setUri("https://api.weixin.qq.com/sns/oauth2/refresh_token");
        this.request.deleteParam("secret");
        this.request.deleteParam("code");
        this.request.addParam("grant_type","refresh_token");
        this.request.addParam("refresh_token",this.refreshToken);
        this.request.setHttpMethodName(HttpMethodName.GET);
        return HttpClient.httpGet(this.request).getBodyMap();
    }

    /**
     * 获取用户信息
     * @return
     */
    public Map<String,String> getUserInfo(){
        this.request.setUri("https://api.weixin.qq.com/sns/userinfo");
        this.request.deleteParamAll();
        this.request.addParam("access_token",this.accessToken);
        this.request.addParam("openid",this.openId);
        this.request.addParam("lang","zh_CN");
        this.request.setHttpMethodName(HttpMethodName.GET);
        Map<String,String> result = HttpClient.httpGet(this.request).getBodyMap();
        System.out.println(this.request.getParamStr());
        for (Iterator<String> it = result.keySet().iterator(); it.hasNext(); ){
            String key = it.next();
            System.out.println(String.format("%s=%s",key,result.get(key)));
        }
        return result;
    }


}
