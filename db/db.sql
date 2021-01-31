CREATE TABLE IF NOT EXISTS `seata_func_test_device_user`(
   `device_id` VARCHAR(255) NOT NULL,
   `owner_id` bigint NOT NULL,
   `user_id` bigint,
   PRIMARY KEY (device_id,owner_id,user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `seata_func_test_tx_control`(
   `global_transaction_id` VARCHAR(255) NOT NULL,
   `branch_id` varchar(255) NOT NULL,
   `branch_tx_status` varchar(255),
   `gmt_create_time` datetime DEFAULT CURRENT_TIMESTAMP,
   `gmt_update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
   PRIMARY KEY (branch_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;