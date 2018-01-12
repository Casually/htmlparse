package test;

import cc.casually.htmlparse.http.HttpClient;
import cc.casually.htmlparse.http.Request;
import cc.casually.htmlparse.http.Response;
import cc.casually.htmlparse.nodeutil.Node;
import cc.casually.htmlparse.nodeutil.Nodes;

import java.util.List;

public class baidu {
    public static void main(String[] args) {
        Request request = new Request();
        request.setUri("http://zh.wikipedia.org/wiki/人工智能");
        Response response = HttpClient.post(request);
        String bodyStr = response.getBodyStr();
        System.out.println(bodyStr);
       /* Nodes nodes = new Nodes(bodyStr);
        List<Node> listNode = nodes.getListNodeForTag("img");
        for (Node node:listNode) {
            if(node.getAttributeValue("name")!=null){
                System.out.println(node.getAttributeValue("name"));
            }
        }*/
    }
}
