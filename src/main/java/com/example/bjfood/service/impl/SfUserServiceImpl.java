package com.example.bjfood.service.impl;

import com.example.bjfood.domain.SfUser;
import com.example.bjfood.mapper.SfUserMapper;
import com.example.bjfood.service.ISfUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户Service业务层处理
 *
 * 
 * @date 2024-10-19
 */
@Service
public class SfUserServiceImpl implements ISfUserService
{
    @Autowired
    private SfUserMapper sptUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;  // 注入 PasswordEncode
    /**
     * 查询用户
     *
     * @param id 用户主键
     * @return 用户
     */
    @Override
    public SfUser selectSfUserById(Long id)
    {
        return sptUserMapper.selectSfUserById(id);
    }

    /**
     * 查询用户列表
     *
     * @param sptUser 用户
     * @return 用户
     */
    @Override
    public List<SfUser> selectSfUserList(SfUser sptUser)
    {
        return sptUserMapper.selectSfUserList(sptUser);
    }

    /**
     * 新增用户
     *
     * @param sptUser 用户
     * @return 结果
     */
    @Override
    public int insertSfUser(SfUser sptUser)
    {
        SfUser old = sptUserMapper.selectSfUserByUsername(sptUser.getUsername());
        if (old != null){
            return -32001;
        }
        String encodedPassword = passwordEncoder.encode(sptUser.getPassword());
        sptUser.setPassword(encodedPassword);
        sptUser.setCreateTime(new Date());
        
        return sptUserMapper.insertSfUser(sptUser);
    }

    /**
     * 修改用户
     *
     * @param sptUser 用户
     * @return 结果
     */
    @Override
    public int updateSfUser(SfUser sptUser)
    {

        SfUser old = sptUserMapper.selectSfUserByUsername(sptUser.getUsername());
        if (!old.getId().equals(sptUser.getId())){
            return -32001;
        }

        sptUser.setUpdateTime(new Date());
        return sptUserMapper.updateSfUser(sptUser);
    }

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的用户主键
     * @return 结果
     */
    @Override
    public int deleteSfUserByIds(Long[] ids)
    {
        return sptUserMapper.deleteSfUserByIds(ids);
    }

    /**
     * 删除用户信息
     *
     * @param id 用户主键
     * @return 结果
     */
    @Override
    public int deleteSfUserById(Long id)
    {
        return sptUserMapper.deleteSfUserById(id);
    }

    @Override
    public SfUser selectRtUserByUsername(String username) {
        return sptUserMapper.selectSfUserByUsername(username);
    }
}
