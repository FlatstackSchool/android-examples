package com.example.ereminilya.smsinterceptor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.ereminilya.smsinterceptor.utils.Strings;
import com.example.ereminilya.smsinterceptor.utils.di.Injector;

import javax.inject.Inject;

/**
 * Created by ereminilya on 24/11/16.
 */

public class SmsListener extends BroadcastReceiver {

    @Inject Settings settings;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Injector.inject(this, context);
            Bundle bundle = intent.getExtras();
            SmsMessage[] messages;
            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    messages = new SmsMessage[pdus.length];
                    messages[0] = SmsMessage.createFromPdu((byte[]) pdus[0]);
                    String wholeString = getMessageText(messages[0]);
                    String sender = messages[0].getOriginatingAddress();
                    if (Strings.equals(settings.getPhoneToSpy(), sender)) {
                        Toast.makeText(context, "message from " + sender + " intercepted: " + wholeString,
                            Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ignored) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * if you need message body you can get it here
     * @param message is intercepted SMS
     * @return message text
     */
    private String getMessageText(@NonNull SmsMessage message) {
        return message.getMessageBody();
    }
}