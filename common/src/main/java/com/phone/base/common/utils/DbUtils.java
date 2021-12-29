package com.phone.base.common.utils;

import static com.phone.base.common.AutomationConstants.AUTO_ACTION_TYPE_DISABLE;
import static com.phone.base.common.AutomationConstants.AUTO_ACTION_TYPE_OCCUPIED;
import static com.phone.base.common.AutomationConstants.AUTO_ACTION_TYPE_UN_OCCUPIED;
import static com.phone.base.common.AutomationConstants.AUTO_STANDARD;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.phone.base.common.CustomTypes;
import com.phone.base.common.InputTypes;
import com.phone.base.common.RequestActions;
import com.phone.base.common.ResponseResult;
import com.phone.base.common.data.bean.AutoSingleStateBean;
import com.phone.base.common.data.bean.FixtureAddressesResponse;
import com.phone.base.common.data.bean.FixtureConfigureResponse;
import com.phone.base.common.data.bean.FixtureDetail;
import com.phone.base.common.data.bean.FixtureListReadResponse;
import com.phone.base.common.data.bean.FixtureState;
import com.phone.base.common.data.bean.FixtureTune;
import com.phone.base.common.data.bean.FixturesReadBean;
import com.phone.base.common.data.bean.GroupListReadResponse;
import com.phone.base.common.data.bean.GroupReadResponse;
import com.phone.base.common.data.bean.GroupsReadBean;
import com.phone.base.common.data.source.BaseDataSource;
import com.phone.base.common.manager.BrainManager;
import com.phone.base.common.network.ApiFactory;
import com.phone.base.common.realmObject.AutomationObject;
import com.phone.base.common.realmObject.AutomationShadow;
import com.phone.base.common.realmObject.DeviceObject;
import com.phone.base.common.realmObject.DmxChannelObject;
import com.phone.base.common.realmObject.DmxModuleObject;
import com.phone.base.common.realmObject.DmxObject;
import com.phone.base.common.realmObject.FixtureObject;
import com.phone.base.common.realmObject.FixtureShadow;
import com.phone.base.common.realmObject.GroupObject;
import com.phone.base.common.realmObject.InputObject;
import com.phone.base.common.realmObject.ScheduleObject;
import com.phone.base.common.realmObject.SensorObject;
import com.phone.base.common.realmObject.SmartSocketObject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DbUtils {
    private static final String TAG = "DbUtils";

    public static void updateFixtureShadow(int addr, JsonObject shadowObj) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            shadowObj.addProperty("addr", addr);
            realm.createOrUpdateObjectFromJson(FixtureShadow.class, shadowObj.toString());
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyFixtureName(int addr, String name) {
        Realm realm = Realm.getDefaultInstance();
        try {
            FixtureObject fixtureObject = realm.where(FixtureObject.class).equalTo("addr", addr).findFirst();
            if (null != fixtureObject) {
                if (!realm.isInTransaction()) {
                    realm.beginTransaction();
                }
                fixtureObject.setName(name);
                realm.copyToRealmOrUpdate(fixtureObject);
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void deleteFixture(int addr) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            FixtureObject fixtureObject = realm.where(FixtureObject.class).equalTo("addr", addr).findFirst();

            if (null != fixtureObject) {
                //auto delete FixtureObject in GroupObject
                fixtureObject.deleteFromRealm();
            }
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void saveFixturesListToRealm(FixtureListReadResponse fixtureReadResponse) {
        if (fixtureReadResponse.getFixture() != null) {
            ArrayList<FixturesReadBean> fixture = new ArrayList<>(fixtureReadResponse.getFixture());
            if (fixture != null && fixture.size() > 0) {
                for (FixturesReadBean fixturesReadBean : fixture) {
                    Realm realm = Realm.getDefaultInstance();
                    try {
                        FixtureShadow fixtureShadow = new FixtureShadow(fixturesReadBean.getAddr());
                        if (fixturesReadBean.getAddr() < 20000000) {
                            // mean it is a SCM addr
                            FixtureDetail detail = fixturesReadBean.getDetail();
                            if (detail != null && !TextUtils.isEmpty(detail.getFwVer())) {
                                DeviceObjectUtils.INSTANCE.setScmVer(detail.getFwVer());
                            }
                        } else {
                            //only not the scm , the fixture could save state and tune
                            FixtureState fixtureState = fixturesReadBean.getState();
                            FixtureTune fixtureTune = fixturesReadBean.getTune();
                            if (fixtureState != null) {
                                fixtureShadow.setFixtureState(fixtureState);
                            }
                            if (fixtureTune != null) {
                                fixtureShadow.setFixtureTune(fixtureTune);
                            }
                        }
                        //all the scm and fixture need to save detail
                        FixtureDetail fixtureDetail = fixturesReadBean.getDetail();
                        if (fixtureDetail != null) {
                            fixtureShadow.setFixtureDetails(fixtureDetail);
                        }
                        FixtureObject fixtureObject = new FixtureObject(fixtureReadResponse.getAction(), fixtureReadResponse.getResult(), fixturesReadBean.getAddr(),
                                fixturesReadBean.getName(), fixturesReadBean.getType(), fixtureShadow);

                        if (!realm.isInTransaction()) {
                            realm.beginTransaction();
                        }
                        realm.copyToRealmOrUpdate(fixtureObject);
                        if (realm.isInTransaction()) {
                            realm.commitTransaction();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

//    public static void setFixtureListOriginalStatus() {
//        Realm realm = Realm.getDefaultInstance();
//        try {
//            RealmResults<ControllerObject> controllerObjects = realm.where(ControllerObject.class).findAll();
//            if (controllerObjects != null && controllerObjects.size() > 0) {
//                if (!realm.isInTransaction()) {
//                    realm.beginTransaction();
//                }
//                for (ControllerObject controllerObject : controllerObjects) {
//                    controllerObject.setCanControl(false);
//                    realm.copyToRealmOrUpdate(controllerObject);
//                }
//                realm.commitTransaction();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (!realm.isClosed()) {
//                realm.close();
//            }
//        }
//    }

    public static void saveGroupDevicesList(int addr, GroupReadResponse groupReadResponse) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            GroupObject groupObject = realm.where(GroupObject.class).equalTo("addr", addr).findFirst();
            if (groupObject == null) {
                groupObject = new GroupObject();
            }
            groupObject.setAddr(addr);
            groupObject.setName(groupReadResponse.getName());
            groupObject.setResult(groupReadResponse.getResult());
            groupObject.setAction(groupReadResponse.getAction());

            List<Integer> fixturesList = groupReadResponse.getFixture();
            if (fixturesList != null && fixturesList.size() > 0) {
                RealmList<FixtureObject> newFixturesList = new RealmList<>();
                for (Integer fixtureAddr : fixturesList) {
                    FixtureObject fixtureObject = realm.where(FixtureObject.class).equalTo("addr", fixtureAddr).findFirst();
                    //for refresh logic,avoid local Group data not have this fixture
                    if (fixtureObject != null) {
                        newFixturesList.add(fixtureObject);
                    }
                }
                groupObject.setListFixtures(newFixturesList);
            }
            realm.copyToRealmOrUpdate(groupObject);
            if (realm.isInTransaction()) {
                realm.commitTransaction();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void saveGroupListDevicesList(GroupListReadResponse groupReadResponse) {
        if (groupReadResponse.getGroup() != null && groupReadResponse.getGroup().size() > 0) {
            ArrayList<GroupsReadBean> group = new ArrayList<>(groupReadResponse.getGroup());
            for (GroupsReadBean groupsReadBean : group) {
                Realm realm = Realm.getDefaultInstance();
                try {
                    if (!realm.isInTransaction()) {
                        realm.beginTransaction();
                    }
                    GroupObject groupObject = realm.where(GroupObject.class).equalTo("addr", groupsReadBean.getAddr()).findFirst();
                    if (groupObject == null) {
                        groupObject = new GroupObject();
                    }
                    groupObject.setAddr(groupsReadBean.getAddr());
                    groupObject.setName(groupsReadBean.getName());
                    groupObject.setResult(groupReadResponse.getResult());
                    groupObject.setAction(groupReadResponse.getAction());

                    List<Integer> fixturesList = groupsReadBean.getFixture();
                    if (fixturesList != null && fixturesList.size() > 0) {
                        RealmList<FixtureObject> newFixturesList = new RealmList<>();
                        for (Integer fixtureAddr : fixturesList) {
                            FixtureObject fixtureObject = realm.where(FixtureObject.class).equalTo("addr", fixtureAddr).findFirst();
                            //for refresh logic,avoid local Group data not have this fixture
                            if (fixtureObject != null) {
                                newFixturesList.add(fixtureObject);
                            }
                        }
                        groupObject.setListFixtures(newFixturesList);
                    }
                    realm.copyToRealmOrUpdate(groupObject);
                    if (realm.isInTransaction()) {
                        realm.commitTransaction();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (!realm.isClosed()) {
                        realm.close();
                    }
                }
            }
        }

    }

    public static void deleteAllGroups() {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            realm.delete(GroupObject.class);
            if (realm.isInTransaction()) {
                realm.commitTransaction();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void saveANewGroupInfo(String groupName, int addr, List<Integer> fixturesList, boolean isFavorite, String bgName) {
        Realm realm = Realm.getDefaultInstance();
        try {
            GroupObject groupObject = new GroupObject(RequestActions.GROUP_CREATE, ResponseResult.RESULT_SUCCESS, addr, groupName);
            if (fixturesList != null && fixturesList.size() > 0) {
                RealmList<FixtureObject> newFixturesList = new RealmList<>();
                for (Integer fixtureAddr : fixturesList) {
                    FixtureObject fixtureObject = realm.where(FixtureObject.class).equalTo("addr", fixtureAddr).findFirst();
                    newFixturesList.add(fixtureObject);
                }
                groupObject.setListFixtures(newFixturesList);
            }
            groupObject.setFavorite(isFavorite);
            if (!TextUtils.isEmpty(bgName)) {
                groupObject.setBgName(bgName);
            }
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            realm.copyToRealmOrUpdate(groupObject);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void updateGroupInfo(String groupName, int addr, List<Integer> fixturesList, @Nullable Boolean isFavorite, @Nullable String bgName) {
        Realm realm = Realm.getDefaultInstance();
        GroupObject groupObject = realm.where(GroupObject.class).equalTo("addr", addr).findFirst();
        if (groupObject != null) {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            if (isFavorite != null) {
                groupObject.setFavorite(isFavorite);
            }
            if (!TextUtils.isEmpty(bgName)) {
                groupObject.setBgName(bgName);
            }
            groupObject.setName(groupName);
            if (fixturesList != null && fixturesList.size() > 0) {
                RealmList<FixtureObject> newFixturesList = new RealmList<>();
                for (Integer fixtureAddr : fixturesList) {
                    FixtureObject fixtureObject = realm.where(FixtureObject.class).equalTo("addr", fixtureAddr).findFirst();
                    newFixturesList.add(fixtureObject);
                }
                groupObject.setListFixtures(newFixturesList);
//                for (Integer fixtureAddr : fixturesList) {
//                    FixtureObject fixtureObject = realm.where(FixtureObject.class).equalTo("addr", fixtureAddr).findFirst();
//                    groupObject.getListFixtures().add(fixtureObject);
//                }
            }
            realm.copyToRealmOrUpdate(groupObject);
            realm.commitTransaction();
        }
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public static void deleteGroup(int address) {
        Realm realm = Realm.getDefaultInstance();
        try {
            GroupObject groupObject = realm.where(GroupObject.class).equalTo("addr", address).findFirst();
            if (groupObject != null) {
                if (!realm.isInTransaction()) {
                    realm.beginTransaction();
                }
                groupObject.deleteFromRealm();
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void updateGroupControlState(int groupAddr, JsonObject stateObj) {
        if (stateObj == null) {
            return;
        }
        Realm realm = Realm.getDefaultInstance();
        try {
            GroupObject groupObject = realm.where(GroupObject.class).equalTo("addr", groupAddr).findFirst();
            if (groupObject != null && groupObject.getListFixtures() != null && groupObject.getListFixtures().size() > 0) {
                if (!realm.isInTransaction()) {
                    realm.beginTransaction();
                }
                for (FixtureObject fixObj : groupObject.getListFixtures()) {
                    //update the fixture shadow
                    stateObj.addProperty("addr", fixObj.getShadow().getAddr());
                    realm.createOrUpdateObjectFromJson(FixtureShadow.class, stateObj.toString());
                }
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getFixtureGroupNum(String fixture_addr) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<GroupObject> groupObjects = realm.where(GroupObject.class).findAll();
        int group_members = 0;
        if (groupObjects != null && groupObjects.size() > 0) {
            for (GroupObject groupObject : groupObjects) {
                if (groupObject != null) {
                    GroupObject groupObject_copy = realm.copyFromRealm(groupObject);
                    RealmList<FixtureObject> listFixtures = groupObject_copy.getListFixtures();
                    if (listFixtures != null && listFixtures.size() > 0) {
                        for (FixtureObject fixture : listFixtures) {
                            if (fixture != null) {
//                                FixtureObject fixtureObject_copy = realm.copyFromRealm(fixture);
                                if (Integer.parseInt(fixture_addr) == fixture.getAddr()) {
                                    group_members++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return group_members;
    }

    public static void deleteAllInput() {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            realm.delete(InputObject.class);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveInput(int input, int addrType, int addr) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            String name = InputTypes.Companion.getInputName(input);
            InputObject inputObject = new InputObject(input, addrType, addr, name);
            realm.copyToRealmOrUpdate(inputObject);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyInput(int input, JsonObject configObj) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            configObj.addProperty("input", input);
            realm.createOrUpdateObjectFromJson(InputObject.class, configObj.toString());
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteInput(int input) {
        Realm realm = Realm.getDefaultInstance();
        try {
            InputObject inputObject = realm.where(InputObject.class).equalTo("input", input).findFirst();
            if (inputObject != null) {
                if (!realm.isInTransaction()) {
                    realm.beginTransaction();
                }
                inputObject.deleteFromRealm();
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveDeviceInfo(JsonObject deviceInfoObj) {
        try {
            Realm realm = Realm.getDefaultInstance();
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            if (deviceInfoObj.has("scmVer")) {
                String scmVer = deviceInfoObj.get("scmVer").getAsString();
                if (!TextUtils.isEmpty(scmVer) && scmVer.length() > 0) {
                    String[] split = scmVer.split("\\.");
                    if (split.length > 0) {
                        if (split[0].equals("0") || split[0].equals("00")) {
                            deviceInfoObj.remove("scmVer");
                        }
                    }
                }
            }
            realm.createOrUpdateObjectFromJson(DeviceObject.class, deviceInfoObj.toString());
            if (realm.isInTransaction()) {
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllExcludeCpu() {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            realm.delete(FixtureObject.class);
            realm.delete(GroupObject.class);
            realm.delete(FixtureShadow.class);
            realm.delete(InputObject.class);
            realm.delete(AutomationObject.class);
            realm.delete(AutomationShadow.class);
            realm.delete(ScheduleObject.class);
            realm.delete(SensorObject.class);
            realm.delete(SmartSocketObject.class);
            realm.delete(DmxObject.class);
            realm.delete(DmxChannelObject.class);
            realm.delete(DmxModuleObject.class);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void deleteAllExcludeLocal() {
        Realm realm = Realm.getDefaultInstance();
        try {
            deleteAllExcludeCpu();
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            realm.delete(DeviceObject.class);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteCpu(String mac) {
        try {
            Realm realm = Realm.getDefaultInstance();
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            DeviceObject deviceObject = realm.where(DeviceObject.class).equalTo("staMac", mac).findFirst();
            if (deviceObject != null) {
                deviceObject.deleteFromRealm();
            }
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void saveFixtureConfigure(int address, FixtureConfigureResponse fixtureConfigureResponse) {
        Realm realm = Realm.getDefaultInstance();
        try {
            FixtureObject fixtureObject = realm.where(FixtureObject.class).equalTo("addr", address).findFirst();
            FixtureTune tune = fixtureConfigureResponse.getTune();
            if (fixtureObject != null && tune != null) {
                if (!realm.isInTransaction()) {
                    realm.beginTransaction();
                }
                fixtureObject.getShadow().setFixtureTune(tune);
                realm.copyToRealmOrUpdate(fixtureObject);
                if (realm.isInTransaction()) {
                    realm.commitTransaction();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void compareFixturesWithSaved(FixtureAddressesResponse fixtureAddressList) {
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<FixtureObject> allSavedFixtures = realm.where(FixtureObject.class).findAll();
            List<Integer> listAddress = fixtureAddressList.getAddr();
            if (listAddress != null && listAddress.size() > 0) {
                for (FixtureObject fixtureObj : allSavedFixtures) {
                    if (!listAddress.contains(fixtureObj.getAddr())) {
                        deleteFixture(fixtureObj.getAddr());
                    }
                }
            } else {
                deleteAllExcludeCpu();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveSCMData(FixtureAddressesResponse fixtureAddresses) {
        Realm realm = Realm.getDefaultInstance();
        List<Integer> addrs = fixtureAddresses.getAddr();
        try {
            if (addrs != null && addrs.size() > 0) {
                for (Integer addr : addrs) {
                    if (addr != null && addr < 20000000) { // mean it is a SCM addr
                        FixtureShadow fixtureShadow = new FixtureShadow();
                        fixtureShadow.setAddr(addr);
                        FixtureObject fixtureObject = new FixtureObject(5, "0", addr, "", CustomTypes.FixturesTypes.SCM, fixtureShadow);
                        if (!realm.isInTransaction()) {
                            realm.beginTransaction();
                        }
                        realm.copyToRealmOrUpdate(fixtureObject);
                        if (realm.isInTransaction()) {
                            realm.commitTransaction();
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void saveOffLineFixtureList(List<Integer> addrList) {
        if (addrList != null && addrList.size() > 0) {
            Realm realm = Realm.getDefaultInstance();
            try {
                for (Integer addr : addrList) {
                    if (addr > 20000000) { // mean it is not a SCM addr
                        FixtureShadow fixtureShadow = new FixtureShadow();
                        fixtureShadow.setAddr(addr);
                        fixtureShadow.setOnline(false);
                        FixtureObject fixtureObject = new FixtureObject(5, "0", addr, "", 0, fixtureShadow);

                        if (!realm.isInTransaction()) {
                            realm.beginTransaction();
                        }
                        realm.copyToRealmOrUpdate(fixtureObject);
                        if (realm.isInTransaction()) {
                            realm.commitTransaction();
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateDCMVer(int addr) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            FixtureShadow fixtureShadow = realm.where(FixtureShadow.class).equalTo("addr", addr).findFirst();
            SensorObject sensorObject = realm.where(SensorObject.class).equalTo("addr", addr).findFirst();
            DmxModuleObject dmxModuleObject = realm.where(DmxModuleObject.class).equalTo("addr", addr).findFirst();
            String dcmtargetVer = PrefUtils.readString(PrefUtils.KEY_DCM_FW_LOCAL_VER);
            String sensortargetVer = PrefUtils.readString(PrefUtils.KEY_SENSOR_FW_LOCAL_VER);
            String dmxtargetVer = PrefUtils.readString(PrefUtils.KEY_DMX_FW_LOCAL_VER);
            if (fixtureShadow != null && dcmtargetVer != null && !TextUtils.isEmpty(dcmtargetVer) && dcmtargetVer.length() >= 5) {
                fixtureShadow.setFwVer(dcmtargetVer.substring(0, 5));
                realm.copyToRealmOrUpdate(fixtureShadow);
            }
            if (sensorObject != null && sensortargetVer != null && !TextUtils.isEmpty(sensortargetVer)) {
                sensorObject.setFwVer(sensortargetVer);
                realm.copyToRealmOrUpdate(sensorObject);
            }
            if (dmxModuleObject != null && dmxModuleObject != null && !TextUtils.isEmpty(dmxtargetVer) && !TextUtils.isEmpty(dmxtargetVer) && dmxtargetVer.length() >= 5) {
                dmxModuleObject.setFwVer(dmxtargetVer.substring(0, 5));
                realm.copyToRealmOrUpdate(dmxModuleObject);
            }
            if (realm.isInTransaction()) {
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void saveDeviceVersion(@NotNull String moduleHeader) {
        try {
            if (BrainManager.INSTANCE.getDeviceObject() != null && !TextUtils.isEmpty(BrainManager.INSTANCE.getDeviceObject().getStaMac())
                    && !TextUtils.isEmpty(BrainManager.INSTANCE.getDeviceObject().getIp())) {
                Realm realm = Realm.getDefaultInstance();
                DeviceObject deviceObject = DeviceObjectUtils.INSTANCE.getDeviceObject(BrainManager.INSTANCE.getDeviceObject().getStaMac());

//                JsonObject brainDeviceJson = BrainManager.INSTANCE.toJsonObject();
                String iotmCloudVer = PrefUtils.readString(PrefUtils.KEY_IOTM_FW_LOCAL_VER);
                String scmCloudVer = PrefUtils.readString(PrefUtils.KEY_SCM_FW_LOCAL_VER);
                if (BaseDataSource.OTAHeaderModule.IOTM.equalsIgnoreCase(moduleHeader) && iotmCloudVer != null && !TextUtils.isEmpty(iotmCloudVer)) {
                    DeviceObjectUtils.INSTANCE.setIOTMVer(iotmCloudVer);
                } else if (BaseDataSource.OTAHeaderModule.SCM.equalsIgnoreCase(moduleHeader) && scmCloudVer != null && !TextUtils.isEmpty(scmCloudVer)) {
                    DeviceObjectUtils.INSTANCE.setScmVer(scmCloudVer);

                    if (!realm.isInTransaction()) {
                        realm.beginTransaction();
                    }
                    FixtureObject fixtureObject = realm.where(FixtureObject.class).equalTo("type", CustomTypes.FixturesTypes.SCM).findFirst();
                    if (fixtureObject != null && fixtureObject.getShadow() != null) {
                        fixtureObject.getShadow().setFwVer(PrefUtils.readString(PrefUtils.KEY_SCM_FW_LOCAL_VER));
                        realm.copyToRealmOrUpdate(fixtureObject);
                        realm.commitTransaction();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveNewIpToDeviceObject(String ip, DeviceObject deviceObject) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            deviceObject.setIp(ip);
            realm.copyToRealmOrUpdate(deviceObject);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void saveAutomation(@NotNull int autoAddr, @Nullable String name, int type, boolean enabled, @Nullable List<? extends AutoSingleStateBean> fixtureStateList, @Nullable List<? extends AutoSingleStateBean> groupStateList,
                                      @Nullable List<Integer> occSensors, @Nullable List<? extends AutoSingleStateBean> fixtWhenOcc, @Nullable List<? extends AutoSingleStateBean> grpWhenOcc,
                                      @Nullable List<? extends AutoSingleStateBean> fixtWhenUnocc, @Nullable List<? extends AutoSingleStateBean> grpWhenUnocc, @Nullable List<? extends AutoSingleStateBean> fixtWhenDisabled,
                                      @Nullable List<? extends AutoSingleStateBean> grpWhenDisabled, @Nullable List<Integer> lightSensor, @Nullable Integer daylightGroup, @Nullable Integer minTargetLevel, @Nullable Integer maxTargetLevel) {
        Log.d(TAG, "saveAutomation: autoAddr = " + autoAddr);
        Log.d(TAG, "saveAutomation: fixtWhenOcc =" + fixtWhenOcc + ",grpWhenOcc =" + grpWhenOcc + ",fixtWhenUnocc =" + fixtWhenUnocc + ",grpWhenUnocc=" + grpWhenUnocc);
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            AutomationObject automationObject = new AutomationObject();
            automationObject.setAddr(autoAddr);
            if (!TextUtils.isEmpty(name)) {
                automationObject.setName(name);
            }
            automationObject.setType(type);
            automationObject.setEnabled(enabled);

            RealmList<Integer> listSensors = new RealmList();
            if (occSensors != null && occSensors.size() > 0) {
                listSensors.addAll(occSensors);
            }
            automationObject.setOccSensors(listSensors);
//            Log.d(TAG, "saveAutomation: fixturesStateList.fixtureAddr="+fixtureStateList.get(0).());

            //save autoshadow
            RealmList<AutomationShadow> fixtureAutoShadowList = getAutoFixtureShadowList(realm, autoAddr, fixtureStateList, AUTO_STANDARD);
            RealmList<AutomationShadow> fixtWhenOccAutoShadowList = getAutoFixtureShadowList(realm, autoAddr, fixtWhenOcc, AUTO_ACTION_TYPE_OCCUPIED);
            RealmList<AutomationShadow> fixtWhenUnoccAutoShadowList = getAutoFixtureShadowList(realm, autoAddr, fixtWhenUnocc, AUTO_ACTION_TYPE_UN_OCCUPIED);
            RealmList<AutomationShadow> fixtWhenDisabledAutoShadowList = getAutoFixtureShadowList(realm, autoAddr, fixtWhenDisabled, AUTO_ACTION_TYPE_DISABLE);

            RealmList<AutomationShadow> groupAutoShadowList = getAutoGroupShadowList(realm, autoAddr, groupStateList, AUTO_STANDARD);
            RealmList<AutomationShadow> grpWhenOccAutoShadowList = getAutoGroupShadowList(realm, autoAddr, grpWhenOcc, AUTO_ACTION_TYPE_OCCUPIED);
            RealmList<AutomationShadow> grpWhenUnoccAutoShadowList = getAutoGroupShadowList(realm, autoAddr, grpWhenUnocc, AUTO_ACTION_TYPE_UN_OCCUPIED);
            RealmList<AutomationShadow> grpWhenDisabledAutoShadowList = getAutoGroupShadowList(realm, autoAddr, grpWhenDisabled, AUTO_ACTION_TYPE_DISABLE);
            automationObject.setFixture(fixtureAutoShadowList);
            automationObject.setFixtWhenOcc(fixtWhenOccAutoShadowList);
            automationObject.setFixtWhenUnocc(fixtWhenUnoccAutoShadowList);
            automationObject.setFixtWhenDisabled(fixtWhenDisabledAutoShadowList);
            automationObject.setGroup(groupAutoShadowList);
            automationObject.setGrpWhenOcc(grpWhenOccAutoShadowList);
            automationObject.setGrpWhenUnocc(grpWhenUnoccAutoShadowList);
            automationObject.setGrpWhenDisabled(grpWhenDisabledAutoShadowList);

            if (lightSensor != null && lightSensor.size() > 0)
                automationObject.setLightSensor(lightSensor.get(0));
            else automationObject.setLightSensor(0);
            if (daylightGroup != null) automationObject.setDaylightGroup(daylightGroup);
            else automationObject.setDaylightGroup(0);
            if (minTargetLevel != null) automationObject.setMinTargetLevel(minTargetLevel);
            if (maxTargetLevel != null) automationObject.setMaxTargetLevel(maxTargetLevel);

            realm.copyToRealmOrUpdate(automationObject);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    private static RealmList<AutomationShadow> getAutoFixtureShadowList(Realm realm, int autoAddr, List<? extends AutoSingleStateBean> fixtureStateList, String typeAutoAction) {
        RealmList<AutomationShadow> autoShadowsList = new RealmList<>();
        if (fixtureStateList != null && fixtureStateList.size() > 0) {
            for (AutoSingleStateBean autoSingleStateBean : fixtureStateList) {
                if (autoSingleStateBean != null) {
                    if (autoSingleStateBean.getAddr() != null) {
                        FixtureObject fixtureObject = realm.where(FixtureObject.class).equalTo("addr", autoSingleStateBean.getAddr()).findFirst();
                        //for refresh logic,avoid local Automation data not have this fixture
                        if (fixtureObject != null) {
//                        String stateJson = gson.toJson(autoSingleStateBean);
                            AutomationShadow atuoFixtureTargetShadow = new AutomationShadow();//contain fixtureAddr
                            //union key format is autoAddr_fixtureAddr
                            atuoFixtureTargetShadow.setUnionKey(String.format(Locale.getDefault(), "%d_%d_%s", autoAddr, autoSingleStateBean.getAddr(), typeAutoAction));
                            atuoFixtureTargetShadow.setAutoAddr(autoAddr);
                            atuoFixtureTargetShadow.setFixtureAddr(autoSingleStateBean.getAddr());
                            if (autoSingleStateBean.getState() != null) {
                                atuoFixtureTargetShadow.setLevel(autoSingleStateBean.getState().getLevel());
                                atuoFixtureTargetShadow.setStatus(autoSingleStateBean.getState().getStatus());
                                atuoFixtureTargetShadow.setPreset(autoSingleStateBean.getState().getPreset());
                            }
                            autoShadowsList.add(atuoFixtureTargetShadow);
                            realm.copyToRealmOrUpdate(atuoFixtureTargetShadow);
                        }
                    }
                }
            }
        }
        return autoShadowsList;
    }

    private static RealmList<AutomationShadow> getAutoGroupShadowList(Realm realm, int autoAddr, List<? extends AutoSingleStateBean> groupStateList, String typeAutoAction) {
        RealmList<AutomationShadow> groupAutoShadowsList = new RealmList<>();
        if (groupStateList != null && groupStateList.size() > 0) {
            for (AutoSingleStateBean autoSingleStateBean : groupStateList) {
                if (autoSingleStateBean != null) {
                    if (autoSingleStateBean.getAddr() != null) {
                        GroupObject groupObject = realm.where(GroupObject.class).equalTo("addr", autoSingleStateBean.getAddr()).findFirst();
                        //for refresh logic,avoid local Automation data not have this group
                        if (groupObject != null) {
                            AutomationShadow autoGroupTarget = new AutomationShadow(); //contain groupAddr
                            autoGroupTarget.setUnionKey(String.format(Locale.getDefault(), "%d_%d_%s", autoAddr, autoSingleStateBean.getAddr(), typeAutoAction));
                            autoGroupTarget.setAutoAddr(autoAddr);
                            autoGroupTarget.setGroupAddr(autoSingleStateBean.getAddr());
                            if (autoSingleStateBean.getState() != null) {
                                autoGroupTarget.setLevel(autoSingleStateBean.getState().getLevel());
                                autoGroupTarget.setStatus(autoSingleStateBean.getState().getStatus());
                            }
                            groupAutoShadowsList.add(autoGroupTarget);
                            realm.copyToRealmOrUpdate(autoGroupTarget);
                        }
                    }
                }
            }
        }
        return groupAutoShadowsList;
    }

    private static RealmList<AutomationShadow> getAutoFixtureShadowModifyList(Realm realm, int autoAddr, List<? extends AutomationShadow> fixtureStateList, String typeAutoAction) {
        Gson gson = new Gson();
        RealmList<AutomationShadow> autoShadowsList = new RealmList<>();
        if (fixtureStateList != null && fixtureStateList.size() > 0) {
            for (AutomationShadow autoFixtureShadow : fixtureStateList) {
                FixtureObject fixtureObject = realm.where(FixtureObject.class).equalTo("addr", autoFixtureShadow.getFixtureAddr()).findFirst();
                //for refresh logic,avoid local Automation data not have this fixture
                if (fixtureObject != null) {
                    String stateJson = gson.toJson(autoFixtureShadow);
                    AutomationShadow atuoFixtureTargetShadow = gson.fromJson(stateJson, AutomationShadow.class);//contain fixtureAddr
                    //union key format is autoAddr_fixtureAddr
                    atuoFixtureTargetShadow.setUnionKey(String.format(Locale.getDefault(), "%d_%d_%s", autoAddr, autoFixtureShadow.getFixtureAddr(), typeAutoAction));
                    atuoFixtureTargetShadow.setAutoAddr(autoAddr);
                    autoShadowsList.add(atuoFixtureTargetShadow);
                }
            }
        }
        return autoShadowsList;
    }

    private static RealmList<AutomationShadow> getAutoGroupShadowModifyList(Realm realm, int autoAddr, List<? extends AutomationShadow> groupStateList, String typeAutoAction) {
        Gson gson = new Gson();
        RealmList<AutomationShadow> groupAutoShadowsList = new RealmList<>();
        if (groupStateList != null && groupStateList.size() > 0) {
            for (AutomationShadow autoGroupShadow : groupStateList) {
                GroupObject groupObject = realm.where(GroupObject.class).equalTo("addr", autoGroupShadow.getGroupAddr()).findFirst();
                //for refresh logic,avoid local Automation data not have this group
                if (groupObject != null) {
                    String stateJson = gson.toJson(autoGroupShadow);
                    AutomationShadow autoGroupTarget = gson.fromJson(stateJson, AutomationShadow.class); //contain groupAddr
                    autoGroupTarget.setUnionKey(String.format(Locale.getDefault(), "%d_%d_%s", autoAddr, autoGroupShadow.getGroupAddr(), typeAutoAction));
                    autoGroupTarget.setAutoAddr(autoAddr);
                    groupAutoShadowsList.add(autoGroupTarget);
                }
            }
        }
        return groupAutoShadowsList;
    }

    public static void updateAutomation(int autoAddr, @Nullable String name, @Nullable Integer type, boolean enabled, @Nullable List<? extends AutomationShadow> fixtureStateList, @Nullable List<? extends AutomationShadow> groupStateList,
                                        @Nullable List<Integer> occSensors, @Nullable List<? extends AutomationShadow> fixtWhenOcc, @Nullable List<? extends AutomationShadow> grpWhenOcc,
                                        @Nullable List<? extends AutomationShadow> fixtWhenUnocc, @Nullable List<? extends AutomationShadow> grpWhenUnocc, @Nullable List<? extends AutomationShadow> fixtWhenDisabled,
                                        @Nullable List<? extends AutomationShadow> grpWhenDisabled, @Nullable Integer lightSensor, @Nullable Integer daylightGroup, @Nullable Integer minTargetLevel, @Nullable Integer maxTargetLevel, boolean isFavorite, @Nullable String bgName) {
        Realm realm = Realm.getDefaultInstance();
        try {
            AutomationObject tempAutoObject = realm.where(AutomationObject.class).equalTo("addr", autoAddr).findFirst();
            AutomationObject automationObject;
            if (tempAutoObject == null) {
                automationObject = new AutomationObject();
            } else {
                automationObject = realm.copyFromRealm(tempAutoObject);
            }
            if (automationObject != null) {
                if (!realm.isInTransaction()) {
                    realm.beginTransaction();
                }
                if (tempAutoObject == null) {
                    automationObject.setAddr(autoAddr);
                }
                automationObject.setEnabled(enabled);
                automationObject.setFavorite(isFavorite);
                if (!TextUtils.isEmpty(name)) automationObject.setName(name);
                if (type != null) automationObject.setType(type);

                if (!TextUtils.isEmpty(bgName)) {
                    automationObject.setBgName(bgName);
                }
                RealmList<Integer> listSensors = new RealmList();
                if (occSensors != null && occSensors.size() > 0) {
                    listSensors.addAll(occSensors);
                }
                automationObject.setOccSensors(listSensors);
                //save autoshadow
                RealmList<AutomationShadow> fixtureAutoShadowList = getAutoFixtureShadowModifyList(realm, autoAddr, fixtureStateList, AUTO_STANDARD);
                RealmList<AutomationShadow> fixtWhenOccAutoShadowList = getAutoFixtureShadowModifyList(realm, autoAddr, fixtWhenOcc, AUTO_ACTION_TYPE_OCCUPIED);
                RealmList<AutomationShadow> fixtWhenUnoccAutoShadowList = getAutoFixtureShadowModifyList(realm, autoAddr, fixtWhenUnocc, AUTO_ACTION_TYPE_UN_OCCUPIED);
                RealmList<AutomationShadow> fixtWhenDisabledAutoShadowList = getAutoFixtureShadowModifyList(realm, autoAddr, fixtWhenDisabled, AUTO_ACTION_TYPE_DISABLE);

                RealmList<AutomationShadow> groupAutoShadowList = getAutoGroupShadowModifyList(realm, autoAddr, groupStateList, AUTO_STANDARD);
                RealmList<AutomationShadow> grpWhenOccAutoShadowList = getAutoGroupShadowModifyList(realm, autoAddr, grpWhenOcc, AUTO_ACTION_TYPE_OCCUPIED);
                RealmList<AutomationShadow> grpWhenUnoccAutoShadowList = getAutoGroupShadowModifyList(realm, autoAddr, grpWhenUnocc, AUTO_ACTION_TYPE_UN_OCCUPIED);
                RealmList<AutomationShadow> grpWhenDisabledAutoShadowList = getAutoGroupShadowModifyList(realm, autoAddr, grpWhenDisabled, AUTO_ACTION_TYPE_DISABLE);

                automationObject.setFixture(fixtureAutoShadowList);
                automationObject.setFixtWhenOcc(fixtWhenOccAutoShadowList);
                automationObject.setFixtWhenUnocc(fixtWhenUnoccAutoShadowList);
                automationObject.setFixtWhenDisabled(fixtWhenDisabledAutoShadowList);
                automationObject.setGroup(groupAutoShadowList);
                automationObject.setGrpWhenOcc(grpWhenOccAutoShadowList);
                automationObject.setGrpWhenUnocc(grpWhenUnoccAutoShadowList);
                automationObject.setGrpWhenDisabled(grpWhenDisabledAutoShadowList);

                if (lightSensor != null) automationObject.setLightSensor(lightSensor);
                else automationObject.setLightSensor(0);
                if (daylightGroup != null) automationObject.setDaylightGroup(daylightGroup);
                else automationObject.setDaylightGroup(0);
                if (minTargetLevel != null) automationObject.setMinTargetLevel(minTargetLevel);
                if (maxTargetLevel != null) automationObject.setMaxTargetLevel(maxTargetLevel);

                realm.copyToRealmOrUpdate(automationObject);
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void enbleAutomation(int addr, boolean enabled) {
        Realm realm = Realm.getDefaultInstance();
        try {
            AutomationObject automationObject = realm.where(AutomationObject.class).equalTo("addr", addr).findFirst();
            if (automationObject != null) {
                if (!realm.isInTransaction()) {
                    realm.beginTransaction();
                }
                automationObject.setEnabled(enabled);
                realm.copyToRealmOrUpdate(automationObject);
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void deleteAutomation(int autoAddr) {
        Realm realm = Realm.getDefaultInstance();
        try {
            AutomationObject automationObject = realm.where(AutomationObject.class).equalTo("addr", autoAddr).findFirst();
            if (automationObject != null) {
                if (!realm.isInTransaction()) {
                    realm.beginTransaction();
                }
                automationObject.deleteFromRealm();
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void deleteAllAutomationsData() {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            realm.delete(AutomationObject.class);
            realm.delete(AutomationShadow.class);
            if (realm.isInTransaction()) {
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    //control standard auto need to save state to fixture and group
    public static void saveAutoShadowToFixAndGrp(int autoAddr) {
        Realm realm = Realm.getDefaultInstance();
        try {
            AutomationObject automationObject = realm.where(AutomationObject.class).equalTo("addr", autoAddr).findFirst();
            if (automationObject != null) {
                AutomationObject automationObject_copy = realm.copyFromRealm(automationObject);
                RealmList<AutomationShadow> fixtureAutoShadowList = automationObject_copy.getFixture();
                RealmList<AutomationShadow> groupAutoShadowList = automationObject_copy.getGroup();

                if (groupAutoShadowList != null && groupAutoShadowList.size() > 0) {
                    for (AutomationShadow groupAutoShadow : groupAutoShadowList) {
                        if (!realm.isInTransaction()) {
                            realm.beginTransaction();
                        }
                        GroupObject groupObject = realm.where(GroupObject.class).equalTo("addr", groupAutoShadow.getGroupAddr()).findFirst();
                        if (groupObject != null) {
                            GroupObject groupObject_copy = realm.copyFromRealm(groupObject);
                            RealmList<FixtureObject> listFixtures = groupObject_copy.getListFixtures();
                            if (listFixtures != null && listFixtures.size() > 0) {
                                for (FixtureObject fixtureObject : listFixtures) {
                                    if (fixtureObject != null) {
                                        if (fixtureObject.getShadow() != null) {
                                            if (groupAutoShadow.getLevel() == 0) {
                                                fixtureObject.getShadow().setLevel(0);
                                                fixtureObject.getShadow().setStatus(false);
                                            } else {
                                                fixtureObject.getShadow().setLevel(groupAutoShadow.getLevel());
                                                fixtureObject.getShadow().setStatus(true);
                                            }
                                            realm.copyToRealmOrUpdate(fixtureObject);
                                        }
                                    }
                                }
                            }
                        }
                        if (realm.isInTransaction()) {
                            realm.commitTransaction();
                        }
                    }
                }

                if (fixtureAutoShadowList != null && fixtureAutoShadowList.size() > 0) {
                    for (AutomationShadow fixtureAutoShadow : fixtureAutoShadowList) {
                        if (!realm.isInTransaction()) {
                            realm.beginTransaction();
                        }
                        FixtureShadow fixtureShadow = realm.where(FixtureShadow.class).equalTo("addr", fixtureAutoShadow.getFixtureAddr()).findFirst();
                        if (fixtureShadow != null) {
                            if (fixtureAutoShadow.getLevel() == 0) {
                                fixtureShadow.setLevel(0);
                                fixtureShadow.setStatus(false);
                            } else {
                                fixtureShadow.setLevel(fixtureAutoShadow.getLevel());
                                fixtureShadow.setStatus(true);
                            }
                            realm.copyToRealmOrUpdate(fixtureShadow);
                        }
                        if (realm.isInTransaction()) {
                            realm.commitTransaction();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public static void saveMyBrainDevices(@NotNull JsonObject jsonObject) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (jsonObject.has("devices")) {
                JsonArray devices = jsonObject.getAsJsonArray("devices");
                if (devices != null && devices.size() > 0) {
                    for (JsonElement device : devices) {
                        JsonObject deviceAsJsonObject = device.getAsJsonObject();
                        if (deviceAsJsonObject.has("clientId")) {
                            String clientId = deviceAsJsonObject.get("clientId").getAsString();
                            String macFromClientID = getMacFromClientID(clientId);
                            if (!TextUtils.isEmpty(macFromClientID)) {
                                if (!realm.isInTransaction()) {
                                    realm.beginTransaction();
                                }
                                DeviceObject targetDeviceObject = realm.where(DeviceObject.class).equalTo("staMac", macFromClientID).findFirst();
                                if (targetDeviceObject == null) {
                                    targetDeviceObject = new DeviceObject();
                                    targetDeviceObject.setStaMac(macFromClientID);
                                }
                                if (deviceAsJsonObject.has("deviceName") && targetDeviceObject.isValid()) {
                                    String deviceName = deviceAsJsonObject.get("deviceName").getAsString();
                                    targetDeviceObject.setDeviceName(deviceName);
                                }

                                realm.copyToRealmOrUpdate(targetDeviceObject);
                                realm.commitTransaction();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getMacFromClientID(String clientId) {
        String targetMac = "";
        if (clientId.length() >= 15) {
            String replaceClientId = clientId.replace("MF_", "");
            targetMac = String.format("%s:%s:%s:%s:%s:%s", replaceClientId.substring(0, 2), replaceClientId.substring(2, 4),
                    replaceClientId.substring(4, 6), replaceClientId.substring(6, 8), replaceClientId.substring(8, 10), replaceClientId.substring(10, 12));

        }
        return targetMac;
    }

    public static void deleteAllDmx() {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            realm.delete(DmxObject.class);
            realm.delete(DmxChannelObject.class);
            if (realm.isInTransaction()) {
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveDmx(JsonObject jsonResponse) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (!realm.isInTransaction()) {
                realm.beginTransaction();
            }
            realm.createOrUpdateObjectFromJson(DmxObject.class, jsonResponse.toString());
            if (realm.isInTransaction()) {
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteDmx(int address) {
        Realm realm = Realm.getDefaultInstance();
        try {
            DmxObject dmxObject = realm.where(DmxObject.class).equalTo("addr", address).findFirst();
            if (dmxObject != null) {
                if (!realm.isInTransaction()) {
                    realm.beginTransaction();
                }
//                RealmList<DmxChannelObject> mapping = dmxObject.getMapping();
//                if (mapping != null && mapping.size() > 0) {
//                    for (DmxChannelObject channelObj : mapping) {
//                        channelObj.deleteFromRealm();
//                    }
//                }
                dmxObject.deleteFromRealm();
                if (realm.isInTransaction()) {
                    realm.commitTransaction();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
