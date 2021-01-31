package com.tplink.seatatest.functest;

import com.codahale.metrics.MetricRegistryListener;
import com.tplink.seatatest.dao.NoSqlDeviceUserDao;
import com.tplink.seatatest.dao.SqlDeviceUserDao;
import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.NoSqlDeviceUserInfo;
import com.tplink.seatatest.service.DeviceUserTransactionService;
import com.tplink.seatatest.service.TccTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class NormalTest extends BaseTest {

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


    //@Test
    void insertDevUser() {
        DeviceUserInfo deviceUserInfo = new DeviceUserInfo();
        deviceUserInfo.setDeviceId("dev1");
        deviceUserInfo.setOwnerId(1);
        deviceUserInfo.setUserId(2);

        sqlDeviceUserDao.save(deviceUserInfo);

    }

    //@Test
    void deleteDevUser() {
        DeviceUserInfo deviceUserInfo = new DeviceUserInfo();
        deviceUserInfo.setDeviceId("dev1");
        deviceUserInfo.setOwnerId(1);
        deviceUserInfo.setUserId(2);

        sqlDeviceUserDao.delete(deviceUserInfo);
    }

   // @Test
    void insertNoSqlDevUser() {
        String deviceId = "dev1";
        long ownerId = 1;
        long userId = 2;
        NoSqlDeviceUserInfo deviceUserInfo = new NoSqlDeviceUserInfo.Builder().withDeviceId(deviceId).withOwnerId(ownerId).withUserId(userId).build();
        noSqlDeviceUserDao.save(deviceUserInfo);
    }

   // @Test
    void deleteNoSqlDevUser() {
        String deviceId = "dev1";
        long ownerId = 1;
        long userId = 2;
        NoSqlDeviceUserInfo deviceUserInfo = new NoSqlDeviceUserInfo.Builder().withDeviceId(deviceId).withOwnerId(ownerId).withUserId(userId).build();
        noSqlDeviceUserDao.delete(deviceUserInfo);
    }

   // @Test
    void selectSqlDeviceUser() {
        String deviceId = "dev_normal";
        long ownerId = 1;
        long userId = 2;
        DeviceUserInfo query = new DeviceUserInfo.Builder().withDeviceId(deviceId).withOwnerId(ownerId).withUserId(userId).build();
        DeviceUserInfo deviceUserInfo = sqlDeviceUserDao.findOne(query);
        System.out.println(deviceUserInfo);

    }
}
