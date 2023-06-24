// TelaPrincipal.java
package com.appcontatos.ui;

import com.google.protobuf.ServiceException;
import com.appcontatos.model.Contato;
import com.appcontatos.service.ContatoService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelaPrincipal extends JFrame {
    private ContatoService contatoService;
    private JTable tabelaContatos;
    private JTextField txtPesquisar;
    private JButton btnPesquisar;
    private DefaultTableModel modelContatos;

    public TelaPrincipal(ContatoService contatoService) throws ServiceException {
        this.contatoService = contatoService;

        setTitle("Agenda de Contatos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        initComponents();
        initMenu();
        atualizarTabelaContatos();

        setVisible(true);
    }

    private void initMenu() {

    }

    private void initComponents() {
        // Painel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        panelPrincipal.setLayout(new BorderLayout());

        txtPesquisar = new JTextField();
        txtPesquisar.setMargin(new Insets(8, 8, 8, 8));
        txtPesquisar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                filtrarTabela();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // Definir o texto de placeholder
        String placeholder = "Pesquisar";
        txtPesquisar.setText(placeholder);
        txtPesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtPesquisar.getText().equals(placeholder)) {
                    txtPesquisar.setText("");
                    txtPesquisar.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtPesquisar.getText().isEmpty()) {
                    txtPesquisar.setText(placeholder);
                    txtPesquisar.setForeground(Color.GRAY);
                }
            }
        });

        btnPesquisar = new JButton("Pesquisar");

        JPanel panelPesquisa = new JPanel(new BorderLayout());
        panelPesquisa.setBorder(BorderFactory.createEmptyBorder(16, 32, 0, 32));
        panelPesquisa.add(txtPesquisar, BorderLayout.CENTER);

        add(panelPesquisa, BorderLayout.NORTH);

        btnPesquisar.addActionListener(e -> filtrarTabela());

        // Tabela de contatos
        modelContatos = new DefaultTableModel();
        modelContatos.addColumn("ID");
        modelContatos.addColumn("Nome");
        modelContatos.addColumn("Telefone");
        modelContatos.addColumn("Email");
        modelContatos.addColumn("Cor");

        tabelaContatos = new JTable(modelContatos);
        tabelaContatos.setDefaultEditor(Object.class, null);
        tabelaContatos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                int modelRow = table.convertRowIndexToModel(row);

                Color color = (Color) table.getModel().getValueAt(modelRow, 4);
                // original coefficients
                final double cr = 0.241;
                final double cg = 0.691;
                final double cb = 0.068;
                // another set of coefficients
                //      final double cr = 0.299;
                //      final double cg = 0.587;
                //      final double cb = 0.114;

                double r, g, b;
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();

                // compute the weighted distance
                double brightness = Math.sqrt(cr * r * r + cg * g * g + cb * b * b);

                setBackground(color);
                setForeground(brightness > 127 ? Color.black : Color.white);
                return this;
            }
        });
        JScrollPane scrollPane = new JScrollPane(tabelaContatos);

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Cria uma barra de menu para o JFrame
        JMenuBar menuBar = new JMenuBar();

        // Adiciona a barra de menu ao  frame
        setJMenuBar(menuBar);

        // Define e adiciona dois menus drop down na barra de menus
        JMenu fileMenu = new JMenu("Arquivo");
        JMenu editMenu = new JMenu("Editar");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        JMenuItem newAction = new JMenuItem("Novo contato");
        newAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastroContato();
            }
        });

        JMenuItem removeAction = new JMenuItem("Remover contato");
        removeAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabelaContatos.getSelectedRow();
                if (linhaSelecionada != -1) {
                    // Exibir uma caixa de diálogo de confirmação
                    int confirmation = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover o contato?", "Confirmação de Remoção", JOptionPane.YES_NO_OPTION);

                    if (confirmation == JOptionPane.YES_OPTION) {
                        int idContato = (int) tabelaContatos.getValueAt(linhaSelecionada, 0);
                        contatoService.removerContato(idContato);
                    }
                    try {
                        atualizarTabelaContatos();
                    } catch (ServiceException ex) {
                        Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum contato selecionado. Selecione um contato da lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JMenuItem editAction = new JMenuItem("Editar contato");
        editAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabelaContatos.getSelectedRow();
                if (linhaSelecionada != -1) {
                    int idContato = (int) tabelaContatos.getValueAt(linhaSelecionada, 0);
                    abrirTelaAlteracaoContato(idContato);
                }
            }
        });

        tabelaContatos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linhaSelecionada = tabelaContatos.getSelectedRow();
                    if (linhaSelecionada != -1) {
                        int idContato = (int) tabelaContatos.getValueAt(linhaSelecionada, 0);
                        abrirTelaAlteracaoContato(idContato);
                    }
                }
            }
        });

        fileMenu.add(newAction);

        editMenu.add(removeAction);
        editMenu.add(editAction);

        // Adicionar painel principal à tela
        add(panelPrincipal);
    }

    private void filtrarTabela() {
        String textoPesquisa = txtPesquisar.getText().trim().toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelContatos);
        tabelaContatos.setRowSorter(sorter);

        if (textoPesquisa.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            RowFilter<DefaultTableModel, Object> filtro = RowFilter.regexFilter("(?i)" + textoPesquisa);
            sorter.setRowFilter(filtro);
        }
        modelContatos.fireTableDataChanged();
        tabelaContatos.repaint();
        tabelaContatos.revalidate();
    }

    private void abrirTelaCadastroContato() {
        TelaCadastroContato telaCadastro = new TelaCadastroContato(contatoService, this);
        telaCadastro.setVisible(true);
    }

    private void abrirTelaAlteracaoContato(int idContato) {
        Contato contato = contatoService.getContatoById(idContato);
        if (contato != null) {
            TelaCadastroContato telaCadastro = new TelaCadastroContato(contatoService, this, contato);
            telaCadastro.setVisible(true);
        }
    }

    public void atualizarTabelaContatos() throws ServiceException {
        List<Contato> contatos = contatoService.listarContatos();

        modelContatos.setRowCount(0);

        for (Contato contato : contatos) {
            Object[] rowData = {
                    contato.getId(),
                    contato.getNome(),
                    contato.getTelefone(),
                    contato.getEmail(),
                    contato.getColor(),
            };
            modelContatos.addRow(rowData);
        }
    }
}
