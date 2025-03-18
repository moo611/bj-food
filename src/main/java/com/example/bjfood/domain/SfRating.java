package com.example.bjfood.domain;


import com.example.bjfood.domain.base.BaseEntity;

/**
 * 评分对象 sf_rating
 *
 * @author ruoyi
 * @date 2025-03-11
 */
public class SfRating extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    
    private String username;

    /** $column.columnComment */
    
    private Long foodId;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /** $column.columnComment */
    
    private Integer rating;

    /** 删除标识 */
    private String delFlag;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
    public void setFoodId(Long foodId)
    {
        this.foodId = foodId;
    }

    public Long getFoodId()
    {
        return foodId;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    
}
