package com.tplink.seatatest.dao;

import com.tplink.seatatest.action.impl.NoSqlDeviceUserActionImpl;
import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.NoSqlDeviceUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.CriteriaDefinition;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;

import static org.springframework.data.cassandra.core.query.Criteria.where;

@Repository
public class NoSqlDeviceUserDao {

    Logger logger = LoggerFactory.getLogger(NoSqlDeviceUserDao.class);

    @Autowired
    private CassandraOperations cassandraOperations;

    public void save(NoSqlDeviceUserInfo user) {
        cassandraOperations.insert(user);
        logger.info("save deviceUser:{}",user);

    }

    public void delete(NoSqlDeviceUserInfo user) {
        cassandraOperations.delete(user);
        logger.info("delete deviceUser:{}",user);

    }

    public NoSqlDeviceUserInfo findOne(NoSqlDeviceUserInfo deviceUserInfo) {
        return cassandraOperations.selectOne(Query.query(where("deviceId").is(deviceUserInfo.getDeviceId()),where("owner_id").is(deviceUserInfo.getOwnerId()),where("user_id").is(deviceUserInfo.getUserId())), NoSqlDeviceUserInfo.class);
    }

    public boolean exists(NoSqlDeviceUserInfo deviceUserInfo) {
        return cassandraOperations.exists(Query.query(where("deviceId").is(deviceUserInfo.getDeviceId()),where("owner_id").is(deviceUserInfo.getOwnerId()),where("user_id").is(deviceUserInfo.getUserId())), NoSqlDeviceUserInfo.class);
    }
}
