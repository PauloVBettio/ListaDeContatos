// TelaCadastroContato.java
package com.appcontatos.ui;

import com.google.protobuf.ServiceException;
import com.appcontatos.model.ComboColor;
import com.appcontatos.model.Contato;
import com.appcontatos.service.ContatoService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta classe representa a tela de cadastro de contato.
 */
public class TelaCadastroContato extends JFrame {
    private ContatoService contatoService;
    private TelaPrincipal telaPrincipal;
    private Contato contato;
    private JTextField txtNome;
    private JFormattedTextField txtTelefone;
    private JTextField txtEmail;
    private JRadioButton isFavoriteRadioButton;
    private JComboBox<ComboColor> cbColor;
    private MaskFormatter telefoneMaskFormatter;

    /**
     * Construtor da classe TelaCadastroContato para adicionar um novo contato.
     *
     * @param contatoService o serviço de gerenciamento de contatos
     * @param telaPrincipal  a tela principal do aplicativo
     */
    public TelaCadastroContato(ContatoService contatoService, TelaPrincipal telaPrincipal) {
        this.contatoService = contatoService;
        this.telaPrincipal = telaPrincipal;

        setTitle("Cadastro de Contato");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    /**
     * Construtor da classe TelaCadastroContato para editar um contato existente.
     *
     * @param contatoService o serviço de gerenciamento de contatos
     * @param telaPrincipal  a tela principal do aplicativo
     * @param contato        o contato a ser editado
     */
    public TelaCadastroContato(ContatoService contatoService, TelaPrincipal telaPrincipal, Contato contato) {
        this.contatoService = contatoService;
        this.telaPrincipal = telaPrincipal;
        this.contato = contato;

        setTitle("Alteração de Contato");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        initComponents();

        // Preencher os campos com os dados do contato a ser alterado
        txtNome.setText(contato.getNome());
        txtTelefone.setText(contato.getTelefone());
        txtEmail.setText(contato.getEmail());
        cbColor.setSelectedItem(contato.getColor());
        isFavoriteRadioButton.setSelected(contato.isFavorite());

        setVisible(true);
    }

    /**
     * Inicializa e configura os componentes da interface gráfica.
     */
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        panel.setLayout(new GridLayout(6, 2));

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblTelefone = new JLabel("Telefone:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblFavorite = new JLabel("Favorito:");
        JLabel lblColor = new JLabel("Cor:");

        txtNome = new JTextField();
        limitarTamanhoCampo(txtNome, 255);

        txtTelefone = new JFormattedTextField();
        try {
            telefoneMaskFormatter = new MaskFormatter("+## (##) #####-####");
            telefoneMaskFormatter.setPlaceholderCharacter('_');
            telefoneMaskFormatter.install(txtTelefone);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtEmail = new JTextField();
        limitarTamanhoCampo(txtEmail, 255);

        isFavoriteRadioButton = new JRadioButton();
        cbColor = new JComboBox<>();
        cbColor.addItem(new ComboColor(Color.red, "Vermelho"));
        cbColor.addItem(new ComboColor(Color.blue, "Azul"));
        cbColor.addItem(new ComboColor(Color.cyan, "Ciano"));
        cbColor.addItem(new ComboColor(Color.green, "Verde"));
        cbColor.addItem(new ComboColor(Color.pink, "Rosa"));
        cbColor.addItem(new ComboColor(Color.orange, "Laranja"));
        cbColor.addItem(new ComboColor(Color.magenta, "Magenta"));

        JButton btnSalvar = new JButton("Salvar");
        setupSave(btnSalvar);

        panel.add(lblNome);
        panel.add(txtNome);
        panel.add(lblTelefone);
        panel.add(txtTelefone);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblColor);
        panel.add(cbColor);
        panel.add(lblFavorite);
        panel.add(isFavoriteRadioButton);
        panel.add(btnSalvar);

        add(panel);
    }

    /**
     * Limita o tamanho máximo de um campo de texto.
     *
     * @param textField o campo de texto a ser limitado
     * @param maxLength o tamanho máximo permitido para o campo de texto
     */
    private static void limitarTamanhoCampo(JTextComponent textField, int maxLength) {
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                int currentLength = fb.getDocument().getLength();
                int insertionLength = text.length();
                if (currentLength - length + insertionLength <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    /**
     * Configura o botão de salvar para adicionar ou atualizar um contato.
     *
     * @param btnSalvar o botão de salvar
     */
    private void setupSave(JButton btnSalvar) {
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String telefone = txtTelefone.getText().replaceAll("[()_+ -]", "");
                String email = txtEmail.getText();
                boolean favorite = isFavoriteRadioButton.isSelected();
                Color color = cbColor.getSelectedItem() != null ? ((ComboColor)cbColor.getSelectedItem()).getValue() : Color.black;

                if (!email.isEmpty()) {
                    if (!Contato.isValidEmail(email)) {
                        JOptionPane.showMessageDialog(null, "Email inválido", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                else if (nome.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Insira um nome", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (contato == null) {
                    // Criar um novo contato
                    Contato novoContato = new Contato(nome, telefone, email, favorite, color);
                    contatoService.adicionarContato(novoContato);
                } else {
                    // Atualizar o contato existente
                    contato.setNome(nome);
                    contato.setTelefone(telefone);
                    contato.setEmail(email);
                    contato.setFavorite(favorite);
                    contato.setColor(color);
                    contatoService.atualizarContato(contato);
                }

                try {
                    telaPrincipal.atualizarTabelaContatos();
                } catch (ServiceException ex) {
                    Logger.getLogger(TelaCadastroContato.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose();
            }
        });
    }
}
