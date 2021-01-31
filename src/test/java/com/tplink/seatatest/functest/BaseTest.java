package com.tplink.seatatest.functest;

import com.tplink.seatatest.dao.NoSqlDeviceUserDao;
import com.tplink.seatatest.dao.SqlDeviceUserDao;
import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.NoSqlDeviceUserInfo;
import com.tplink.seatatest.service.DeviceUserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseTest {

    @Autowired
    SqlDeviceUserDao sqlDeviceUserDao;

    @Autowired
    NoSqlDeviceUserDao noSqlDeviceUserDao;

    @Autowired
    DeviceUserTransactionService deviceUserTransactionService;

    public void clean(DeviceUserInfo userInfo) {
        sqlDeviceUserDao.delete(userInfo);
        NoSqlDeviceUserInfo noSqlDeviceUserInfo = new NoSqlDeviceUserInfo.Builder().withDeviceId(userInfo.getDeviceId())
                .withOwnerId(userInfo.getOwnerId()).withUserId(userInfo.getUserId()).build();
        noSqlDeviceUserDao.delete(noSqlDeviceUserInfo);
    }
}
