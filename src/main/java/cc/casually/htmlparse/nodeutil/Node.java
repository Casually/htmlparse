package cc.casually.htmlparse.nodeutil;

import cc.casually.htmlparse.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 节点实体类
 * @user Administrator
 * @author
 * @CreateTime 2017/10/26 16:51
 */
public class Node {

    private String tag;
    private String context;
    private int start;
    private int num;
    private int entiretyNum;
    private Node node;
    private static String nodeAttributePatternStr = "\\w*(=\")[\\w\\s/\\?#&;\\$\\.\\+,=\\-_:\\(\\)\\u4E00-\\u9FFF]*\"";
    private static Pattern nodeAttributePattern = Pattern.compile(nodeAttributePatternStr);

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getEntiretyNum() {
        return entiretyNum;
    }

    public void setEntiretyNum(int entiretyNum) {
        this.entiretyNum = entiretyNum;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * 获取标签所有的属性（不包含DOM事件）
     * @return
     */
    public List<NodeAttribute> getAttribute(){
        Matcher matcher = nodeAttributePattern.matcher(getContext());
        List<NodeAttribute> listNodeAttribute = new ArrayList<>();
        while (matcher.find()){
            String nodeAttributeStr = matcher.group();
            NodeAttribute nodeAttribute = new NodeAttribute();
            if (nodeAttributeStr.indexOf("=\"") != -1){
                nodeAttribute.setName(nodeAttributeStr.substring(0,nodeAttributeStr.indexOf("=\"")));
                nodeAttribute.setValue(nodeAttributeStr.substring(nodeAttributeStr.indexOf("=\"") + 2,nodeAttributeStr.lastIndexOf("\"")));
            }else {
                nodeAttribute.setName(nodeAttributeStr.substring(0,nodeAttributeStr.indexOf("='")));
                nodeAttribute.setValue(nodeAttributeStr.substring(nodeAttributeStr.indexOf("='") + 2,nodeAttributeStr.lastIndexOf("'")));
            }
            listNodeAttribute.add(nodeAttribute);
        }

        return listNodeAttribute;
    }

    /**
     * 获取指定属性值
     * @param attribute
     * @return
     */
    public String getAttributeValue(String attribute){
        boolean isFlag = Util.findInSet(attribute,HtmlStaticData.FUNCTIONATTRIBUTETYPE);
        String navps = "";
        if(isFlag){
            if(getContext().indexOf(attribute + "=\"") != -1){
                navps = String.format("%s=(\")[\\w\\s=/#\"'\\?&;\\$\\+,\\.\\-_:\\(\\)\\u4E00-\\u9FFF]*(\")",attribute);
            }else {
                navps = String.format("%s=(')[\\w\\s=/#\"'\\?&;\\$\\+,\\.\\-_:\\(\\)\\u4E00-\\u9FFF]*(')",attribute);
            }
        }else{
            if(getContext().indexOf(attribute + "=\"") != -1){
                navps = String.format("%s=(\")[\\w\\s=/#\\?&;\\$\\.\\+,\\-_:\\(\\)\\u4E00-\\u9FFF]*(\")",attribute);
            }else {
                navps = String.format("%s=(')[\\w\\s=/#\\?&;\\$\\.\\+,\\-_:\\(\\)\\u4E00-\\u9FFF]*(')",attribute);
            }
        }
        Pattern nodeAttributeValuePattern = Pattern.compile(navps);
        Matcher matcher = nodeAttributeValuePattern.matcher(getContext());
        while (matcher.find()){
            String attributeAndValue = matcher.group();
            if (!"".equals(attribute)){
                if(isFlag){
                    return attributeAndValue.indexOf("=\"") != -1 ?
                            attributeAndValue.substring(attributeAndValue.indexOf("=\"")
                                    + 2,attributeAndValue.lastIndexOf(attributeAndValue.indexOf("\" ") != -1 ? "\" ":"\""))
                            :attributeAndValue.substring(attributeAndValue.indexOf("='")
                                    + 2,attributeAndValue.lastIndexOf(attributeAndValue.indexOf("' ") != -1 ? "' ":"'"));
                }
                return attributeAndValue.indexOf("=\"") != -1 ?
                        attributeAndValue.substring(attributeAndValue.indexOf("=\"") + 2,attributeAndValue.lastIndexOf("\""))
                        :attributeAndValue.substring(attributeAndValue.indexOf("='") + 2,attributeAndValue.lastIndexOf("'"));
            }
        }
        return null;
    }

    /**
     * 获取节点文本信息
     * @return
     */
    public String getText(){
        String singleTag = "<inpt>,<img>,<meta>,<link>";
        if(Util.findInSet(getTag(),singleTag)){
            return getContext();
        }
        return getContext().substring(getContext().indexOf(">") + 1,getContext().indexOf("</"));
    }

    @Override
    public String toString() {
        return "Node{" +
                "tag='" + tag + '\'' +
                ", context='" + context + '\'' +
                ", start=" + start +
                ", num=" + num +
                ", entiretyNum=" + entiretyNum +
                '}';
    }
}
