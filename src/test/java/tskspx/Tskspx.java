package tskspx;

import cc.casually.htmlparse.http.Headers;
import cc.casually.htmlparse.http.HttpClient;
import cc.casually.htmlparse.http.Response;
import cc.casually.htmlparse.nodeutil.Node;
import cc.casually.htmlparse.nodeutil.Nodes;
import cc.casually.htmlparse.http.Request;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tskspx {

    private Request request;
    public Tskspx() {
        this.request = new Request();
    }

    /**
     * 登录系统
     * @param userName 登录用户名
     * @param password 登录密码
     * @return
     */
    public Map<String,Object> loginSystem(String userName, String password){
        Map<String,Object> reslut = new HashMap<>();
        request.setUri("http://www.tskspx.com/userLogin.do");
        request.addParam("userid",userName);
        request.addParam("password",password);
        Response response = HttpClient.post(request);
        JSONObject jsonObject = response.getBodyJson();
        int status = response.getStatus();
        reslut.put("status",status);
        reslut.put("response",response);
        if(status != 200){
            reslut.put("state","error");
        }else{
            reslut.put("state","success");
        }
        request.addHeader(Headers.COOKIE,response.getHeader().get("Set-Cookie").toString());
        return reslut;
    }

    /**
     * 进入系统
     * @return
     */
    public Response intoSystem(){
        request.setUri("http://www.tskspx.com/toSelectProject.do");
        request.deleteParamAll();
        return HttpClient.post(request);
    }

    /**
     * 选择题型
     * @param apId
     * @return
     */
    public Response choiceQuestionType(String apId){
        request.setUri("http://www.tskspx.com/selectProject.do");
        request.deleteParamAll();
        request.addParam("apId",apId);
        return HttpClient.httpGet(request);
    }
    /**
     * 获取首页html
     * @return
     */
    public String getIndexHtmlStr(){
        request.setUri("http://www.tskspx.com/toSelectProject.do");
        request.deleteParamAll();
        String indexHtml = HttpClient.post(request).getBodyStr();
        return indexHtml;
    }

    /**
     * 获取首页html所有节点
     * @return
     */
    public List<Node> getIndexNodeS() {
        Nodes nodes = new Nodes(getIndexHtmlStr());
        List<Node> listNodes = nodes.getListNodeAll();
        return listNodes;
    }

    /**
     * 获取指定标签的节点数据
     * @param tag
     * @return
     */
    public List<Node> getIndexNodeSForTag(String tag){
        Nodes nodes = new Nodes(getIndexHtmlStr());
        List<Node> listNodes = nodes.getListNodeForTag(tag);
        return listNodes;
    }

    /**
     * 获取题目html
     * @param topicNum
     * @return
     */
    public String getTopicHtmlStr(String topicNum,String currentPage,String stlx,String stbh){
        request.setUri(String.format("http://www.tskspx.com/user/%s/trainBegin.do",topicNum));
        request.deleteParamAll();
        request.addParam("currentPage",currentPage);
        request.addParam("stlx",stlx);
        request.addParam("answer","");
        request.addParam("stbh",stbh);
        Response response = HttpClient.httpGet(request);
        String topicHtmlStr = response.getBodyStr();
        return topicHtmlStr;
    }
}
