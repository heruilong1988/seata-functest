package com.tplink.seatatest.action.impl;

import com.tplink.seatatest.action.ResultHolder;
import com.tplink.seatatest.action.TccActionTwo;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.stereotype.Component;

/**
 * The type Tcc action two.
 *
 * @author zhangsen
 */
@Component
public class TccActionTwoImpl implements TccActionTwo {
    static  long count = 0;
    @Override
    public boolean prepare(BusinessActionContext actionContext, String b) {
        String xid = actionContext.getXid();
        System.out.println("TccActionTwo prepare, xid:" + xid);
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        System.out.println("TccActionTwo commit, xid:" + xid);
        System.out.println("count：=================================" + count++);
        ResultHolder.setActionTwoResult(xid, "T");
        //throw new RuntimeException();
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" + actionContext.getActionContext("b"));

        String xid = actionContext.getXid();
        System.out.println("TccActionTwo rollback, xid:" + xid);
        ResultHolder.setActionTwoResult(xid, "R");

        //throw new RuntimeException();
        return true;
    }

}
