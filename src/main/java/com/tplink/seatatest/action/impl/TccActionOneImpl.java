package com.tplink.seatatest.action.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tplink.seatatest.action.ResultHolder;
import com.tplink.seatatest.action.TccActionOne;
import com.tplink.seatatest.entity.DeviceUserInfo;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import org.json.JSONException;
import org.springframework.stereotype.Component;

/**
 * The type Tcc action one.
 *
 * @author zhangsen
 */
@Component
public class TccActionOneImpl implements TccActionOne {


    @Override
    public boolean prepare(BusinessActionContext actionContext, int a, DeviceUserInfo deviceUserInfo) {
        String xid = actionContext.getXid();
        System.out.println("TccActionOne prepare, xid:" + xid);
        //return true;
        return false;

    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        System.out.println("TccActionOne commit, xid:" + xid);
        DeviceUserInfo deviceUserInfo = null;
            //deviceUserInfo = DeviceUserInfo.convertFrom((JSONObject) actionContext.getActionContext("deviceUser"));
        deviceUserInfo = JSON.toJavaObject((JSON) actionContext.getActionContext("deviceUser"),DeviceUserInfo.class);

        System.out.println("deviceUser:" + deviceUserInfo);
        ResultHolder.setActionOneResult(xid, "T");

        //throw new RuntimeException();
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa" + actionContext.getActionContext("a"));
        String xid = actionContext.getXid();
        System.out.println("TccActionOne rollback, xid:" + xid);
        ResultHolder.setActionOneResult(xid, "R");
        return true;
    }
}
