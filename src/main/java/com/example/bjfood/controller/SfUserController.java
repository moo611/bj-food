package com.example.bjfood.controller;

import com.example.bjfood.config.auth.JwtUtil;
import com.example.bjfood.config.auth.MyUserDetailsService;
import com.example.bjfood.config.auth.UserUtil;
import com.example.bjfood.domain.SfUser;
import com.example.bjfood.domain.base.AjaxResult;
import com.example.bjfood.domain.base.R;
import com.example.bjfood.domain.req.LoginReq;
import com.example.bjfood.domain.req.SfUserListReq;
import com.example.bjfood.service.ISfUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


/**
 * 用户Controller
 *
 * 
 * @date 2024-10-19
 */
@RestController
@RequestMapping("/user")
public class SfUserController extends BaseController {
    @Autowired
    private ISfUserService sfUserService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/login")
    @ApiOperation("登录")
    public AjaxResult login(@RequestBody LoginReq loginReq) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
            );
        }catch (Exception e){
            return AjaxResult.error("用户名或密码错误");
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginReq.getUsername());
        return AjaxResult.success("ok",jwtUtil.generateToken(userDetails.getUsername()));

    }


    /**
     * 查询用户列表
     */

    @GetMapping("/list")
    public R list(SfUserListReq sfUserListReq) {
        PageHelper.startPage(sfUserListReq.getPageNum(), sfUserListReq.getPageSize());
        SfUser sfUser = new SfUser();
        BeanUtils.copyProperties(sfUserListReq, sfUser);

        List<SfUser> sfUsers = sfUserService.selectSfUserList(sfUser);
        if (sfUsers.size() > 0) {
            PageInfo<SfUser> pageInfo = new PageInfo<>(sfUsers);
            return R.ok(pageInfo);
        }
        return R.ok(new PageInfo<SfUser>(Collections.emptyList()));
    }


    /**
     * 获取用户详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sfUserService.selectSfUserById(id));
    }

    /**
     * 获取用户详细信息
     */

    @GetMapping(value = "/info")
    @ApiOperation("获取用户详细信息")
    public AjaxResult getInfo()
    {
        String username = UserUtil.getCurrentUsername();
        return success(sfUserService.selectRtUserByUsername(username));
    }

    /**
     * 新增用户
     */


    @PostMapping
    public AjaxResult add(@RequestBody SfUser sfUser) {

        int res = sfUserService.insertSfUser(sfUser);
        if (res == -32001){
            return AjaxResult.error("用户名已存在");
        }
        return toAjax(res);


    }

    /**
     * 修改用户
     */


    @PutMapping
    public AjaxResult edit(@RequestBody SfUser sfUser) {
        int res = sfUserService.updateSfUser(sfUser);
        if (res == -32001){
            return AjaxResult.error("用户名已存在");
        }
        return toAjax(res);
    }

    /**
     * 删除用户
     */


    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sfUserService.deleteSfUserByIds(ids));
    }
}
