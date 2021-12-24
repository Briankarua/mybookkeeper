package com.example.mybookkeeper;

import android.widget.CheckBox;

import java.util.HashSet;
import java.util.Set;

public class CheckBoxGroup {
    final Set<CheckBox> checkBoxes = new HashSet<>();
    CheckBox checked;

    public void addCheckBox(CheckBox checkBox) {
        checkBoxes.add(checkBox);
    }

    public void activate(CheckBox checkBox) {
        if (checked != checkBox && checked != null) {
            checked.setChecked(false);
        }
        checked = checkBox;
    }
}
