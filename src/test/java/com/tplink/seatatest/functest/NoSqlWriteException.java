package com.tplink.seatatest.functest;

import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.NoSqlDeviceUserInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class NoSqlWriteException extends BaseTest{
    @Test
    void noSqlWriteException() {

        String deviceId = "seata_func_test_nosql_write_exception";
        long ownerId = 1;
        long userId = 2;
        DeviceUserInfo deviceUserInfo = new DeviceUserInfo.Builder().withDeviceId(deviceId).withOwnerId(ownerId).withUserId(userId).build();

        //1.清理
        clean(deviceUserInfo);

        //2.执行业务
        try {
            deviceUserTransactionService.noSqlWriteException(deviceUserInfo);
        }catch (RuntimeException e) {
            e.printStackTrace();
        }

        //3.判断结果
        DeviceUserInfo queryResult = sqlDeviceUserDao.findOne(deviceUserInfo);
        assertNull(queryResult);

        NoSqlDeviceUserInfo noSqlQueryResult = noSqlDeviceUserDao.findOne(deviceUserInfo.toNoSqlDeviceUserInfo());
        assertNull(noSqlQueryResult);

        //4. clean
        clean(deviceUserInfo);
    }
}
