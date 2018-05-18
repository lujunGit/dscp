package com.sinohealth.dscp.api.v1;

import com.sinohealth.dscp.exception.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qcc/v1")
@ResponseBody
public class QccController {

    public QccController() {
    }

    public JsonResult getQccById() {
        return null;
    }


}
