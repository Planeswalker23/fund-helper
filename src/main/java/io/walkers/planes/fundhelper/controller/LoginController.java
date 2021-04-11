package io.walkers.planes.fundhelper.controller;

import io.walkers.planes.fundhelper.entity.model.VirtualUserModel;
import io.walkers.planes.fundhelper.entity.pojo.Response;
import io.walkers.planes.fundhelper.service.VirtualUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录控制层
 *
 * @author planeswalker23
 */
@RestController
public class LoginController {

    @Resource
    private VirtualUserService virtualUserService;

    @PostMapping("/login")
    public Response<VirtualUserModel> adminLogin(@Validated VirtualUserModel virtualUser) {
        return Response.success(virtualUserService.login(virtualUser));
    }
}
