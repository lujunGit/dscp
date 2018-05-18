package com.sinohealth.dscp.service;

import com.sinohealth.dscp.model.Drug;
import com.sinohealth.dscp.repository.DrugRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 在这里添加事务，最好在方法上面添加,避免全局事务影响读效率
 */
@Service
public class DrugServiceV1 {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DrugRepository DrugRepository;

    @Autowired
    public DrugServiceV1(DrugRepository DrugRepository) {
        this.DrugRepository = DrugRepository;
    }

    /**
     * 获取我的频道
     */
    public List<Drug> getMyDrugByName(String name) {
        return DrugRepository.findDrug(name);
    }

    /**
     * 获取我的频道
     */
    public Drug getMyDrugById(Integer id) {
        return DrugRepository.findOne(id);
    }

    /**
     * 新增频道
     * @param Drug
     */
    public void addDrug(Drug Drug) {
        DrugRepository.save(Drug);
    }

    public DrugRepository getDrugRepository() {
        return DrugRepository;
    }

    /**
     * 修改频道
     * 注意：删除了修改频道需要添加事务，新增和查询不需要
     * @param create_time
     * @param update_time
     * @param id
     * @return
     */
    @Transactional
    public Integer updateDrug(Date create_time, Date update_time, Integer id) {
        return DrugRepository.updateDrug(create_time, update_time, id);
    }
}
