package com.appcontatos.ui;

import javax.swing.JOptionPane;

/**
 * Esta classe exibe um diálogo de erro.
 */
public class DialogoErro {
    /**
     * Exibe um diálogo de erro com a mensagem especificada.
     *
     * @param message a mensagem de erro
     */
    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}