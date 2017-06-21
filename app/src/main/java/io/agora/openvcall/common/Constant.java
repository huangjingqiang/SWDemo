package io.agora.openvcall.common;

import io.agora.rtc.RtcEngine;

public class Constant {
    public static final String certificate = "bf9d1c56c7384d2da0aefb2f846aa532";
    public static final String MEDIA_SDK_VERSION;

    static {
        String sdk = "undefined";
        try {
            sdk = RtcEngine.getSdkVersion();
        } catch (Throwable e) {
        }
        MEDIA_SDK_VERSION = sdk;
    }

    public static boolean PRP_ENABLED = true;
    public static float PRP_DEFAULT_LIGHTNESS = .65f;
    public static float PRP_DEFAULT_SMOOTHNESS = 1.0f;
    public static final float PRP_MAX_LIGHTNESS = 1.0f;
    public static final float PRP_MAX_SMOOTHNESS = 1.0f;

}
