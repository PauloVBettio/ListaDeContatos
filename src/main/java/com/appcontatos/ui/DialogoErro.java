package com.appcontatos.ui;

import javax.swing.JOptionPane;

public class DialogoErro {
    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}