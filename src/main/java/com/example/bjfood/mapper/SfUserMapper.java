package com.example.bjfood.mapper;

import com.example.bjfood.domain.SfUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户Mapper接口
 *
 * 
 * @date 2024-10-19
 */
@Mapper
public interface SfUserMapper
{
    /**
     * 查询用户
     *
     * @param id 用户主键
     * @return 用户
     */
    public SfUser selectSfUserById(Long id);

    /**
     * 查询用户列表
     *
     * @param sptUser 用户
     * @return 用户集合
     */
    public List<SfUser> selectSfUserList(SfUser sptUser);

    /**
     * 新增用户
     *
     * @param sptUser 用户
     * @return 结果
     */
    public int insertSfUser(SfUser sptUser);

    /**
     * 修改用户
     *
     * @param sptUser 用户
     * @return 结果
     */
    public int updateSfUser(SfUser sptUser);

    /**
     * 删除用户
     *
     * @param id 用户主键
     * @return 结果
     */
    public int deleteSfUserById(Long id);

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSfUserByIds(Long[] ids);

    SfUser selectSfUserByUsername(String username);

}
