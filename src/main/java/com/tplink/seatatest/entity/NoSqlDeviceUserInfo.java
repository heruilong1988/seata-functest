package com.tplink.seatatest.entity;

import com.google.common.base.MoreObjects;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("seata_func_test_device_user")
public class NoSqlDeviceUserInfo {

    @PrimaryKeyColumn(value = "device_id",type = PrimaryKeyType.PARTITIONED)
    private String deviceId;

    @PrimaryKeyColumn("owner_id")
    private long ownerId;

    @PrimaryKeyColumn("user_id")
    private long userId;

    public NoSqlDeviceUserInfo(String deviceId, long ownerId, long userId) {
        this.deviceId = deviceId;
        this.ownerId = ownerId;
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static interface DeviceIdStep {
        OwnerIdStep withDeviceId(String deviceId);
    }

    public static interface OwnerIdStep {
        UserIdStep withOwnerId(long ownerId);
    }

    public static interface UserIdStep {
        BuildStep withUserId(long userId);
    }

    public static interface BuildStep {
        NoSqlDeviceUserInfo build();
    }


    public static class Builder implements DeviceIdStep, OwnerIdStep, UserIdStep, BuildStep {
        private String deviceId;
        private long ownerId;
        private long userId;

        public Builder() {
        }

        public static DeviceIdStep noSqlDeviceUserInfo() {
            return new Builder();
        }

        @Override
        public OwnerIdStep withDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        @Override
        public UserIdStep withOwnerId(long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        @Override
        public BuildStep withUserId(long userId) {
            this.userId = userId;
            return this;
        }

        @Override
        public NoSqlDeviceUserInfo build() {
            return new NoSqlDeviceUserInfo(
                    this.deviceId,
                    this.ownerId,
                    this.userId
            );
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("deviceId", deviceId)
                .add("ownerId", ownerId)
                .add("userId", userId)
                .toString();
    }
}
