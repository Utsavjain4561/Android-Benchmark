package com.example.android.anditest;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by UTSAV JAIN on 6/2/2018.
 */

public class ComputeSHA {

    private String mMode,mMessage;
    public ComputeSHA(String message,String mode) {

        mMode = mode;
        mMessage = message;

    }
    public void computeSHAHash() {

        try {
            MessageDigest mdSha1 = null;
            try {
                mdSha1 = MessageDigest.getInstance(mMode);
            } catch (NoSuchAlgorithmException e) {
                Log.e("Exception :", "No hash Algorithm found");
                e.printStackTrace();
            }
            try {
                mdSha1.update(mMessage.getBytes("ASCII"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte data[] = mdSha1.digest();
            StringBuffer buffer = new StringBuffer();
            String hexaHash = Base64.encodeToString(data, 0, data.length, 0);
            buffer.append(hexaHash);

            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
