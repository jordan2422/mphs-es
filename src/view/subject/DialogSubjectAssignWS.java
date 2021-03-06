/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moph_ui;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class DialogSubjectAssignWS extends javax.swing.JDialog {

    /**
     * Creates new form dialog_subjectassignWS
     */
    public DialogSubjectAssignWS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panel_toppanel = new javax.swing.JPanel();
        panel_selection = new javax.swing.JPanel();
        lbl_section1 = new javax.swing.JLabel();
        combo_section1 = new javax.swing.JComboBox<>();
        lbl_section2 = new javax.swing.JLabel();
        combo_section2 = new javax.swing.JComboBox<>();
        panel_rawscores = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_rawscores = new javax.swing.JTable();
        panel_footer = new javax.swing.JPanel();
        btn_cancel = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_saveandclose = new javax.swing.JButton();
        panel_multiplier = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jTextField1 = new javax.swing.JTextField();
        jCheckBox2 = new javax.swing.JCheckBox();
        jTextField2 = new javax.swing.JTextField();
        jCheckBox3 = new javax.swing.JCheckBox();
        jTextField3 = new javax.swing.JTextField();
        btn_View = new javax.swing.JButton();
        btn_View1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Assign Weighted Scores(WS)");
        setMinimumSize(new java.awt.Dimension(1000, 700));
        setModal(true);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        panel_toppanel.setLayout(new java.awt.GridBagLayout());

        panel_selection.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Selection", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        panel_selection.setMinimumSize(new java.awt.Dimension(500, 120));
        panel_selection.setPreferredSize(new java.awt.Dimension(500, 120));
        panel_selection.setLayout(new java.awt.GridBagLayout());

        lbl_section1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_section1.setText("Subject Code :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panel_selection.add(lbl_section1, gridBagConstraints);

        combo_section1.setEditable(true);
        combo_section1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        combo_section1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "G1English" }));
        combo_section1.setMinimumSize(new java.awt.Dimension(100, 25));
        combo_section1.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        panel_selection.add(combo_section1, gridBagConstraints);

        lbl_section2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_section2.setText("Grade Level");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        panel_selection.add(lbl_section2, gridBagConstraints);

        combo_section2.setEditable(true);
        combo_section2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        combo_section2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1" }));
        combo_section2.setMinimumSize(new java.awt.Dimension(100, 25));
        combo_section2.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        panel_selection.add(combo_section2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 0.5;
        panel_toppanel.add(panel_selection, gridBagConstraints);

        panel_rawscores.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Results", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        panel_rawscores.setMinimumSize(new java.awt.Dimension(1000, 520));
        panel_rawscores.setPreferredSize(new java.awt.Dimension(1000, 520));
        panel_rawscores.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setMinimumSize(new java.awt.Dimension(980, 490));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(980, 490));

        table_rawscores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Subject Code", "Name/Description", "Weighted Score (WW)", "Weighted Score (PT)", "Weighted Score (QA)"
            }
        ));
        table_rawscores.setRowHeight(20);
        jScrollPane2.setViewportView(table_rawscores);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel_rawscores.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        panel_toppanel.add(panel_rawscores, gridBagConstraints);

        panel_footer.setMinimumSize(new java.awt.Dimension(1000, 50));
        panel_footer.setPreferredSize(new java.awt.Dimension(1000, 50));
        panel_footer.setLayout(new java.awt.GridBagLayout());

        btn_cancel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_cancel.setText("Cancel");
        btn_cancel.setMaximumSize(new java.awt.Dimension(69, 40));
        btn_cancel.setMinimumSize(new java.awt.Dimension(69, 40));
        btn_cancel.setPreferredSize(new java.awt.Dimension(69, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.5;
        panel_footer.add(btn_cancel, gridBagConstraints);

        btn_clear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_clear.setText("Clear");
        btn_clear.setMaximumSize(new java.awt.Dimension(60, 40));
        btn_clear.setMinimumSize(new java.awt.Dimension(60, 40));
        btn_clear.setPreferredSize(new java.awt.Dimension(60, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        panel_footer.add(btn_clear, gridBagConstraints);

        btn_save.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_save.setText("Save");
        btn_save.setMaximumSize(new java.awt.Dimension(59, 40));
        btn_save.setMinimumSize(new java.awt.Dimension(59, 40));
        btn_save.setPreferredSize(new java.awt.Dimension(59, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        panel_footer.add(btn_save, gridBagConstraints);

        btn_saveandclose.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_saveandclose.setText("Save & Close");
        btn_saveandclose.setMaximumSize(new java.awt.Dimension(120, 40));
        btn_saveandclose.setMinimumSize(new java.awt.Dimension(120, 40));
        btn_saveandclose.setPreferredSize(new java.awt.Dimension(120, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        panel_footer.add(btn_saveandclose, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 30, 0);
        panel_toppanel.add(panel_footer, gridBagConstraints);

        panel_multiplier.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Weighted Score Multiplier", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        panel_multiplier.setMinimumSize(new java.awt.Dimension(500, 120));
        panel_multiplier.setPreferredSize(new java.awt.Dimension(500, 120));
        panel_multiplier.setLayout(new java.awt.GridBagLayout());

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox1.setText("Written Work (WW)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panel_multiplier.add(jCheckBox1, gridBagConstraints);

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField1.setText("30%");
        jTextField1.setMinimumSize(new java.awt.Dimension(50, 30));
        jTextField1.setPreferredSize(new java.awt.Dimension(50, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        panel_multiplier.add(jTextField1, gridBagConstraints);

        jCheckBox2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox2.setText("Performance Tasks (PT)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panel_multiplier.add(jCheckBox2, gridBagConstraints);

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField2.setText("40%");
        jTextField2.setMinimumSize(new java.awt.Dimension(50, 30));
        jTextField2.setPreferredSize(new java.awt.Dimension(50, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        panel_multiplier.add(jTextField2, gridBagConstraints);

        jCheckBox3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox3.setText("Quarterly Assessment (QA)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        panel_multiplier.add(jCheckBox3, gridBagConstraints);

        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField3.setText("30%");
        jTextField3.setMinimumSize(new java.awt.Dimension(50, 30));
        jTextField3.setPreferredSize(new java.awt.Dimension(50, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        panel_multiplier.add(jTextField3, gridBagConstraints);

        btn_View.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_View.setText("Plot");
        btn_View.setMaximumSize(new java.awt.Dimension(80, 31));
        btn_View.setMinimumSize(new java.awt.Dimension(80, 31));
        btn_View.setPreferredSize(new java.awt.Dimension(80, 31));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        panel_multiplier.add(btn_View, gridBagConstraints);

        btn_View1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_View1.setText("Delete");
        btn_View1.setMaximumSize(new java.awt.Dimension(80, 31));
        btn_View1.setMinimumSize(new java.awt.Dimension(80, 31));
        btn_View1.setPreferredSize(new java.awt.Dimension(80, 31));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        panel_multiplier.add(btn_View1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 0.5;
        panel_toppanel.add(panel_multiplier, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(panel_toppanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogSubjectAssignWS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogSubjectAssignWS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogSubjectAssignWS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogSubjectAssignWS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogSubjectAssignWS dialog = new DialogSubjectAssignWS(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_View;
    private javax.swing.JButton btn_View1;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_saveandclose;
    private javax.swing.JComboBox<String> combo_section1;
    private javax.swing.JComboBox<String> combo_section2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lbl_section1;
    private javax.swing.JLabel lbl_section2;
    private javax.swing.JPanel panel_footer;
    private javax.swing.JPanel panel_multiplier;
    private javax.swing.JPanel panel_rawscores;
    private javax.swing.JPanel panel_selection;
    private javax.swing.JPanel panel_toppanel;
    private javax.swing.JTable table_rawscores;
    // End of variables declaration//GEN-END:variables
}
