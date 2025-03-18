package com.example.bjfood.controller;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.example.bjfood.domain.base.AjaxResult;
import com.example.bjfood.domain.base.R;
import com.example.bjfood.domain.req.SfFoodListReq;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bjfood.domain.SfFood;
import com.example.bjfood.service.ISfFoodService;


/**
 * 美食Controller
 *
 * @author ruoyi
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/food")
public class SfFoodController extends BaseController
{
    @Autowired
    private ISfFoodService sfFoodService;

    /**
     * 查询美食列表
     */
    
    @GetMapping("/list")
    public R list(SfFoodListReq sfFoodListReq)
    {
        PageHelper.startPage(sfFoodListReq.getPageNum(), sfFoodListReq.getPageSize());
        SfFood sfFood = new SfFood();
        BeanUtils.copyProperties(sfFoodListReq, sfFood);

        List<SfFood> sfFoods = sfFoodService.selectSfFoodList(sfFood);
        if (sfFoods.size() > 0) {
            PageInfo<SfFood> pageInfo = new PageInfo<>(sfFoods);
            return R.ok(pageInfo);
        }
        return R.ok(new PageInfo<SfFood>(Collections.emptyList()));
    }

    /**
     * 相似
     * @param id
     * @return
     */
    @GetMapping("/similar/{id}")
    public AjaxResult getSimilar(@PathVariable("id")Long id){

        return AjaxResult.success(sfFoodService.getSimilarList(id));

    }

    /**
     * 协同过滤
     * @param sfFoodListReq
     * @return
     */
    @GetMapping("/recommend")
    public AjaxResult recommend(SfFoodListReq sfFoodListReq){

        return AjaxResult.success(sfFoodService.recommendFoods());
    }


    /**
     * 获取美食详细信息
     */
    
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sfFoodService.selectSfFoodById(id));
    }

    /**
     * 新增美食
     */
    
    
    @PostMapping
    public AjaxResult add(@RequestBody SfFood sfFood)
    {
        return toAjax(sfFoodService.insertSfFood(sfFood));
    }

    /**
     * 修改美食
     */
    
    
    @PutMapping
    public AjaxResult edit(@RequestBody SfFood sfFood)
    {
        return toAjax(sfFoodService.updateSfFood(sfFood));
    }

    /**
     * 删除美食
     */
    
    
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sfFoodService.deleteSfFoodByIds(ids));
    }
}
