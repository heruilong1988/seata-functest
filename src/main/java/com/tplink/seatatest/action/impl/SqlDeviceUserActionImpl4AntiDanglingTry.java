package com.tplink.seatatest.action.impl;

import com.alibaba.fastjson.JSON;
import com.tplink.seatatest.action.AntiDanglingTryAction;
import com.tplink.seatatest.action.SqlDeviceUserAction;
import com.tplink.seatatest.dao.SqlDeviceUserDao;
import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.TxControl;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("antiDanglingTry")
public class SqlDeviceUserActionImpl4AntiDanglingTry implements AntiDanglingTryAction {

    Logger logger = LoggerFactory.getLogger(SqlDeviceUserActionImpl4AntiDanglingTry.class);

    @Autowired
    SqlDeviceUserDao sqlDeviceUserDao;

    @Override
    public boolean prepareAddDeviceUser(BusinessActionContext actionContext, DeviceUserInfo deviceUserInfo) {

        //用新形成等待5秒后写入
        new Thread(() -> {
            try {
                   logger.info("anti dangling try sleep 5秒");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //插入事务控制记录， 插入失败则不执行try
            try {
                String xid = actionContext.getXid();
                long branchId = actionContext.getBranchId();
                String branchTxStatus = "begin";
                TxControl txControl = new TxControl.Builder().globalTransactionId(xid).branchId(branchId).branchTxStatus(branchTxStatus).build();
                sqlDeviceUserDao.save(txControl);

                //插入事务记录
                sqlDeviceUserDao.save(deviceUserInfo);

            }catch (Exception e) {
                e.printStackTrace();
                logger.info("prepare stage: insert tx control error. skip prepare business");
            }

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
        logger.info("begin rollback==========================================");
        String xid = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        TxControl txControl = new TxControl.Builder().globalTransactionId(xid).branchId(branchId).build();

        TxControl txControl1 = sqlDeviceUserDao.findOne(txControl);

        if(txControl1 == null) {
            //放悬挂插入
            logger.info("rollback stage, tx controll null. add new TxControll record");
            txControl.setBranchTxStatus("rollback");
            sqlDeviceUserDao.save(txControl);
        }

        DeviceUserInfo deviceUserInfo = JSON.toJavaObject((JSON) actionContext.getActionContext("deviceUser"),DeviceUserInfo.class);
        sqlDeviceUserDao.delete(deviceUserInfo);
        return true;
    }
}
