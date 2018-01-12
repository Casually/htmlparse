package cc.casually.htmlparse.nodeutil;

/**
 * 标签属性
 * @user Administrator
 * @author
 * @CreateTime 2017/10/26 11:05
 */
public class NodeAttribute {

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NodeAttribute{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
