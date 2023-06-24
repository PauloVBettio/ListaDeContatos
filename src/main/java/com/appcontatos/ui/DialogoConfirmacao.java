package com.appcontatos.ui;

import javax.swing.JOptionPane;

public class DialogoConfirmacao {
    public static void showConfirmationDialog(String message) {
        int option = JOptionPane.showConfirmDialog(null, message, "Confirmação", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Ação a ser executada quando o usuário confirmar
            System.out.println("Usuário confirmou.");
        } else {
            // Ação a ser executada quando o usuário cancelar
            System.out.println("Usuário cancelou.");
        }
    }
}