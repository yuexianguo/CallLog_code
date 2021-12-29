package com.phone.base.common;

public interface RequestActions {

    int FIXTURE_CREATE = 0;
    int FIXTURE_MODIFY = 1;
    int FIXTURE_DELETE = 2;
    int FIXTURE_READ = 3;
    int FIXTURE_CONTROL = 4;
    int FIXTURE_LIST = 5;
    int FIXTURE_CONFIGURE = 6;
    int FIXTURE_SEARCH = 7;

    int GROUP_CREATE = 0;
    int GROUP_MODIFY = 1;
    int GROUP_DELETE = 2;
    int GROUP_READ = 3;
    int GROUP_CONTROL = 4;
    int GROUP_LIST = 5;

    int INPUT_CREATE = 0;
    int INPUT_MODIFY = 1;
    int INPUT_DELETE = 2;
    int INPUT_READ = 3;
    int INPUT_CONTROL = 4;
    int INPUT_LIST = 5;

    int SENSOR_CREATE = 0;
    int SENSOR_MODIFY = 1;
    int SENSOR_DELETE = 2;
    int SENSOR_READ = 3;
    int SENSOR_RESERVED = 4;
    int SENSOR_LIST = 5;
    //    int SENSOR_RESERVED = 6;
    int SENSOR_SEARCH = 7;

    int SCHEDULE_CREATE = 0;
    int SCHEDULE_MODIFY = 1;
    int SCHEDULE_DELETE = 2;
    int SCHEDULE_READ = 3;
    int SCHEDULE_RESERVED = 4;
    int SCHEDULE_LIST = 5;

    int SMARTSOCKET_READ = 1;
    int SMARTSOCKET_CONTROL = 2;

    int AUTOMATION_CREATE = 0;
    int AUTOMATION_MODIFY = 1;
    int AUTOMATION_DELETE = 2;
    int AUTOMATION_READ = 3;
    int AUTOMATION_CONTROL = 4;
    int AUTOMATION_LIST = 5;

    int DMX_CREATE = 0;
    int DMX_MODIFY = 1;
    int DMX_DELETE = 2;
    int DMX_READ = 3;
    int DMX_CONFIG = 4;
    int DMX_LIST = 5;
    int DMX_GET_INFO = 6;

    int BATCHOTA_GET_VER = 0;
    int BATCHOTA_TRIGGER = 1;
    int BATCHOTA_STATUS = 2;
}
