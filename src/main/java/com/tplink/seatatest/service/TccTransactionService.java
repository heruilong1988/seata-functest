package com.tplink.seatatest.service;

import com.tplink.seatatest.action.TccActionOne;
import com.tplink.seatatest.action.TccActionTwo;
import com.tplink.seatatest.entity.DeviceUserInfo;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * The type Tcc transaction service.
 *
 * @author zhangsen
 */
@Component
public class TccTransactionService {

    static long count = 0;
    private TccActionOne tccActionOne;

    private TccActionTwo tccActionTwo;

    public TccTransactionService(TccActionOne tccActionOne, TccActionTwo tccActionTwo) {
        this.tccActionOne = tccActionOne;
        this.tccActionTwo = tccActionTwo;
    }

    /**
     * 发起分布式事务
     *
     * @return string string
     */
    @GlobalTransactional
    public String doTransactionCommit(){
        DeviceUserInfo deviceUserInfo = new DeviceUserInfo.Builder().withDeviceId("dev2").withOwnerId(1).withUserId(2).build();
        //第一个TCC 事务参与者
        boolean result = tccActionOne.prepare(null, 1,deviceUserInfo);
        if(!result){
           // throw new RuntimeException("TccActionOne failed.");
        }

      /*  try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        result = tccActionTwo.prepare(null, "two");
        if(!result){
         //   throw new RuntimeException("TccActionTwo failed.");
        }
        return RootContext.getXID();
    }

    /**
     * Do transaction rollback string.
     *
     * @param map the map
     * @return the string
     */
    @GlobalTransactional
    public String doTransactionRollback(Map map){
        DeviceUserInfo deviceUserInfo = new DeviceUserInfo.Builder().withDeviceId("dev2").withOwnerId(1).withUserId(2).build();
        //第一个TCC 事务参与者
        boolean result = tccActionOne.prepare(null, 1,deviceUserInfo);
        if(!result){
            //throw new RuntimeException("TccActionOne failed.");
        }

        result = tccActionTwo.prepare(null, "two");
        if(!result){
            //throw new RuntimeException("TccActionTwo failed.");
        }
        map.put("xid", RootContext.getXID());
        //throw new RuntimeException("transacton rollback");
        return RootContext.getXID();
    }

    /**
     * Sets tcc action one.
     *
     * @param tccActionOne the tcc action one
     */
    public void setTccActionOne(TccActionOne tccActionOne) {
        this.tccActionOne = tccActionOne;
    }

    /**
     * Sets tcc action two.
     *
     * @param tccActionTwo the tcc action two
     */
    public void setTccActionTwo(TccActionTwo tccActionTwo) {
        this.tccActionTwo = tccActionTwo;
    }
}
