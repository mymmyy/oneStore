package cn.onestore.domain;

import java.util.Date;

/**
 * Created by 明柯 on 2017/4/27.
 * 作为商品表和 购物车表的第三张表映射实体类
 */
public class Scp {
    private Integer shopcartid;
    private Integer pid;
    private Integer count;
    private Date updatetime;

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getShopcartid() {
        return shopcartid;
    }

    public void setShopcartid(Integer shopcartid) {
        this.shopcartid = shopcartid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}
