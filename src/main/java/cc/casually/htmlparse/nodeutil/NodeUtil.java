package cc.casually.htmlparse.nodeutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 节点工具类
 * @user Administrator
 * @author
 * @CreateTime 2017/10/24 14:46
 */
public class NodeUtil {

    private static String nodePattern = "<[/\\w]{0,10}+";
    private static String singlePattern = "<(input|hr|br|img|meta|\\!DOCTYPE|link|\\!doctype)[\\w\\s=\"'/\\.\\-_:;\\(\\)\\u4E00-\\u9FFF]*>";

    private static Pattern patternNodes = Pattern.compile(nodePattern);
    private static Pattern patternSingleTagS = Pattern.compile(singlePattern);

    /**
     * 获取所有的节点标签信息
     * 节点标签名称 tag
     * 节点开始字符串位置 start
     * 节点当前编号 num
     * 节点整体编号 entiretyNum
     * @param str
     * @return
     */
    public Map<String,List<Node>> getNodeS(String str){
        List<Node> nodeStartS = new ArrayList<>();
        List<Node> nodeEndS = new ArrayList<>();
        Matcher matcher = patternNodes.matcher(str);
        int startNum = 0;
        int endNum = 0;
        int entiretyNum = 0;
        while (matcher.find()){
            Node node = new Node();
            String nodeTag = matcher.group();
            int start = matcher.start();
            node.setTag(nodeTag.indexOf(">") != -1?nodeTag:nodeTag.split(" ")[0] + ">");
            node.setStart(start);
            node.setEntiretyNum(entiretyNum);
            if(nodeTag.indexOf("/") != -1){
                node.setNum(endNum);
                endNum++;
                nodeEndS.add(node);
            }else{
                node.setNum(startNum);
                startNum++;
                nodeStartS.add(node);
            }
            entiretyNum++;
        }
        Map<String,List<Node>> result = new HashMap<>();
        result.put("nodeStartS",nodeStartS);
        result.put("nodeEndS",nodeEndS);

        //处理单标签
        matcher = patternSingleTagS.matcher(str);
        List<Node> singleTagS = new ArrayList<>();
        while (matcher.find()){
            Node node = new Node();
            String nodeTag = matcher.group();
            int start = matcher.start();
            node.setTag(nodeTag.split(" ")[0] + ">");
            node.setStart(start);
            node.setEntiretyNum(entiretyNum);
            node.setContext(nodeTag);
            singleTagS.add(node);
            entiretyNum++;
        }
        result.put("singleTagS",singleTagS);
        //获取单标签信息
        return result;
    }

    /**
     * 将Map转换成String
     * @param hashMap
     * @return
     */
    public String getHashMapStr(HashMap<String,Object> hashMap){
        StringBuilder hashMapStr = new StringBuilder();
        hashMapStr.append("[ ");
        for (String key:hashMap.keySet()) {
            hashMapStr.append(String.format("%s=%s, ",key,hashMap.get(key)));
        }
        hashMapStr.deleteCharAt(hashMapStr.length() - 1);
        hashMapStr.append(" ]");
        return hashMapStr.toString();
    }

}
