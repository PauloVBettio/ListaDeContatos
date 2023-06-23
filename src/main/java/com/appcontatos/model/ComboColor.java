package com.appcontatos.model;

import java.awt.*;

public class ComboColor {
    private Color value;
    private String text;

    public ComboColor(Color value, String text) {
        this.value = value;
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public Color getValue() {
        return value;
    }

    public void setValue(Color value) {
        this.value = value;
    }
}
