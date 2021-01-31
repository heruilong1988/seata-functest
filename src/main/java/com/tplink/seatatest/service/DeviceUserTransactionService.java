package com.tplink.seatatest.service;

import com.tplink.seatatest.action.AntiDanglingTryAction;
import com.tplink.seatatest.action.DanglingTryAction;
import com.tplink.seatatest.action.NoSqlDeviceUserAction;
import com.tplink.seatatest.action.SqlDeviceUserAction;
import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.NoSqlDeviceUserInfo;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component

public class DeviceUserTransactionService {

    @Autowired
    @Qualifier("normal")
    SqlDeviceUserAction sqlDeviceUserAction;

    @Autowired
    NoSqlDeviceUserAction noSqlDeviceUserAction;

    @Autowired
    @Qualifier("danglingTry")
    DanglingTryAction sqlDeviceUserAction4DanglingTry;

    @Autowired
    @Qualifier("antiDanglingTry")
    AntiDanglingTryAction sqlDeviceUserAction4AntiDanglingTry;

    /**
     * 正常事务添加成功
     * @param deviceUserInfo
     */
    @GlobalTransactional
    public void addDeviceUser(DeviceUserInfo deviceUserInfo) {
        boolean result1 = sqlDeviceUserAction.prepareAddDeviceUser(null,deviceUserInfo);
        if(!result1) {
            throw new RuntimeException("sql failed");
        }

        NoSqlDeviceUserInfo noSqlDeviceUserInfo = new NoSqlDeviceUserInfo.Builder().withDeviceId(deviceUserInfo.getDeviceId())
                .withOwnerId(deviceUserInfo.getOwnerId()).withUserId(deviceUserInfo.getUserId()).build();
        boolean result2 = noSqlDeviceUserAction.prepareAddDeviceUser(null, noSqlDeviceUserInfo);
        if(!result2) {
            throw new RuntimeException("nosql failed");
        }
    }


    /**
     * 写sql异常，回滚
     * @param deviceUserInfo
     */
    @GlobalTransactional
    public void sqlWriteException(DeviceUserInfo deviceUserInfo) {
        boolean result1 = sqlDeviceUserAction.prepareAddDeviceUser(null,deviceUserInfo);
        if(result1) {
            //result返回true,抛出异常,模拟sql 异常
            throw new RuntimeException("sql failed");
        }

        NoSqlDeviceUserInfo noSqlDeviceUserInfo = new NoSqlDeviceUserInfo.Builder().withDeviceId(deviceUserInfo.getDeviceId())
                .withOwnerId(deviceUserInfo.getOwnerId()).withUserId(deviceUserInfo.getUserId()).build();
        boolean result2 = noSqlDeviceUserAction.prepareAddDeviceUser(null, noSqlDeviceUserInfo);
        if(!result2) {
            throw new RuntimeException("nosql failed");
        }
    }

    /**
     * 写nosql异常，回滚
     * @param deviceUserInfo
     */
    @GlobalTransactional
    public void noSqlWriteException(DeviceUserInfo deviceUserInfo) {
        boolean result1 = sqlDeviceUserAction.prepareAddDeviceUser(null,deviceUserInfo);
        if(!result1) {
            throw new RuntimeException("sql failed");
        }

        NoSqlDeviceUserInfo noSqlDeviceUserInfo = new NoSqlDeviceUserInfo.Builder().withDeviceId(deviceUserInfo.getDeviceId())
                .withOwnerId(deviceUserInfo.getOwnerId()).withUserId(deviceUserInfo.getUserId()).build();
        boolean result2 = noSqlDeviceUserAction.prepareAddDeviceUser(null, noSqlDeviceUserInfo);
        if(result2) {
            //result返回true,抛出异常,模拟sql 异常
            throw new RuntimeException("nosql failed");
        }
    }

