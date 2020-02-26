package org.zheteng.spring.beanfactory.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zheteng.spring.beanfactory.annotation.MyDao;

@MyDao
public interface UserBeanDao {

    @Query("from Users where userid = :userid")
    void query(@Param("userid") String userid);
}
