package com.appcontatos.service;

import com.appcontatos.dao.ContatoDAO;
import com.appcontatos.model.Contato;

import java.util.List;


/**
 * Esta classe fornece serviços relacionados aos contatos.
 */
public class ContatoService {
    private ContatoDAO contatoDAO;

    /**
     * Cria uma instância de ContatoService.
     *
     * @param contatoDAO o objeto ContatoDAO a ser usado para acessar os dados dos contatos
     */
    public ContatoService(ContatoDAO contatoDAO) {
        this.contatoDAO = contatoDAO;
    }

    /**
     * Adiciona um novo contato.
     *
     * @param contato o contato a ser adicionado
     */
    public void adicionarContato(Contato contato) {
        contatoDAO.adicionarContato(contato);
    }

    /**
     * Remove um contato pelo seu ID.
     *
     * @param id o ID do contato a ser removido
     */
    public void removerContato(int id) {
        contatoDAO.removerContato(id);
    }

    /**
     * Retorna uma lista de todos os contatos.
     *
     * @return a lista de contatos
     */
    public List<Contato> listarContatos() {
        return contatoDAO.listarContatos();
    }

    /**
     * Retorna um contato com base no seu ID.
     *
     * @param id o ID do contato
     * @return o contato com o ID correspondente, ou null se não for encontrado
     */
    public Contato getContatoById(int id) {
        return contatoDAO.getContatoById(id);
    }

    /**
     * Atualiza as informações de um contato.
     *
     * @param contato o contato a ser atualizado
     */
    public void atualizarContato(Contato contato) {
        contatoDAO.atualizarContato(contato);
    }

    /**
     * Fecha a conexão com a fonte de dados dos contatos.
     */
    public void fecharConexao() {
        contatoDAO.fecharConexao();
    }
}
