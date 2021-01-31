package com.tplink.seatatest.action.impl;

import com.alibaba.fastjson.JSON;
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
@Qualifier("normal")
public class SqlDeviceUserActionImpl implements SqlDeviceUserAction {

    Logger logger = LoggerFactory.getLogger(SqlDeviceUserActionImpl.class);


    @Autowired
    SqlDeviceUserDao sqlDeviceUserDao;

    @Override
    public boolean prepareAddDeviceUser(BusinessActionContext actionContext, DeviceUserInfo deviceUserInfo) {
        sqlDeviceUserDao.save(deviceUserInfo);
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        DeviceUserInfo deviceUserInfo = JSON.toJavaObject((JSON) actionContext.getActionContext("deviceUser"),DeviceUserInfo.class);
        String deviceId = deviceUserInfo.getDeviceId();

      /*  //测试multiple commit时开启if语句
        if(deviceId.equals("seata_func_test_multiple_commit")) {
            throw new RuntimeException("failed to response commit");
        }*/
        //do nothing
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        DeviceUserInfo deviceUserInfo = JSON.toJavaObject((JSON) actionContext.getActionContext("deviceUser"),DeviceUserInfo.class);
        sqlDeviceUserDao.delete(deviceUserInfo);
        return true;
    }
}
