/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

#include <jni.h>
#include <string>
extern "C"
JNIEXPORT jstring JNICALL
Java_com_sanjaya_joseph_core_data_Secured_apiKey(JNIEnv *env, jobject thiz) {
    std::string API_KEY = "996363c15423470d95d98ef440795091";
    return env->NewStringUTF(API_KEY.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_sanjaya_joseph_core_data_Secured_baseUrl(JNIEnv *env, jobject thiz) {
    std::string API_KEY = "https://newsapi.org/";
    return env->NewStringUTF(API_KEY.c_str());
}