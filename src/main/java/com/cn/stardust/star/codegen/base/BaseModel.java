package com.cn.stardust.star.codegen.base;

import java.util.Date;

/**
 * https://github.com/oraclexing
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 */
public class BaseModel {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 修改时间
     */
    private Date updateAt;

    /**
     * 逻辑删除（0 :未删除 , 1:已经删除）
     */
    private Integer archive;

    public BaseModel() {}

    public BaseModel(Long id) {
        this.id = id;
    }

    public BaseModel(Long id, Date createAt, Date updateAt) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public BaseModel(Long id, Date createAt, Date updateAt, Integer archive) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.archive = archive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getArchive() {
        return archive;
    }

    public void setArchive(Integer archive) {
        this.archive = archive;
    }
}