package utils;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 分页
 * Created by weican on 2017/10/12.
 */
public class PageModel<T> {

    public static final String mStartRow = "startRows";
    public static final String mPageSize = "pageSize";
    public static final String mCondition = "condition";

    @ApiModelProperty(value = "当前页")
    private int currentPage;
    @ApiModelProperty(value = "每页显示条数")
    private int pageSize;
    @ApiModelProperty(value = "总页数")
    private int totalPage;
    @ApiModelProperty(value = "总记录数")
    private int totalRecord;
    @ApiModelProperty(value = "开始行")
    private int startRows;
    @ApiModelProperty(value = "分页数据")
    private List<T> dataList;

    private PageModel() {

    }

    private PageModel(final int pageSize, final int page, final int totalRecord) {

        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        setTotalPage();
        setCurrentPage(page);
        this.startRows = (currentPage - 1) * pageSize;
    }

    /**
     * 初始化分页对象
     *
     * @param pageSize    每页显示条数
     * @param page        当前页
     * @param totalRecord 总记录条数
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static PageModel newPageModel(final int pageSize, final int page, final int totalRecord) {
        return new PageModel(pageSize, page, totalRecord);
    }

    public void setCurrentPage(int page) {

        currentPage = page;

        if (currentPage < 1) {
            currentPage = 1;
        }
//        if (currentPage > totalPage) {
//            currentPage = totalPage;
//        }
    }

    private void setTotalPage() {
        if (totalRecord % pageSize == 0) {
            totalPage = totalRecord / pageSize;
        } else {
            totalPage = totalRecord / pageSize + 1;
        }
    }

    public int getPrevious() {
        return currentPage - 1;
    }

    public int getNext() {
        return currentPage + 1;
    }

    public int getLast() {
        return totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getStartRows() {
        return startRows;
    }

    public void setStartRows(int startRows) {
        this.startRows = startRows;
    }

    @Override
    public String toString() {
        return "PageModel{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", totalRecord=" + totalRecord +
                ", startRows=" + startRows +
                ", dataList=" + dataList +
                '}';
    }
}
