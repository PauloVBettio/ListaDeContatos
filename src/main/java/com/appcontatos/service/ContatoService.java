package com.appcontatos.service;

import com.appcontatos.dao.ContatoDAO;
import com.appcontatos.model.Contato;

import java.util.List;

public class ContatoService {
    private ContatoDAO contatoDAO;

    public ContatoService(ContatoDAO contatoDAO) {
        this.contatoDAO = contatoDAO;
    }

    public void adicionarContato(Contato contato) {
        contatoDAO.adicionarContato(contato);
    }

    public void removerContato(int id) {
        contatoDAO.removerContato(id);
    }

    public List<Contato> listarContatos() {
        return contatoDAO.listarContatos();
    }

    public Contato getContatoById(int id) {
        return contatoDAO.getContatoById(id);
    }

    public void atualizarContato(Contato contato) {
        contatoDAO.atualizarContato(contato);
    }

    public void fecharConexao() {
        contatoDAO.fecharConexao();
    }
}
