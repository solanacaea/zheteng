package org.zheteng.spring.beanfactory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zheteng.spring.beanfactory.dao.UserBeanDao;

@Component
public class UserBeanService {

    @Autowired
    private UserBeanDao dao;//通过代理动态生成

    public void query(String userid) {
        dao.query(userid);
    }

}
