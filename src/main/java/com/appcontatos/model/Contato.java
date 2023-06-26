package com.appcontatos.model;

import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A classe Contato representa um contato com informações como nome, telefone, email, favorito e cor.
 */
public class Contato {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private boolean favorite;
    private Color color;

    /**
     * Constrói um objeto Contato com o ID, nome, telefone, email, favorito e cor especificados.
     *
     * @param id       O ID do contato.
     * @param nome     O nome do contato.
     * @param telefone O telefone do contato.
     * @param email    O email do contato.
     * @param favorite Indica se o contato é um favorito.
     * @param color    A cor do contato.
     */
    public Contato(int id, String nome, String telefone, String email, boolean favorite, Color color) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.favorite = favorite;
        this.color = color;
    }

    /**
     * Constrói um objeto Contato com o nome, telefone, email, favorito e cor especificados.
     *
     * @param nome     O nome do contato.
     * @param telefone O telefone do contato.
     * @param email    O email do contato.
     * @param favorite Indica se o contato é um favorito.
     * @param color    A cor do contato.
     */
    public Contato(String nome, String telefone, String email, boolean favorite, Color color) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.favorite = favorite;
        this.color = color;
    }

    /**
     * Retorna o ID do contato.
     *
     * @return O ID do contato.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o nome do contato.
     *
     * @return O nome do contato.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do contato.
     *
     * @param nome O nome do contato.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o telefone do contato.
     *
     * @return O telefone do contato.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone do contato.
     *
     * @param telefone O telefone do contato.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna o email do contato.
     *
     * @return O email do contato.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do contato.
     *
     * @param email O email do contato.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Verifica se o contato é um favorito.
     *
     * @return true se o contato é um favorito, false caso contrário.
     */
    public boolean isFavorite() {
        return favorite;
    }

    /**
     * Define se o contato é um favorito.
     *
     * @param favorite Indica se o contato é um favorito.
     */
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    /**
     * Retorna a cor do contato.
     *
     * @return A cor do contato.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Retorna a representação hexadecimal da cor do contato.
     *
     * @return A representação hexadecimal da cor do contato.
     */
    public String getColorHex() {
        return String.format("%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Retorna a representação hexadecimal da cor especificada.
     *
     * @param color A cor para a qual a representação hexadecimal será obtida.
     * @return A representação hexadecimal da cor especificada.
     */
    public static String getColorHex(Color color) {
        return String.format("%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Define a cor do contato.
     *
     * @param color A cor do contato.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Formata o número de telefone especificado no formato "+## (##) #####-####".
     *
     * @param telefone O número de telefone a ser formatado.
     * @return O número de telefone formatado, ou uma string vazia se o telefone for vazio.
     */
    public static String getTelefoneFormatted(String telefone) {
        try {
            if (!telefone.equals("")) {
                MaskFormatter mf = new MaskFormatter("+## (##) #####-####");
                mf.setValueContainsLiteralCharacters(false);
                return mf.valueToString(telefone);
            } else {
                return "";
            }
        } catch (ParseException ex) {
            System.err.println(ex);
            return null;
        }
    }

    /**
     * Verifica se o email especificado é válido.
     *
     * @param email O email a ser verificado.
     * @return true se o email é válido, false caso contrário.
     */
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
