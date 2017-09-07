package cn.onestore.domain;

/**
 * Created by 明柯 on 2017/4/21.
 * 类别数据字典对应的domain
 */
public class Category {
    private Integer cid;
    private String cname;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }


}
