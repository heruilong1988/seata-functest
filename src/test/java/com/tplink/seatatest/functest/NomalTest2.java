package com.tplink.seatatest.functest;

import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.NoSqlDeviceUserInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NomalTest2 extends BaseTest{

    @Test
    void normal() {


        String deviceId = "seata_func_test_normal";
        long ownerId = 1;
        long userId = 2;
        DeviceUserInfo deviceUserInfo = new DeviceUserInfo.Builder().withDeviceId(deviceId).withOwnerId(ownerId).withUserId(userId).build();

        //1.先清除数据库
        clean(deviceUserInfo);

        //2.执行事务
        deviceUserTransactionService.addDeviceUser(deviceUserInfo);

        //3.判断结果
        DeviceUserInfo queryResult = sqlDeviceUserDao.findOne(deviceUserInfo);
        assertNotNull(queryResult);

        NoSqlDeviceUserInfo noSqlQueryResult = noSqlDeviceUserDao.findOne(deviceUserInfo.toNoSqlDeviceUserInfo());
        assertNotNull(noSqlQueryResult);

        //4.清理数据库
        clean(deviceUserInfo);
    }
}
