package com.tgcity.widget;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

/**
 * @author TGCity
 */
public class ClearViewFocusChangeListener implements View.OnFocusChangeListener {

    private View vClear;

    public ClearViewFocusChangeListener(View vClear) {
        this.vClear = vClear;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Editable text = ((EditText) v).getText();
        if (vClear != null) {
            if (hasFocus) {
                vClear.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
            } else {
                vClear.setVisibility(View.GONE);
            }
            v.setTag(hasFocus);
        }
    }
}
