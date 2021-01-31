package com.tplink.seatatest.action;

import com.tplink.seatatest.entity.DeviceUserInfo;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface SqlDeviceUserAction {

    /**
     * Prepare boolean.
     *
     * @param actionContext the action context
     * @param deviceUserInfo    deviceUserInfo
     * @return the boolean
     */
    @TwoPhaseBusinessAction(name = "prepareAddSqlDeviceUser" , commitMethod = "commit", rollbackMethod = "rollback")
    public boolean prepareAddDeviceUser(BusinessActionContext actionContext,
                                        @BusinessActionContextParameter(paramName = "deviceUser") DeviceUserInfo deviceUserInfo);
    /**
     * Commit boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    public boolean commit(BusinessActionContext actionContext);

    /**
     * Rollback boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    public boolean rollback(BusinessActionContext actionContext);
}
