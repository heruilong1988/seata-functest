package com.tplink.seatatest.dao;

import com.tplink.seatatest.entity.DeviceUserInfo;
import com.tplink.seatatest.entity.TxControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SqlDeviceUserDao {

    Logger logger = LoggerFactory.getLogger(SqlDeviceUserDao.class);


    @Autowired
    JdbcTemplate jdbcTemplate;

    public void save(DeviceUserInfo user) {
        String sql = "insert into seata.seata_func_test_device_user(device_id, owner_id, user_id) values(?,?,?)";
        jdbcTemplate.update(sql, user.getDeviceId(), user.getOwnerId(), user.getUserId());
        logger.info("save deviceUser:{}",user);
    }

    public void delete(DeviceUserInfo user) {
        String sql = "delete from seata.seata_func_test_device_user where device_id = ? and owner_id =? and user_id = ?";
        jdbcTemplate.update(sql, user.getDeviceId(), user.getOwnerId(), user.getUserId());
        logger.info("delete deviceUser:{}",user);

    }

    public DeviceUserInfo findOne(DeviceUserInfo userInfo) {
        try {
            return jdbcTemplate.queryForObject("select * from seata.seata_func_test_device_user where device_id = ? and owner_id =? and user_id = ?.", BeanPropertyRowMapper.newInstance(DeviceUserInfo.class), userInfo.getDeviceId(), userInfo.getOwnerId(), userInfo.getUserId());
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public TxControl findOne(TxControl txControl) {
        try {
            TxControl txControl1 =  jdbcTemplate.queryForObject("select * from seata.seata_func_test_tx_control where branch_id = ?;", BeanPropertyRowMapper.newInstance(TxControl.class), txControl.getBranchId());
            logger.info("findOne txControl:{}",txControl);
            return txControl1;
        }catch (EmptyResultDataAccessException e) {
            logger.info("findOne txControl: null");
            return null;
        }
    }

    public void save(TxControl txControl) {
        String sql = "insert into seata.seata_func_test_tx_control(global_transaction_id, branch_id, branch_tx_status) values(?,?,?)";
        jdbcTemplate.update(sql, txControl.getGlobalTransactionId(), txControl.getBranchId(), txControl.getBranchTxStatus());
        logger.info("save tx control:{}",txControl);
    }

    public void update(TxControl txControl) {
        String sql = "update seata.seata_func_test_tx_control set branch_tx_status = ? where branch_id = ?";
        jdbcTemplate.update(sql,txControl.getBranchTxStatus(), txControl.getBranchId());
        logger.info("update tx control:{}",txControl);
    }


    public void delete(TxControl txControl) {
        String sql = "delete from seata.seata_func_test_tx_control where branch_id = ?";
        jdbcTemplate.update(sql, txControl.getBranchId());
        logger.info("delete tx control:{}",txControl);

    }


}
