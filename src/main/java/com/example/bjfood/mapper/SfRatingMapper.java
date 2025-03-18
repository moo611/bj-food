package com.example.bjfood.mapper;

import java.util.Collection;
import java.util.List;
import com.example.bjfood.domain.SfRating;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评分Mapper接口
 *
 * @author ruoyi
 * @date 2025-03-11
 */
@Mapper
public interface SfRatingMapper
{
    /**
     * 查询评分
     *
     * @param id 评分主键
     * @return 评分
     */
    public SfRating selectSfRatingById(Long id);

    /**
     * 查询评分列表
     *
     * @param sfRating 评分
     * @return 评分集合
     */
    public List<SfRating> selectSfRatingList(SfRating sfRating);

    /**
     * 新增评分
     *
     * @param sfRating 评分
     * @return 结果
     */
    public int insertSfRating(SfRating sfRating);

    /**
     * 修改评分
     *
     * @param sfRating 评分
     * @return 结果
     */
    public int updateSfRating(SfRating sfRating);

    /**
     * 删除评分
     *
     * @param id 评分主键
     * @return 结果
     */
    public int deleteSfRatingById(Long id);

    /**
     * 批量删除评分
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSfRatingByIds(Long[] ids);

    List<SfRating> findRatingsByUsername(String username);

    List<Long> selectFoodIdsByUsername(String similarUser);

    SfRating selectSfRatingByUser(String username, Long foodId);

}
