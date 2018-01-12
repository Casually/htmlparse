package cc.casually.htmlparse.staticdata;

/**
 * 正则匹配
 * @author 13545
 * @create-time 2017/10/30 22:03
 */
public class RegexStaticData {

    /**
     * 文件路劲匹配正则
     */
    public static final String filePathRegex = "[a-zA-Z]:\\\\.*";

    /**
     * 网络连接匹配正则
     */
    public static final String htmlUriRegex = "(http://|https://).*";

    /**
     * 文件后缀名匹配
     */
    public static final String imgPostfixRegex = "\\.[a-zA-Z]{2,4}";
}
