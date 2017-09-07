package cn.onestore.domain;

/**
 * Created by 明柯 on 2017/4/24.
 * 专门用来存储分页面信息
 */
public class PageBean {
    //总记录数
    private Integer totalCount;
    //总页数
    private Integer totalPage;
    //每页记录数， 没页多少条
    private Integer pageSize;
    //当前页
    private Integer currentPage;
    //开始位置
    private Integer begin;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }
}
