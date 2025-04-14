package com.example.bjfood.domain;

import com.example.bjfood.domain.base.BaseEntity;

import java.math.BigDecimal;


/**
 * 美食对象 sf_food
 *
 * @author ruoyi
 * @date 2025-03-11
 */
public class SfFood extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 类别 */
    
    private String category;

    /** $column.columnComment */
    
    private String name;

    /** $column.columnComment */
    
    private BigDecimal price;

    /** $column.columnComment */
    
    private String url;

    /** 特征向量 */
    
    private String features;

    /** 删除标识 */
    private String delFlag;


    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    private Double rating;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setFeatures(String features)
    {
        this.features = features;
    }

    public String getFeatures()
    {
        return features;
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
