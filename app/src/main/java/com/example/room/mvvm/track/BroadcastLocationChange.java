package com.example.room.mvvm.track;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * Created by Marty on 11/25/2017.
 */

class BroadcastLocationChange extends IntentService{

    public BroadcastLocationChange(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
