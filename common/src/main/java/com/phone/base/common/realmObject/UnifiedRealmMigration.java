package com.phone.base.common.realmObject;

import com.phone.base.common.utils.LogUtil;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/11/10
 */
public class UnifiedRealmMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm dynamicRealm, long oldVersion, long newVersion) {
        LogUtil.d("oldVersion ="+oldVersion+", newVersion="+newVersion);
//        if (oldVersion == 0L && newVersion == 1L){
//            RealmSchema schema = dynamicRealm.getSchema();
//            RealmObjectSchema deviceObjectSchema = schema.get("DeviceObject");
//            if (deviceObjectSchema == null) {
//                deviceObjectSchema = schema.create("DeviceObject");
//            }
//            deviceObjectSchema.addField("test",String.class);
//            oldVersion++;
//
//        }
//        if (oldVersion == 1L && newVersion == 2L){
//            RealmSchema schema = dynamicRealm.getSchema();
//            RealmObjectSchema deviceObjectSchema = schema.get("DeviceObject");
//            if (deviceObjectSchema == null) {
//                deviceObjectSchema = schema.create("DeviceObject");
//            }
//            deviceObjectSchema.addField("alltest",String.class);
//            oldVersion++;
//        }
//        if (oldVersion == 2L && newVersion == 3L){
//            RealmSchema schema = dynamicRealm.getSchema();
//            RealmObjectSchema deviceObjectSchema = schema.get("NewTestObject");
//            if (deviceObjectSchema == null) {
        //when new class ,need this
//                deviceObjectSchema = schema.create("NewTestObject");
//            }
//            deviceObjectSchema.addField("testId",String.class, FieldAttribute.PRIMARY_KEY);
//            oldVersion++;
//        }
    }
}
