package com.wisesoft.traveltv.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by picher on 2018/1/7.
 * Describe：
 */

public class Utils {

    public static String readTextfileFromAssets(Context context, String fileName) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;

        try {
            fIn = context.getResources().getAssets().open(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String e2 = "";

            while((e2 = input.readLine()) != null) {
                returnString.append(e2);
            }
        } catch (Exception var15) {
            var15.getMessage();
        } finally {
            try {
                if(isr != null) {
                    isr.close();
                }

                if(fIn != null) {
                    fIn.close();
                }

                if(input != null) {
                    input.close();
                }
            } catch (Exception var14) {
                var14.getMessage();
            }

        }

        return returnString.toString();
    }
}
