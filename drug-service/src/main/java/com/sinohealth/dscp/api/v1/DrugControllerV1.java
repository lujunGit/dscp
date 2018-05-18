package com.sinohealth.dscp.api.v1;


import com.sinohealth.dscp.exception.JsonResult;
import com.sinohealth.dscp.exception.RespCode;
import com.sinohealth.dscp.service.DrugServiceV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/drug/v1")
@ResponseBody
public class DrugControllerV1 {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * springBoot规定无参构造方法必须要要
     */
    public DrugControllerV1() {
    }

    private DrugServiceV1 DrugServiceV1;

    @Autowired
    public DrugControllerV1(DrugServiceV1 DrugServiceV1) {
        this.DrugServiceV1 = DrugServiceV1;
    }

    /**
     * 获取我的频道
     */
    @GetMapping("/getMyDrugByName/{name}")
    public JsonResult getMyDrugByName(HttpServletRequest request, @PathVariable String name) {
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        jsonResult.setResultData(DrugServiceV1.getMyDrugByName(name));
        return jsonResult;
    }

    /**
     * 获取我的频道
     */
    @GetMapping("/getMyDrugById/{id}")
    public JsonResult getRandomDrug(HttpServletRequest request, @PathVariable Integer id) {
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        jsonResult.setResultData(DrugServiceV1.getMyDrugById(id));
        return jsonResult;
    }
}
