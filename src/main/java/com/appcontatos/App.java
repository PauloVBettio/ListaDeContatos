package com.appcontatos;

import com.formdev.flatlaf.FlatDarkLaf;
import com.google.protobuf.ServiceException;
import com.appcontatos.dao.ContatoDAO;
import com.appcontatos.service.ContatoService;
import com.appcontatos.ui.TelaPrincipal;

import javax.swing.*;

public class App {
    public static void main(String[] args) throws ServiceException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        FlatDarkLaf.setup();

        ContatoDAO contatoDAO = new ContatoDAO();
        ContatoService contatoService = new ContatoService(contatoDAO);

        TelaPrincipal telaPrincipal = new TelaPrincipal(contatoService);
        telaPrincipal.setVisible(true);
    }
}
