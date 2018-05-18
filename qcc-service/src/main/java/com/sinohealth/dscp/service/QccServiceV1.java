package com.sinohealth.dscp.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sinohealth.dscp.model.Qcc;
import com.sinohealth.dscp.repository.QccRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class QccServiceV1 {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestTemplate restTemplate;
    private QccRepository qccRepository;

    @Value("${spring.application.userUrl}")
    private String userUrl;

    @Autowired
    public QccServiceV1(@LoadBalanced RestTemplate restTemplate, QccRepository qccRepository) {
        this.restTemplate = restTemplate;
        this.qccRepository = qccRepository;
    }

    /**
     * 根据Id获取
     */
    public Qcc getQccsById(Integer id) {
        return qccRepository.getOne(id);
    }

    /**
     * 新增
     */
    public void addQcc(Qcc qcc) {
        qccRepository.save(qcc);
    }

    /**
     * 获取评论
     */
    public List<Qcc> getMyQccByText(String text) {
        return qccRepository.findQcc(text);
    }

    public QccRepository getQccRepository() {
        return qccRepository;
    }

    /**
     * 更新评论
     */
    @Transactional
    public Integer updateQcc(Date create_time, Date update_time, Integer id) {
        return qccRepository.updateQcc(create_time, update_time, id);
    }

    /**
     * 获取评论作者 如果get方法中有访问远程接口也是需要添加事务，并且配置文件中开放session,否则会出现懒加载问题
     */
    @HystrixCommand(ignoreExceptions = {IllegalStateException.class}, fallbackMethod = "getUserByQccIdError")
    @Transactional
    public Object getUserByQccId(Integer id) {
        Qcc Qcc = qccRepository.getOne(id);
        if (Qcc != null) {
            return restTemplate.getForObject(userUrl + Qcc.getUserId(), Object.class);
        } else {
            return null;
        }
    }

    /**
     * 在配置熔断方法时，熔断类的返回类型和入参个数必须都相同，并且添加忽略的异常，否组会直接进入到熔断方法
     * @param id
     * @return
     */
    public Object getUserByQccIdError(Integer id) {
        logger.error("方法【getUserByQccId】执行熔断处理，id=" + id);
        return null;
    }
}
