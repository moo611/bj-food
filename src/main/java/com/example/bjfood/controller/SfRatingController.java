package com.example.bjfood.controller;

import java.util.Collections;
import java.util.List;

import com.example.bjfood.domain.SfRating;
import com.example.bjfood.domain.base.AjaxResult;
import com.example.bjfood.domain.base.R;
import com.example.bjfood.domain.req.SfRatingListReq;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bjfood.service.ISfRatingService;


/**
 * 评分Controller
 *
 * @author ruoyi
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/rating")
public class SfRatingController extends BaseController
{
    @Autowired
    private ISfRatingService sfRatingService;

    /**
     * 查询评分列表
     */
    
    @GetMapping("/list")
    public R list(SfRatingListReq sfRatingListReq)
    {
        PageHelper.startPage(sfRatingListReq.getPageNum(), sfRatingListReq.getPageSize());
        SfRating sfRating = new SfRating();
        BeanUtils.copyProperties(sfRatingListReq, sfRating);

        List<SfRating> sfRatings = sfRatingService.selectSfRatingList(sfRating);
        if (sfRatings.size() > 0) {
            PageInfo<SfRating> pageInfo = new PageInfo<>(sfRatings);
            return R.ok(pageInfo);
        }
        return R.ok(new PageInfo<SfRating>(Collections.emptyList()));
    }


    /**
     * 获取评分详细信息
     */
    
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sfRatingService.selectSfRatingById(id));
    }

    /**
     * 新增评分
     */
    
    
    @PostMapping
    public AjaxResult add(@RequestBody SfRating sfRating)
    {
        int rows = sfRatingService.insertSfRating(sfRating);
        if (rows == -32001){
            return AjaxResult.error("您已经评价过了");
        }
        return toAjax(rows);
    }

    /**
     * 修改评分
     */
    
    
    @PutMapping
    public AjaxResult edit(@RequestBody SfRating sfRating)
    {
        return toAjax(sfRatingService.updateSfRating(sfRating));
    }

    /**
     * 删除评分
     */
    
    
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sfRatingService.deleteSfRatingByIds(ids));
    }
}
