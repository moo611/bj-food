package com.example.bjfood.service;

import com.example.bjfood.domain.SfUser;

import java.util.List;

/**
 * 用户Service接口
 *
 * 
 * @date 2024-10-19
 */
public interface ISfUserService
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
     * @param sfUser 用户
     * @return 用户集合
     */
    public List<SfUser> selectSfUserList(SfUser sfUser);

    /**
     * 新增用户
     *
     * @param sfUser 用户
     * @return 结果
     */
    public int insertSfUser(SfUser sfUser);

    /**
     * 修改用户
     *
     * @param sfUser 用户
     * @return 结果
     */
    public int updateSfUser(SfUser sfUser);

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的用户主键集合
     * @return 结果
     */
    public int deleteSfUserByIds(Long[] ids);

    /**
     * 删除用户信息
     *
     * @param id 用户主键
     * @return 结果
     */
    public int deleteSfUserById(Long id);

    SfUser selectRtUserByUsername(String username);
}
