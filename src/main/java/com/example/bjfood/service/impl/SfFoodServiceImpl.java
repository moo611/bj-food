package com.example.bjfood.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.example.bjfood.config.auth.UserUtil;
import com.example.bjfood.domain.SfRating;
import com.example.bjfood.domain.SfUser;
import com.example.bjfood.mapper.SfRatingMapper;
import com.example.bjfood.mapper.SfUserMapper;
import com.example.bjfood.utils.CosineSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bjfood.mapper.SfFoodMapper;
import com.example.bjfood.domain.SfFood;
import com.example.bjfood.service.ISfFoodService;

/**
 * 美食Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-11
 */
@Service
public class SfFoodServiceImpl implements ISfFoodService {
    @Autowired
    private SfFoodMapper sfFoodMapper;
    @Autowired
    private SfUserMapper sfUserMapper;
    @Autowired
    private SfRatingMapper sfRatingMapper;

    /**
     * 查询美食
     *
     * @param id 美食主键
     * @return 美食
     */
    @Override
    public SfFood selectSfFoodById(Long id) {
        return sfFoodMapper.selectSfFoodById(id);
    }

    /**
     * 查询美食列表
     *
     * @param sfFood 美食
     * @return 美食
     */
    @Override
    public List<SfFood> selectSfFoodList(SfFood sfFood) {
        return sfFoodMapper.selectSfFoodList(sfFood);
    }

    /**
     * 新增美食
     *
     * @param sfFood 美食
     * @return 结果
     */
    @Override
    public int insertSfFood(SfFood sfFood) {
        sfFood.setCreateTime(new Date());
        return sfFoodMapper.insertSfFood(sfFood);
    }

    /**
     * 修改美食
     *
     * @param sfFood 美食
     * @return 结果
     */
    @Override
    public int updateSfFood(SfFood sfFood) {
        sfFood.setUpdateTime(new Date());
        return sfFoodMapper.updateSfFood(sfFood);
    }

    /**
     * 批量删除美食
     *
     * @param ids 需要删除的美食主键
     * @return 结果
     */
    @Override
    public int deleteSfFoodByIds(Long[] ids) {
        return sfFoodMapper.deleteSfFoodByIds(ids);
    }

    /**
     * 删除美食信息
     *
     * @param id 美食主键
     * @return 结果
     */
    @Override
    public int deleteSfFoodById(Long id) {
        return sfFoodMapper.deleteSfFoodById(id);
    }

    @Override
    public List<SfFood> getSimilarList(Long id) {


        SfFood targetSpot = sfFoodMapper.selectSfFoodById(id);
        List<SfFood> allSpots = sfFoodMapper.selectSfFoodList(null);


        return getSpotsBySimilarity(targetSpot, allSpots, 5);

    }

    /**
     * 相似推荐
     * @param targetSpot
     * @param allSpots
     * @param topN
     * @return
     */
    private List<SfFood> getSpotsBySimilarity(SfFood targetSpot, List<SfFood> allSpots, int topN) {

        Map<SfFood, Double> similarityMap = new HashMap<>();

        for (SfFood spot : allSpots) {
            if (!spot.getId().equals(targetSpot.getId())) {

                List<Integer> features1 = Arrays.stream(targetSpot.getFeatures().split(",")).map(Integer::parseInt).collect(Collectors.toList());
                List<Integer> features2 = Arrays.stream(spot.getFeatures().split(",")).map(Integer::parseInt).collect(Collectors.toList());
                double similarity = CosineSimilarity.calculateSimilarity(features1, features2);
                similarityMap.put(spot, similarity);
            }
        }

        // 按照相似度降序排序
        List<SfFood> res = similarityMap.entrySet()
                .stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(topN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        ;
        return res;

    }

    /**
     * 协同过滤
     * @return
     */
    @Override
    public List<SfFood> recommendFoods() {
        String username = UserUtil.getCurrentUsername();
        List<String> similarUsers = findSimilarSfUsers(username);
        Set<Long> recommendedFoodIds = new HashSet<>();

        for (String similarUser : similarUsers) {
            List<Long> foodIds = sfRatingMapper.selectFoodIdsByUsername(similarUser);
            recommendedFoodIds.addAll(foodIds);
        }
        if (recommendedFoodIds.isEmpty()){
            return Collections.emptyList();
        }
        return sfFoodMapper.selectSfFoodListByIds(recommendedFoodIds);
    }


    public double cosineSimilarity(List<Integer> userA, List<Integer> userB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < userA.size(); i++) {
            dotProduct += userA.get(i) * userB.get(i);
            normA += Math.pow(userA.get(i), 2);
            normB += Math.pow(userB.get(i), 2);
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public List<String> findSimilarSfUsers(String username) {
        List<String> similarSfUsers = new ArrayList<>();
        List<SfUser> users = sfUserMapper.selectSfUserList(null);

        SfUser currentSfUser = sfUserMapper.selectSfUserByUsername(username);
        if (currentSfUser == null) return similarSfUsers;

        Map<String, Double> similarityScores = new HashMap<>();
        for (SfUser user : users) {
            if (!user.getUsername().equals(username)) {
                double similarity = CosineSimilarity.calculateSimilarity(getUserRatings(username), getUserRatings(user.getUsername()));
                similarityScores.put(user.getUsername(), similarity);
            }
        }

        return similarityScores.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    public List<Integer> getUserRatings(String username) {
        // 获取所有美食ID，确保向量维度一致
        List<Long> allFoodIds = sfFoodMapper.selectSfFoodIds();

        // 获取该用户的评分数据（foodId -> rating）
        Map<Long, Integer> userRatings = sfRatingMapper.findRatingsByUsername(username)
                .stream()
                .collect(Collectors.toMap(SfRating::getFoodId, SfRating::getRating));

        // 生成评分列表，如果用户未评分则填充 0
        List<Integer> ratings = new ArrayList<>();
        for (Long foodId : allFoodIds) {
            ratings.add(userRatings.getOrDefault(foodId, 0));
        }

        return ratings;
    }

}
