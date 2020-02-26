package org.zheteng.spring.aop.dao;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

@CommonsLog
@Component
public class UserDao {

    public void query() {
        log.info("query database...");
    }
}
