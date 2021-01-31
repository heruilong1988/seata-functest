package com.tplink.seatatest.action.impl;

import com.alibaba.fastjson.JSON;
import com.tplink.seatatest.action.NoSqlDeviceUserAction;
import com.tplink.seatatest.dao.NoSqlDeviceUserDao;
import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.NoSqlDeviceUserInfo;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoSqlDeviceUserActionImpl implements NoSqlDeviceUserAction {

    Logger logger = LoggerFactory.getLogger(NoSqlDeviceUserActionImpl.class);

    @Autowired
    NoSqlDeviceUserDao noSqlDeviceUserDao;

    @Override
    public boolean prepareAddDeviceUser(BusinessActionContext actionContext, NoSqlDeviceUserInfo deviceUserInfo) {
        noSqlDeviceUserDao.save(deviceUserInfo);
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        //do nothing
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        NoSqlDeviceUserInfo deviceUserInfo = JSON.toJavaObject((JSON) actionContext.getActionContext("deviceUser"),NoSqlDeviceUserInfo.class);
        noSqlDeviceUserDao.delete(deviceUserInfo);
        return true;
    }
}
