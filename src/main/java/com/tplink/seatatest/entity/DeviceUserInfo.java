package com.tplink.seatatest.entity;


import com.alibaba.fastjson.JSONObject;
import com.google.common.base.MoreObjects;
import org.json.JSONException;

import java.io.Serializable;

public class DeviceUserInfo implements Serializable {

    String deviceId;
    long ownerId;
    long userId;


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
        DeviceUserInfo build();
    }

    public static class Builder implements DeviceIdStep, OwnerIdStep, UserIdStep, BuildStep {
        private String deviceId;
        private long ownerId;
        private long userId;

        public Builder() {
        }

        public static DeviceIdStep deviceUserInfo() {
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
        public DeviceUserInfo build() {
            DeviceUserInfo deviceUserInfo = new DeviceUserInfo();
            deviceUserInfo.setDeviceId(this.deviceId);
            deviceUserInfo.setOwnerId(this.ownerId);
            deviceUserInfo.setUserId(this.userId);
            return deviceUserInfo;
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

    public static DeviceUserInfo convertFrom(JSONObject deviceUserJson) throws JSONException {
        return new Builder().withDeviceId(deviceUserJson.getString("deviceId")).withOwnerId(deviceUserJson.getLong("ownerId")).withUserId(deviceUserJson.getLong("userId")).build();
    }

    public NoSqlDeviceUserInfo toNoSqlDeviceUserInfo() {
        return new NoSqlDeviceUserInfo.Builder().withDeviceId(deviceId).withOwnerId(ownerId).withUserId(userId).build();
    }
}