    /**
     * 全局事务超时，回滚
     * @param deviceUserInfo
     */
    @GlobalTransactional
    public void globalTxTimeout(DeviceUserInfo deviceUserInfo) {
        boolean result1 = sqlDeviceUserAction.prepareAddDeviceUser(null,deviceUserInfo);
        if(!result1) {
            throw new RuntimeException("sql failed");
        }

        NoSqlDeviceUserInfo noSqlDeviceUserInfo = new NoSqlDeviceUserInfo.Builder().withDeviceId(deviceUserInfo.getDeviceId())
                .withOwnerId(deviceUserInfo.getOwnerId()).withUserId(deviceUserInfo.getUserId()).build();
        boolean result2 = noSqlDeviceUserAction.prepareAddDeviceUser(null, noSqlDeviceUserInfo);
        if(!result2) {
            //result返回true,抛出异常,模拟sql 异常
            throw new RuntimeException("nosql failed");
        }

        //全局事务默认60秒超时
        try {
            Thread.sleep(65000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 空回滚，回滚
     * @param deviceUserInfo
     */
    @GlobalTransactional
    public void rollbackWithoutTry(DeviceUserInfo deviceUserInfo) {

        //超时直接回滚，全局事务默认60秒超时
        try {
            Thread.sleep(65000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 正常事务添加成功
     * 遍历插入，没插入一次睡眠500ms，在插入的过程中关闭一个tc实例，
     * 测试运行完后，数据库应该有一样多的数据
     */
    @GlobalTransactional
    public void tcInstanceDown(DeviceUserInfo deviceUserInfo) {

        boolean result1 = sqlDeviceUserAction.prepareAddDeviceUser(null, deviceUserInfo);
        if (!result1) {
            throw new RuntimeException("sql failed");
        }

        NoSqlDeviceUserInfo noSqlDeviceUserInfo = new NoSqlDeviceUserInfo.Builder().withDeviceId(deviceUserInfo.getDeviceId())
                .withOwnerId(deviceUserInfo.getOwnerId()).withUserId(deviceUserInfo.getUserId()).build();
        boolean result2 = noSqlDeviceUserAction.prepareAddDeviceUser(null, noSqlDeviceUserInfo);
        if (!result2) {
            throw new RuntimeException("nosql failed");
        }
    }


    /**
     * 测试幂等，commit方法睡眠60秒
     * @param deviceUserInfo
     */
    @GlobalTransactional
    public void multipleCommit(DeviceUserInfo deviceUserInfo) {

        boolean result1 = sqlDeviceUserAction.prepareAddDeviceUser(null,deviceUserInfo);
        if(!result1) {
            throw new RuntimeException("sql failed");
        }

        NoSqlDeviceUserInfo noSqlDeviceUserInfo = new NoSqlDeviceUserInfo.Builder().withDeviceId(deviceUserInfo.getDeviceId())
                .withOwnerId(deviceUserInfo.getOwnerId()).withUserId(deviceUserInfo.getUserId()).build();

        boolean result2 = noSqlDeviceUserAction.prepareAddDeviceUser(null, noSqlDeviceUserInfo);
        if(!result2) {
            throw new RuntimeException("nosql failed");
        }
    }

    /**
     * 正常事务添加成功
     * @param deviceUserInfo
     */
    @GlobalTransactional
    public void danglingTry(DeviceUserInfo deviceUserInfo) {
        boolean result1 = sqlDeviceUserAction4DanglingTry.prepareAddDeviceUser(null,deviceUserInfo);
        if(!result1) {
            throw new RuntimeException("sql failed");
        }

        NoSqlDeviceUserInfo noSqlDeviceUserInfo = new NoSqlDeviceUserInfo.Builder().withDeviceId(deviceUserInfo.getDeviceId())
                .withOwnerId(deviceUserInfo.getOwnerId()).withUserId(deviceUserInfo.getUserId()).build();
        boolean result2 = noSqlDeviceUserAction.prepareAddDeviceUser(null, noSqlDeviceUserInfo);
        if(!result2) {
            throw new RuntimeException("nosql failed");
        }
    }

    /**
     * 正常事务添加成功
     * @param deviceUserInfo
     */
    @GlobalTransactional
    public void antiDanglingTry(DeviceUserInfo deviceUserInfo) {
        boolean result1 = sqlDeviceUserAction4AntiDanglingTry.prepareAddDeviceUser(null,deviceUserInfo);
        if(!result1) {
            throw new RuntimeException("sql failed");
        }

        NoSqlDeviceUserInfo noSqlDeviceUserInfo = new NoSqlDeviceUserInfo.Builder().withDeviceId(deviceUserInfo.getDeviceId())
                .withOwnerId(deviceUserInfo.getOwnerId()).withUserId(deviceUserInfo.getUserId()).build();
        boolean result2 = noSqlDeviceUserAction.prepareAddDeviceUser(null, noSqlDeviceUserInfo);
        if(!result2) {
            throw new RuntimeException("nosql failed");
        }
    }
}
