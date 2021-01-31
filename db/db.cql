CREATE KEYSPACE seata WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

CREATE TABLE seata_func_test_device_user (
   device_id text ,
   owner_id bigint,
   user_id bigint,
   PRIMARY KEY (device_id,owner_id,user_id)
);