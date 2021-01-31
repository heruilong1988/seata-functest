package com.tplink.seatatest.functest;

import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.NoSqlDeviceUserInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TCOneInstanceDownTest extends BaseTest{

    /**
     * 程序运行过程中关闭一台TC实例
     * 先不测
     */
    //@Test
    void tcOneInstanceDown() {

        List<DeviceUserInfo>  deviceUserInfos = new ArrayList<>();
        String deviceIdPrefix = "seata_func_test_tc_one_instance_down_%d";

        //1.先清除数据库
        for(int i = 0; i < 100; i++) {
            String deviceId = String.format(deviceIdPrefix,i);
            long ownerId = 1;
            long userId = 2;
            DeviceUserInfo deviceUserInfo = new DeviceUserInfo.Builder().withDeviceId(deviceId).withOwnerId(ownerId).withUserId(userId).build();
            clean(deviceUserInfo);
            deviceUserInfos.add(deviceUserInfo);
        }

        for(DeviceUserInfo deviceUserInfo : deviceUserInfos) {
            deviceUserTransactionService.tcInstanceDown(deviceUserInfo);
           /* try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

        //2.执行事务

        //3.判断结果
        for(DeviceUserInfo deviceUserInfo : deviceUserInfos) {
            DeviceUserInfo queryResult = sqlDeviceUserDao.findOne(deviceUserInfo);
            assertNotNull(queryResult);

            NoSqlDeviceUserInfo noSqlQueryResult = noSqlDeviceUserDao.findOne(deviceUserInfo.toNoSqlDeviceUserInfo());
            assertNotNull(noSqlQueryResult);
        }

        //4.清理数据库
        for(DeviceUserInfo deviceUserInfo : deviceUserInfos) {
            clean(deviceUserInfo);
        }
    }
}
