package test;

import cc.casually.htmlparse.nodeutil.Node;
import cc.casually.htmlparse.nodeutil.Nodes;
import tskspx.Tskspx;

public class TskspxTest {

    public static void main(String[] args) {
        Tskspx tskspx = new Tskspx();
        tskspx.loginSystem("18710716894","123456");
        tskspx.intoSystem();
        tskspx.choiceQuestionType("768007");

        for (int i = 1;i<632;i++){
            String topicHtmlstr = tskspx.getTopicHtmlStr("020553",String.valueOf(i),"6","2033073");
            Nodes nodes = new Nodes(topicHtmlstr);
            System.out.println("=======================分割线========================");
            System.out.println("【题目：】" + nodes.getListNodeForTagAndSite("td",5).getContext() /*+ "【类型：】" + nodes.getListNodeForTagAndSite("span",3).getContext()*/);
            for (Node node:nodes.getListNodeForTag("label")) {
                System.out.println("【答案：】" + node.getContext());
            }
            System.out.println("【正确答案：】" + nodes.getListNodeForTagAndSite("span",i == 1?5:4).getContext());
        }

    }
}
