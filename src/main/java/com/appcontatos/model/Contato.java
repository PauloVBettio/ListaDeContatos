package com.appcontatos.model;

import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.MessageFormat;
import java.text.ParseException;

public class Contato {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private boolean favorite;
    private Color color;

    public Contato(int id, String nome, String telefone, String email, boolean favorite, Color color) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.favorite = favorite;
        this.color = color;
    }
    
    public Contato(String nome, String telefone, String email, boolean favorite, Color color) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.favorite = favorite;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Color getColor() {
        return color;
    }

    public String getColorHex() {
        return String.format("%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public void setColor(Color color) {
        this.color = color;
    }


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
}
