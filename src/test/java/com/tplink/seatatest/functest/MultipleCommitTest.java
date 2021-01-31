package com.tplink.seatatest.functest;

import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.NoSqlDeviceUserInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MultipleCommitTest extends BaseTest{

    /**
     * 测试幂等，commit方法睡眠60秒
     * 查看日志会有多个commit重试
     *
     * 测试时需要在SqlDeviceUserActionImpl类的commit方法，抛出异常
     */
    @Test
    void multipleCommit() {
        String deviceId = "seata_func_test_multiple_commit";
        long ownerId = 1;
        long userId = 2;
        DeviceUserInfo deviceUserInfo = new DeviceUserInfo.Builder().withDeviceId(deviceId).withOwnerId(ownerId).withUserId(userId).build();

        //1.先清除数据库
        clean(deviceUserInfo);

       //2.执行事务
        try {
            deviceUserTransactionService.multipleCommit(deviceUserInfo);
        }catch (Exception e) {
            e.printStackTrace();

        }

        try {
            System.out.println("sleep 10s");
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }


        //3.判断结果
        DeviceUserInfo queryResult = sqlDeviceUserDao.findOne(deviceUserInfo);
        assertNotNull(queryResult);

        NoSqlDeviceUserInfo noSqlQueryResult = noSqlDeviceUserDao.findOne(deviceUserInfo.toNoSqlDeviceUserInfo());
        assertNotNull(noSqlQueryResult);

        //4.清理数据库
        clean(deviceUserInfo);
    }
}
