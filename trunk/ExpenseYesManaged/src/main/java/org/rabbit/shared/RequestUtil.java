package org.rabbit.shared;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

             public static final String EMPTY_STR = "";
             public static final String CANCEL_STR = "Cancel";

             public static String getStringValue(HttpServletRequest request,
                                             String param, String defaultValue) {
                             String paramValue = request.getParameter(param);
                             if (ObjectUtils.isNotNullAndNotEmpty(paramValue)) {
                                             return paramValue;
                             }

                             return defaultValue;
             }

             public static String getStringValue(HttpServletRequest request, String param) {
                             return getStringValue(request, param, EMPTY_STR);
             }

             public static int getIntValue(HttpServletRequest request, String param,
                                             int defaultValue) {
                             String paramValue = request.getParameter(param);
                             return ObjectUtils.getIntValue(paramValue, defaultValue);
             }

             public static double getDoubleValue(HttpServletRequest request,
                                             String param, double defaultValue) {
                             String paramValue = request.getParameter(param);
                             return ObjectUtils.getDoubleValue(paramValue, defaultValue);
             }

             public static long getLongValue(HttpServletRequest request, String param,
                                             long defaultValue) {
                             String paramValue = request.getParameter(param);
                             return ObjectUtils.getLongValue(paramValue, defaultValue);
             }

             public static boolean getBoolValue(HttpServletRequest request,
                                             String param, boolean defaultValue) {
                             String paramValue = request.getParameter(param);
                             return ObjectUtils.getBooleanValue(paramValue, defaultValue);
             }
}