package com.newtonacademic.newtontutors.retrofit;

import android.content.Context;

import com.newtonacademic.newtontutors.EducationAppliction;
import com.newtonacademic.newtontutors.R;

/**
 * ResponseCode类：
 *
 * @author lyy
 * @version v1.0, 2019/1/21
 */
public class ResponseResult {

    public interface ResponseCode {
        /**
         * 请求失败
         */
        int FAILURE = 0;
        /**
         * 请求成功
         */
        int SUCCESS = 1;

        int NO_PERMISSION = 403;
        int IP_NO_PERMISSION = 4031;
        int IP_REFUSE = 4036;
        int REFUSE_VISITE = 4038;
        int RESOURCE_NO_EXIST = 404;
        int DATABASE_TIMEOUT = 405;
        int SERVER_EXCEPTION = 500;
        int PASSWORD_ERROR_MORE_THAN = 501;
        int PASSWORD_ERROR_MORE_THAN_TODAY = 502;
        int SMS_CODE_ERROR_MORE_THAN = 503;
        int SMS_SEND_MORE_THAN = 504;
        int ACCOUNT_PWD_ERROR = 400137;

    }

    public static String getString(int responseCode) {
        Context context = EducationAppliction.getInstance();
        switch (responseCode) {
            case ResponseCode.FAILURE:
                return context.getString(R.string.request_result_failed);
            case ResponseCode.SUCCESS:
                return context.getString(R.string.request_result_success);
            case ResponseCode.NO_PERMISSION:
                return context.getString(R.string.no_permission);
            case ResponseCode.IP_NO_PERMISSION:
                return context.getString(R.string.ip_no_permission);
            case ResponseCode.IP_REFUSE:
                return context.getString(R.string.ip_refuse);
            case ResponseCode.REFUSE_VISITE:
                return context.getString(R.string.refuse_visite);
            case ResponseCode.RESOURCE_NO_EXIST:
                return context.getString(R.string.resource_no_exist);
                case ResponseCode.DATABASE_TIMEOUT:
                return context.getString(R.string.database_timeout);
            case ResponseCode.SERVER_EXCEPTION:
                return context.getString(R.string.server_exception);
            case ResponseCode.PASSWORD_ERROR_MORE_THAN:
                return context.getString(R.string.password_error_more_than);
            case ResponseCode.PASSWORD_ERROR_MORE_THAN_TODAY:
                return context.getString(R.string.password_error_more_than_today);
            case ResponseCode.SMS_CODE_ERROR_MORE_THAN:
                return context.getString(R.string.sms_code_error_more_than);
            case ResponseCode.SMS_SEND_MORE_THAN:
                return context.getString(R.string.sms_send_more_than);
            default:
        }

        return "";
    }


}
