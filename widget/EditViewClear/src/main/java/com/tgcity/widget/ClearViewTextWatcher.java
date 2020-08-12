package com.tgcity.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * @author TGCity
 */
public class ClearViewTextWatcher implements TextWatcher {
    private View vClear;

    public ClearViewTextWatcher(View vClear) {
        this.vClear = vClear;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (vClear != null) {
            vClear.setVisibility(s.length() == 0 ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}