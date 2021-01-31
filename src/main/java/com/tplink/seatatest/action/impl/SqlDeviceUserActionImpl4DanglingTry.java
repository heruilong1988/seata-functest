package com.tplink.seatatest.action.impl;

import com.alibaba.fastjson.JSON;
import com.tplink.seatatest.action.DanglingTryAction;
import com.tplink.seatatest.action.SqlDeviceUserAction;
import com.tplink.seatatest.dao.SqlDeviceUserDao;
import com.tplink.seatatest.entity.DeviceUserInfo;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("danglingTry")
public class SqlDeviceUserActionImpl4DanglingTry implements DanglingTryAction {

    Logger logger = LoggerFactory.getLogger(SqlDeviceUserActionImpl4DanglingTry.class);


    @Autowired
    SqlDeviceUserDao sqlDeviceUserDao;

    @Override
    public boolean prepareAddDeviceUser(BusinessActionContext actionContext, DeviceUserInfo deviceUserInfo) {
        //用新形成等待5秒后写入
        new Thread(() -> {
            try {
                logger.info("dangling try sleep 5秒");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sqlDeviceUserDao.save(deviceUserInfo);
        }).start();

        return false;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        //commit失败，导致事务回滚
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        DeviceUserInfo deviceUserInfo = JSON.toJavaObject((JSON) actionContext.getActionContext("deviceUser"),DeviceUserInfo.class);
        sqlDeviceUserDao.delete(deviceUserInfo);
        return true;
    }
}
