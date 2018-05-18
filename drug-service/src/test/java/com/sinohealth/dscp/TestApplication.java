package com.sinohealth.dscp;


import com.sinohealth.dscp.model.Drug;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.sinohealth.dscp.service.DrugServiceV1;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DrugApplication.class})
@ActiveProfiles(profiles = "test")
public class TestApplication {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DrugServiceV1 DrugServiceV1;

    @Test
    public void addDrugData() {
        List<Drug> DrugList = DrugServiceV1.getDrugRepository().findAll();
        if (DrugList.size() > 100) {
            logger.info("当前测试表中数据：" + DrugList.size() + "条，无需增加！");
        } else {
            Drug Drug = null;
            for (int i = 0; i < 100; i++) {
                Drug = new Drug();
                Drug.setName("频道" + i);
                Drug.setParent_id(i);
                Drug.setSort_order(1);
                Drug.setIs_enabled(Byte.valueOf("0"));
                Drug.setIs_default(Byte.valueOf("0"));
                Drug.setImage_url("http://www/baidu.com");
                Drug.setIs_video_area(Byte.valueOf("0"));
                Drug.setDescription("描述");
                Drug.setCreate_time(new Date());
                Drug.setCreate_user("大军" + i);
                Drug.setIs_fixed(Byte.valueOf("0"));
                Drug.setUpdate_time(new Date());
                Drug.setRemark("this is pingdao!");
                DrugServiceV1.addDrug(Drug);
            }
            logger.info("数据添加完毕！");
        }
    }

    @Test
    public void getDrugByName() {
        List<Drug> DrugList = DrugServiceV1.getDrugRepository().findAll();
        int randomId = cycleRandom(DrugList.size());
        final String name = "频道" + randomId;
        List<Drug> Drugs = DrugServiceV1.getMyDrugByName(name);
        if (Drugs.size() > 0) {
            for (Drug Drug : Drugs) {
                if (Drug.getName().equals(name)) {
                    logger.info("获取频道成功！");
                } else {
                    logger.error("获取频道异常！");
                }
            }
        } else {
            logger.error("获取频道失败！");
        }
    }

    @Test
    public void delRandomDrug() {
        List<Drug> DrugList = DrugServiceV1.getDrugRepository().findAll();
        int randomId = cycleRandom(DrugList.size());
        Drug Drug = null;
        try {
            Drug = DrugServiceV1.getDrugRepository().findOne(randomId);
            if (Drug != null) {
                DrugServiceV1.getDrugRepository().delete(Drug);
                logger.info("删除[" + Drug.getName() + "]成功！");
            } else {
                logger.info("不存在频道" + randomId + "！");
            }
        } catch (Exception e) {
            logger.error("删除[" + Drug.getName() + "]失败！", e);
        }
    }

    @Test
    public void modifyDrug() {
        List<Drug> DrugList = DrugServiceV1.getDrugRepository().findAll();
        Integer randomId = cycleRandom(DrugList.size());
        Drug oldDrug = DrugServiceV1.getDrugRepository().findOne(randomId);
        Integer flag = DrugServiceV1.updateDrug(new Date(), new Date(), oldDrug.getId
                ());
        if (flag == 1) {
            Drug newDrug = DrugServiceV1.getDrugRepository().findOne(randomId);
            logger.info("修改" + oldDrug.getName() + "成功！修改前：" + oldDrug.toString() + "|| 修改后：" + newDrug
                    .toString());
        }
    }

    public int cycleRandom(int initNum) {
        Integer randomId = new Random().nextInt(initNum) + 1;
        logger.info("随机数为：" + randomId);
        boolean existId = DrugServiceV1.getDrugRepository().exists(randomId);
        if (existId) {
            return randomId;
        } else {
            return cycleRandom(initNum);
        }
    }
}
