package io.walkers.planes.fundhelper.service;

import io.walkers.planes.fundhelper.dao.VirtualUserDao;
import io.walkers.planes.fundhelper.entity.dict.MessageDict;
import io.walkers.planes.fundhelper.entity.model.VirtualUserModel;
import io.walkers.planes.fundhelper.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * User 服务层
 *
 * @author planeswalker23
 */
@Slf4j
@Service
public class VirtualUserService {
    @Resource
    private VirtualUserDao virtualUserDao;

    /**
     * 登录
     *
     * @param virtualUser 待登录用户
     * @return VirtualUserModel
     */
    public VirtualUserModel login(VirtualUserModel virtualUser) {
        VirtualUserModel userResult = virtualUserDao.selectByAccount(virtualUser.getAccount());
        if (userResult == null) {
            this.createVirtualUser(virtualUser);
            userResult = virtualUser;
        }
        if (!userResult.getPassword().equals(virtualUser.getPassword())) {
            throw new RuntimeException(MessageDict.WRONG_PASSWORD);
        }
        // 基于 session 保存登录状态
        SessionUtil.updateAttribute("loginUser", userResult);
        log.info("用户{{}}登录成功", userResult.getAccount());
        return userResult;
    }

    /**
     * 创建用户
     *
     * @param virtualUser 用户
     */
    public void createVirtualUser(VirtualUserModel virtualUser) {
        virtualUserDao.insert(virtualUser);
    }
}
