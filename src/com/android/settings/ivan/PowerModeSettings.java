package com.android.settings.ivan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings.System;
import android.view.Window;

import com.android.settings.R;

public class PowerModeSettings extends Activity {
    public static final String POWER_MODE_KEY_PROPERTY = "persist.sys.aries.power_profile";
    public static final String POWER_MODE_VALUE_HIGH = "high";
    public static final String POWER_MODE_VALUE_MIDDLE = "middle";
    public static final String POWER_MODE_VALUE_DEFAULT = POWER_MODE_VALUE_MIDDLE;
    public static final String[] POWER_MODE_VALUES = {POWER_MODE_VALUE_HIGH,
                                                          POWER_MODE_VALUE_MIDDLE};
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mEntries = getResources().getTextArray(R.array.power_mode_entries);
        mEntryValues = POWER_MODE_VALUES;
        createPowerModeDialog();
    }

    private void createPowerModeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.power_mode);
        String powerMode = SystemProperties.get(POWER_MODE_KEY_PROPERTY,
                POWER_MODE_VALUE_DEFAULT);
        int clickedIndex = findIndexOfValue(powerMode);
        builder.setSingleChoiceItems(mEntries, clickedIndex,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int clickedIndex) {
                        if (clickedIndex < mEntryValues.length && clickedIndex >= 0) {
                            String powerMode = mEntryValues[clickedIndex].toString();
                            SystemProperties.set(POWER_MODE_KEY_PROPERTY, powerMode);
                        }
                        dialog.dismiss();
                        finish();
                    }
        });
        builder.setNeutralButton(android.R.string.cancel, null);

        Dialog dialog = builder.show();
        dialog.setOnDismissListener(new OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
    }

    public int findIndexOfValue(String value) {
        if (value != null && mEntryValues != null) {
            for (int i = mEntryValues.length - 1; i >= 0; i--) {
                if (mEntryValues[i].equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
