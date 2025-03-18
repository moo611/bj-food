package com.example.bjfood.service;

import java.util.List;
import com.example.bjfood.domain.SfFood;

/**
 * 美食Service接口
 *
 * @author ruoyi
 * @date 2025-03-11
 */
public interface ISfFoodService
{
    /**
     * 查询美食
     *
     * @param id 美食主键
     * @return 美食
     */
    public SfFood selectSfFoodById(Long id);

    /**
     * 查询美食列表
     *
     * @param sfFood 美食
     * @return 美食集合
     */
    public List<SfFood> selectSfFoodList(SfFood sfFood);

    /**
     * 新增美食
     *
     * @param sfFood 美食
     * @return 结果
     */
    public int insertSfFood(SfFood sfFood);

    /**
     * 修改美食
     *
     * @param sfFood 美食
     * @return 结果
     */
    public int updateSfFood(SfFood sfFood);

    /**
     * 批量删除美食
     *
     * @param ids 需要删除的美食主键集合
     * @return 结果
     */
    public int deleteSfFoodByIds(Long[] ids);

    /**
     * 删除美食信息
     *
     * @param id 美食主键
     * @return 结果
     */
    public int deleteSfFoodById(Long id);

    List<SfFood> getSimilarList(Long id);

    List<SfFood> recommendFoods();

}
