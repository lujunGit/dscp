package com.sinohealth.dscp.api.v1;

import com.sinohealth.dscp.exception.JsonResult;
import com.sinohealth.dscp.exception.RespCode;
import com.sinohealth.dscp.model.User;
import com.sinohealth.dscp.service.UserServiceV1;
import com.sinohealth.dscp.util.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@ResponseBody
@RequestMapping("/user/v1")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController() {
    }

    private UserServiceV1 userServiceV1;

    @Autowired
    public UserController(UserServiceV1 userServiceV1) {
        this.userServiceV1 = userServiceV1;
    }

    /**
     * 获取用户
     *
     * @param request
     * @param id
     * @return
     */
    @GetMapping("/getUserById/{id}")
    public JsonResult getUserById(HttpServletRequest request, @PathVariable Integer id) {
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        //jsonResult.setResultData(userServiceV1.getUserById(id));
        jsonResult.setResultData(userServiceV1.getUserRepository().getById(id));
        return jsonResult;
    }

    /**
     * 根据账号密码获取用户
     *
     * @param request
     * @param loginName
     * @param loginPasswd
     * @return
     */
    @GetMapping("/getUserByNamePasswd/{loginName}/{loginPasswd}")
    public JsonResult getUserByNamePasswd(HttpServletRequest request, @PathVariable String loginName, @PathVariable String loginPasswd) {
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        User user = userServiceV1.getUserRepository().getByLoginNameAndLoginPasswd(loginName, EncryptUtil.md5Encode(loginPasswd));
        jsonResult.setRespCode(RespCode.SUCCESS);
        if (user == null) {
            jsonResult.setRespCode(RespCode.LOGIN_FAIL);
        }

        return jsonResult;
    }

}
