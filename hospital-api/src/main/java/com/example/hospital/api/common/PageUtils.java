package com.example.hospital.api.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageUtils implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * total
     */
    private long totalCount;
    /**
     * Number of records per page
     */
    private int pageSize;
    /**
     * total pages
     */
    private int totalPage;
    /**
     * current page number
     */
    private int pageIndex;
    /**
     * List data
     */
    private List list;

    public PageUtils(List list, long totalCount, int pageIndex, int pageSize) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

}