package com.example.bjfood.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bjfood.mapper.SfRatingMapper;
import com.example.bjfood.domain.SfRating;
import com.example.bjfood.service.ISfRatingService;

/**
 * 评分Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-11
 */
@Service
public class SfRatingServiceImpl implements ISfRatingService
{
    @Autowired
    private SfRatingMapper sfRatingMapper;

    /**
     * 查询评分
     *
     * @param id 评分主键
     * @return 评分
     */
    @Override
    public SfRating selectSfRatingById(Long id)
    {
        return sfRatingMapper.selectSfRatingById(id);
    }

    /**
     * 查询评分列表
     *
     * @param sfRating 评分
     * @return 评分
     */
    @Override
    public List<SfRating> selectSfRatingList(SfRating sfRating)
    {
        return sfRatingMapper.selectSfRatingList(sfRating);
    }

    /**
     * 新增评分
     *
     * @param sfRating 评分
     * @return 结果
     */
    @Override
    public int insertSfRating(SfRating sfRating)
    {

        SfRating old = sfRatingMapper.selectSfRatingByUser(sfRating.getUsername(),sfRating.getFoodId());
        if (old != null){
            return -32001;
        }
        sfRating.setCreateTime(new Date());
        return sfRatingMapper.insertSfRating(sfRating);
    }

    /**
     * 修改评分
     *
     * @param sfRating 评分
     * @return 结果
     */
    @Override
    public int updateSfRating(SfRating sfRating)
    {
        sfRating.setUpdateTime(new Date());
        return sfRatingMapper.updateSfRating(sfRating);
    }

    /**
     * 批量删除评分
     *
     * @param ids 需要删除的评分主键
     * @return 结果
     */
    @Override
    public int deleteSfRatingByIds(Long[] ids)
    {
        return sfRatingMapper.deleteSfRatingByIds(ids);
    }

    /**
     * 删除评分信息
     *
     * @param id 评分主键
     * @return 结果
     */
    @Override
    public int deleteSfRatingById(Long id)
    {
        return sfRatingMapper.deleteSfRatingById(id);
    }
}
