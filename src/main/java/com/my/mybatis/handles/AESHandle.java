package com.my.mybatis.handles;

import com.my.mybatis.utils.AESUtils;

public class AESHandle implements DESHandle {

    @Override
    public boolean support(String flag) {
        return null != flag && flag.equals("AES");
    }

    @Override
    public String encrypt(String content) {
        try {
            return AESUtils.encrypt(content);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public String decrypt(String secretContent) {
        try {
            return AESUtils.decrypt(secretContent);
        } catch (Exception e) {
            e.printStackTrace();
            return secretContent;
        }
    }
}
