package com.tplink.seatatest.functest;

import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.NoSqlDeviceUserInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class TimeoutExceptionTest extends BaseTest{

    @Test
    void globalTxTimeout() {

        String deviceId = "seata_func_test_global_tx_timeout";
        long ownerId = 1;
        long userId = 2;
        DeviceUserInfo deviceUserInfo = new DeviceUserInfo.Builder().withDeviceId(deviceId).withOwnerId(ownerId).withUserId(userId).build();

        //1.清理
        clean(deviceUserInfo);

        //2.执行业务
        deviceUserTransactionService.globalTxTimeout(deviceUserInfo);

        //3.超时后回滚，判断结果
        DeviceUserInfo queryResult = sqlDeviceUserDao.findOne(deviceUserInfo);
        assertNull(queryResult);

        NoSqlDeviceUserInfo noSqlQueryResult = noSqlDeviceUserDao.findOne(deviceUserInfo.toNoSqlDeviceUserInfo());
        assertNull(noSqlQueryResult);


        //4. clean
        clean(deviceUserInfo);
    }

}
