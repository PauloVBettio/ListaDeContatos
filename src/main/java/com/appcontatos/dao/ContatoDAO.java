// ContatoDAO.java
package com.appcontatos.dao;

import com.appcontatos.model.Contato;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A classe ContatoDAO é responsável por lidar com as operações de acesso aos dados para os objetos Contato.
 * Ela fornece métodos para realizar operações CRUD (Create, Read, Update, Delete) no banco de dados.
 */
public class ContatoDAO {
    private Connection connection;

    /**
     * Constrói um objeto ContatoDAO e inicializa a conexão com o banco de dados.
     */
    public ContatoDAO() {
        try {
            // Caminho para o diretório contatos no diretório atual do projeto
            String directoryPath = "./contatos";

            // Verifica se o diretório contatos não existe
            if (!Files.exists(Paths.get(directoryPath))) {
                // Cria o diretório contatos
                Files.createDirectories(Paths.get(directoryPath));
            }

            // Caminho completo para o banco de dados H2
            String dbPath = directoryPath + "/contatos";

            // JDBC URL for connecting to the H2 database
            String jdbcUrl = "jdbc:h2:" + dbPath;

            // Register the H2 JDBC driver
            Class.forName("org.h2.Driver");

            // Conexão com o banco de dados H2
            connection = DriverManager.getConnection(jdbcUrl, "sa", "");

            // Criação da tabela caso ela não exista
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS contatos (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "nome VARCHAR(255) NOT NULL," +
                    "telefone VARCHAR(20) NOT NULL," +
                    "email VARCHAR(255) NOT NULL," +
                    "favorite BOOL NOT NULL," +
                    "color VARCHAR(6) NOT NULL" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retorna um objeto Contato com base no seu ID.
     *
     * @param id O ID do contato a ser retornado.
     * @return O objeto Contato correspondente ao ID fornecido, ou null se não for encontrado.
     */
    public Contato getContatoById(int id) {
        String sql = "SELECT * FROM contatos WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int contatoId = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String telefone = rs.getString("telefone");
                    String email = rs.getString("email");
                    boolean favorite = rs.getBoolean("favorite");
                    String color = rs.getString("color");

                    return new Contato(contatoId, nome, telefone, email, favorite, Color.decode("#" + color));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Atualiza as informações de um contato existente no banco de dados.
     *
     * @param contato O objeto Contato contendo as informações atualizadas.
     */
    public void atualizarContato(Contato contato) {
        String query = "UPDATE contatos SET nome = ?, telefone = ?, email = ?, favorite = ?, color = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, contato.getNome());
            statement.setString(2, contato.getTelefone());
            statement.setString(3, contato.getEmail());
            statement.setBoolean(4, contato.isFavorite());
            statement.setString(5, contato.getColorHex());
            statement.setInt(6, contato.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adiciona um novo contato ao banco de dados.
     *
     * @param contato O objeto Contato a ser adicionado.
     */
    public void adicionarContato(Contato contato) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO contatos (nome, telefone, email, favorite, color) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, contato.getNome());
            preparedStatement.setString(2, contato.getTelefone());
            preparedStatement.setString(3, contato.getEmail());
            preparedStatement.setBoolean(4, contato.isFavorite());
            preparedStatement.setString(5, contato.getColorHex());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove um contato do banco de dados com base no seu ID.
     *
     * @param id O ID do contato a ser removido.
     */
    public void removerContato(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM contatos WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna uma lista de todos os contatos no banco de dados, ordenados por favoritos e nome.
     *
     * @return Uma lista de objetos Contato representando todos os contatos no banco de dados.
     */
    public List<Contato> listarContatos() {
        List<Contato> contatos = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contatos ORDER BY favorite DESC, nome ASC");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String email = resultSet.getString("email");
                boolean favorite = resultSet.getBoolean("favorite");
                Color color = Color.decode("#" + resultSet.getString("color"));

                Contato contato = new Contato(id, nome, telefone, email, favorite, color);
                contatos.add(contato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contatos;
    }

    /**
     * Fecha a conexão com o banco de dados.
     */
    public void fecharConexao() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
