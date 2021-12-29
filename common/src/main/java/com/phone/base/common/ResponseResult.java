package com.phone.base.common;

public interface ResponseResult {
    String RESULT_SUCCESS = "0";
    String STRUT_GENERAL_FAILURE = "-1";
    String STRUT_INVALID_ADDR = "-2";
    String STRUT_INVALID_INPUT = "-3";
    String STRUT_INVALID_SIZE = "-4";
    String STRUT_STORAGE_ERROR = "-5";
    String STRUT_SCM_COMM_ERROR = "-6";
    String STRUT_INVALID_PARAM = "-7";
    String STRUT_INVALID_FIXTURE = "-8";
    String STRUT_INVALID_PROV = "-9";
    String STRUT_NOT_ENABLED = "-10";
    String STRUT_MAX_ENABLED = "-11";
    String STRUT_OVERFLOW = "-12";

    String STRUT_MAX_DEVICES = "-13";
    String STRUT_MAX_PAIRINGS = "-14";
    String STRUT_NOT_FOUND = "-15";
    String STRUT_ALREADY_EXISTS = "-16";
    String STRUT_ALREADY_PAIRED = "-17";
    String STRUT_NAME_TOO_LARGE = "-18";
    String STRUT_INVALID_ACTION = "-19";
    String STRUT_AUTOMATION_FULL = "-20";
    String STRUT_INVALID_FIXTURE_ADDR = "-21";
    String STRUT_GROUP_ADDR_INVALID = "-22";
    String STRUT_GROUP_INDEX_INVALID = "-23";
    String STRUT_MAX_INTEGRATIONS = "-26";
    String STRUT_OUT_OF_MEMORY = "-27";
    String STRUT_ADDRESS_EXISTS = "-28";
    String STRUT_NVS_GET_ERROR = "-29";
    String STRUT_NVS_SET_ERROR = "-30";
    String STRUT_NVS_ERASE_ERROR = "-31";
    String STRUT_SIZE_MISMATCH = "-32";
    String STRUT_INVALID_TYPE = "-33";
    String STRUT_CREATE_JSON_ERROR = "-34";
    String STRUT_MISS_TYPE = "-35";
    String STRUT_MISS_FIXTURES = "-36";
    String STRUT_MISS_GROUPS = "-37";
    String STRUT_MISS_OCCSENSOR = "-38";
    String STRUT_AUTOM_RES_FAIL = "-39";
    String STRUT_CANT_REMOVE_INTEGRATED_DEVICE = "-40";
    String STRUT_MAX_GROUPS = "-41";
    String STRUT_NO_MAPPING = "-42";
    //Network Errors
    String STRUT_SCAN_IN_PROGRESS = "-60";
    String STRUT_SCAN_NOT_STARTED = "-61";
    String STRUT_WIFI_OFF = "-62";
    String STRUT_AWS_NOT_CONNECTED = "-63";
    String STRUT_AWS_CLOUD_FAILURE = "-64";
    String STRUT_AWS_TIMED_OUT = "-65";
    String STRUT_AWS_ALREADY_COMMISSIONED = "-66";
    String STRUT_AWS_NOT_COMMISSIONED = "-67";
    //OTA Errors
    String STRUT_OTA_UPDATE_NOT_STARTED = "-102";
    String STRUT_OTA_VALIDATION_FAILED = "-103";
    String STRUT_OTA_END_FAILED = "-104";
    String STRUT_OTA_SAME_IMAGE = "-105";
    String STRUT_OTA_SET_BOOT_FAILED = "-106";
    String STRUT_OTA_UPDATE_IN_PROGRESS = "-107";
    String STRUT_OTA_PARTITION_FAILED = "-108";
    String STRUT_OTA_IMAGE_SIZE = "-109";
    String STRUT_OTA_BEGIN_FAILED = "-110";
    String STRUT_OTA_INVALID_PARAMS = "-111";
    String STRUT_OTA_WRITE_FAILED = "-112";
}
