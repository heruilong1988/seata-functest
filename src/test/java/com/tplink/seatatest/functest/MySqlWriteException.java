package com.tplink.seatatest.functest;

import com.codahale.metrics.MetricRegistryListener;
import com.tplink.seatatest.dao.NoSqlDeviceUserDao;
import com.tplink.seatatest.dao.SqlDeviceUserDao;
import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.NoSqlDeviceUserInfo;
import com.tplink.seatatest.service.DeviceUserTransactionService;
import io.seata.tm.api.TransactionalExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MySqlWriteException extends BaseTest {

    @Test
    void sqlWriteException() {

        String deviceId = "seata_func_test_sql_write_exception";
        long ownerId = 1;
        long userId = 2;
        DeviceUserInfo deviceUserInfo = new DeviceUserInfo.Builder().withDeviceId(deviceId).withOwnerId(ownerId).withUserId(userId).build();

        //1.清理
        clean(deviceUserInfo);

        //2.执行业务
        try {
            deviceUserTransactionService.sqlWriteException(deviceUserInfo);
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
