package com.tplink.seatatest.functest;

import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.NoSqlDeviceUserInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DanglingTryTest extends BaseTest{

    @Test
    void danglingTryTest() {
        String deviceId = "seata_func_test_dangling_try";
        long ownerId = 1;
        long userId = 2;
        DeviceUserInfo deviceUserInfo = new DeviceUserInfo.Builder().withDeviceId(deviceId).withOwnerId(ownerId).withUserId(userId).build();

        //1.先清除数据库
        clean(deviceUserInfo);

        //2.执行事务,try会抛出异常,实际上写数据库是异步的，模拟try超时，实际上有执行
        try {
            deviceUserTransactionService.danglingTry(deviceUserInfo);
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //等待异步的try执行完成
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //3.判断结果
        DeviceUserInfo queryResult = sqlDeviceUserDao.findOne(deviceUserInfo);
        assertNotNull(queryResult);

        NoSqlDeviceUserInfo noSqlQueryResult = noSqlDeviceUserDao.findOne(deviceUserInfo.toNoSqlDeviceUserInfo());
        assertNull(noSqlQueryResult);

        //4.清理数据库
        clean(deviceUserInfo);
    }
}
