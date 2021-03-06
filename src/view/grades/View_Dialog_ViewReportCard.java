package view.grades;

import component_renderers.Renderer_Faculty_JComboBox;
import component_renderers.Renderer_GradeLevel_JComboBox;
import component_renderers.Renderer_Grade_ReportCard_JTableCell;
import component_renderers.Renderer_ReportCard_StudentName_JComboBox;
import component_renderers.Renderer_Section_JComboBox;
import component_renderers.Renderer_Student_JComboBox;
import controller.global.Controller_JButton_ExitJDialog;
import controller.global.Controller_Print_JButton;
import daoimpl.GradeDaoImpl;
import daoimpl.SchoolYearDaoImpl;
import daoimpl.SectionDaoImpl;
import daoimpl.SubjectDaoImpl;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.print.PageFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import model.grade.Grade;
import model.schoolyear.SchoolYear;
import model.section.Section;
import model.student.Student;
import model.subject.Subject;
import utility.component.ImageUtil;
import utility.initializer.Initializer;

public class View_Dialog_ViewReportCard extends javax.swing.JDialog implements Initializer {

    private final Student student;
    private SectionDaoImpl sectionDaoImpl;
    private SubjectDaoImpl subjectDaoImpl;
    private GradeDaoImpl gradeDaoImpl;
    private SchoolYearDaoImpl schoolYearDaoImpl;
    private Image schoolLogo;

    public View_Dialog_ViewReportCard(java.awt.Frame parent, boolean modal, Student student) {
        super(parent, modal);
        initComponents();
        schoolLogo = new ImageUtil().getResourceAsImage("assets/logo.png", 200, 200);
        this.student = student;

        UIManager.put("ComboBox.disabledBackground", new Color(212, 212, 210));
        UIManager.put("ComboBox.disabledForeground", Color.BLACK);

        initDaoImpl();
        initRenderers();
        initJCompModelLoaders();
        initControllers();
        initViewComponents();

        initForm();
    }

    @Override
    public void initGridBagConstraints() {
    }

    @Override
    public void initJCompModelLoaders() {
    }

    @Override
    public void initRenderers() {
        jtblReportCard.setDefaultRenderer(Object.class, new Renderer_Grade_ReportCard_JTableCell(0));
        jcmbGradeLevel.setRenderer(new Renderer_GradeLevel_JComboBox());
        jcmbAdviser.setRenderer(new Renderer_Faculty_JComboBox());
        jcmbSection.setRenderer(new Renderer_Section_JComboBox());
        jcmbStudentId.setRenderer(new Renderer_Student_JComboBox());
        jcmbStudentName.setRenderer(new Renderer_ReportCard_StudentName_JComboBox());
    }

    @Override
    public void initModels() {
    }

    @Override
    public void initViewComponents() {
    }

    @Override
    public void initControllers() {
        jbtnCancel.addActionListener(new Controller_JButton_ExitJDialog(this));
        jtblReportCard.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getColumn() == 1 || e.getColumn() == 2 || e.getColumn() == 3 || e.getColumn() == 4) {
                    DefaultTableModel tModel = (DefaultTableModel) jtblReportCard.getModel();
                    if (tModel.getRowCount() > 0) {
                        for (int row = 0; row < tModel.getRowCount(); row++) {
                            BigDecimal sum = new BigDecimal(BigInteger.ZERO);
                            BigDecimal divisor = new BigDecimal(BigInteger.ZERO);
                            for (int col = 0; col < tModel.getColumnCount(); col++) {
                                if (col == 1 || col == 2 || col == 3 || col == 4) {
                                    if (tModel.getValueAt(row, col) != null) {
                                        sum = sum.add(BigDecimal.valueOf(Double.parseDouble(tModel.getValueAt(row, col).toString().trim())));
                                        divisor = divisor.add(BigDecimal.ONE);
                                    }
                                }
                            }
                            if (tModel.getValueAt(row, 4) != null && isInteger(tModel.getValueAt(row, 4))) {
                                if(Integer.parseInt(tModel.getValueAt(row, 4).toString().trim()) != 0){
                                    setFinalGradeAtRows(5, 6, tModel, divisor, sum, row);
                                }
                            }
                        }
                    }
                }
            }
        });
        
        jbtnPrint.addActionListener(new Controller_Print_JButton(panel_gradingTable,PageFormat.PORTRAIT));
    }

    private void setFinalGradeAtRows(int fgRawRow, int fgTransmutedRow, DefaultTableModel tModel, BigDecimal divisor, BigDecimal rowSum, int currentRow) {
        if (divisor.compareTo(BigDecimal.ZERO) == 1) {
            BigDecimal finalGrade = rowSum.divide(divisor, 2, RoundingMode.HALF_UP);
            BigDecimal transmuted = finalGrade.round(new MathContext(2, RoundingMode.HALF_UP));
            tModel.setValueAt(finalGrade, currentRow, fgRawRow);
            tModel.setValueAt(transmuted, currentRow, fgTransmutedRow);
        }
    }
    
    private boolean gradesCompleteFor(JTable t, int nthGrading){
        boolean isComplete = true;
        for(int row = 0; row < t.getRowCount(); row++){
            if(t.getValueAt(row, nthGrading) != null){
                if(!t.getValueAt(row, nthGrading).toString().trim().isEmpty()){
                    int value = Integer.parseInt(t.getValueAt(row, nthGrading).toString().trim());
                    if(value != 0){
                        isComplete = isComplete && true;
                    }else{
                        isComplete = false;
                        break;
                    }
                }
            }
        }
        return isComplete;
    }
    
    private void setGeneralAverage(JTable t){
        List<Integer> averageCols = Arrays.asList(1, 2, 3, 4);
        BigDecimal sum = new BigDecimal(BigInteger.ZERO);
        for (int col : averageCols) {
            int lastRowIndex = t.getRowCount() - 1;
            if (t.getValueAt(lastRowIndex, col) != null) {
                sum = sum.add(BigDecimal.valueOf(Double.parseDouble(t.getValueAt(lastRowIndex, col).toString().trim())));
            }
        }
        BigDecimal average = sum.divide(BigDecimal.valueOf(4),2,RoundingMode.HALF_UP);
        jtfReportCardGeneralAverage.setText(""+average);
    }
    
    private void setAverageFor(JTable t, int nthGradingPeriod) {
        if (gradesCompleteFor(t, nthGradingPeriod)) {
            BigDecimal average = new BigDecimal(BigInteger.ZERO);
            BigDecimal sum = new BigDecimal(BigInteger.ZERO);
            for (int row = 0; row < t.getRowCount() - 1; row++) {
                if (isInteger(t.getValueAt(row, nthGradingPeriod))) {
                    sum = sum.add(BigDecimal.valueOf(Double.parseDouble(t.getValueAt(row, nthGradingPeriod).toString().trim())));
                }
            }
            int subjectRowCount = t.getRowCount() - 1;
            average = sum.divide(BigDecimal.valueOf(subjectRowCount),2, RoundingMode.HALF_UP);
            int averageRowIndex = t.getRowCount()-1;
            t.setValueAt(average, averageRowIndex, nthGradingPeriod);
        }
    }
    
    private boolean isInteger(Object number) {
        boolean isInteger = false;
        if (number != null) {
            try {
                Integer.parseInt(number.toString().trim());
                isInteger = true;
            } catch (NumberFormatException e) {
                isInteger = false;
            }
        }
        return isInteger;
    }
    
    @Override
    public void initDaoImpl() {
        gradeDaoImpl = new GradeDaoImpl();
        schoolYearDaoImpl = new SchoolYearDaoImpl();
        sectionDaoImpl = new SectionDaoImpl();
        subjectDaoImpl = new SubjectDaoImpl();
    }

    private void initForm() {
        DefaultComboBoxModel studentComboModel = new DefaultComboBoxModel();
        studentComboModel.addElement(student);
        jcmbStudentId.setModel(studentComboModel);
        jcmbStudentName.setModel(studentComboModel);

        SchoolYear schoolYear = schoolYearDaoImpl.getCurrentSchoolYear();
        DefaultComboBoxModel sectionComboModel = new DefaultComboBoxModel();
        Section section = sectionDaoImpl.getSectionOf(student, schoolYear);
        sectionComboModel.addElement(section);
        jcmbSection.setModel(sectionComboModel);

        DefaultComboBoxModel adviserComboModel = new DefaultComboBoxModel();
        adviserComboModel.addElement(section.getAdviser());
        jcmbAdviser.setModel(adviserComboModel);

        jcmbSession.setSelectedItem(section.getSectionSession().trim());

        DefaultComboBoxModel gradeLevelComboModel = new DefaultComboBoxModel();
        gradeLevelComboModel.addElement(section.getGradeLevel());
        jcmbGradeLevel.setModel(gradeLevelComboModel);

        List<Subject> subjects = subjectDaoImpl.getAllSubjectsByGradeLevelId(section.getGradeLevel().getGradeLevelId());
        DefaultTableModel tableModel = (DefaultTableModel) jtblReportCard.getModel();
        for (Subject s : subjects) {
            Object[] rowData = {s};
            tableModel.addRow(rowData);
        }
        tableModel.addRow(new Object[]{"Average"});
        jtblReportCard.setModel(tableModel);

        setGradesToCells();
        setAverageFor(jtblReportCard, 1);
        setAverageFor(jtblReportCard, 2);
        setAverageFor(jtblReportCard, 3);
        setAverageFor(jtblReportCard, 4);
        setGeneralAverage(jtblReportCard);
    }

    private void setGradesToCells() {
        DefaultTableModel tableModel = (DefaultTableModel) jtblReportCard.getModel();
        SchoolYear schoolYear = schoolYearDaoImpl.getCurrentSchoolYear();
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (tableModel.getValueAt(row, 0) instanceof Subject) {
                Subject subject = (Subject) tableModel.getValueAt(row, 0);
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    if (col == 1) {
                        int gradingPeriod = 1;
                        Grade grade = gradeDaoImpl.getGradeBySubjectGradingPeriodSchoolYearAndStudent(subject, gradingPeriod, schoolYear, student);
                        tableModel.setValueAt(grade.getValue(), row, col);
                    } else if (col == 2) {
                        int gradingPeriod = 2;
                        Grade grade = gradeDaoImpl.getGradeBySubjectGradingPeriodSchoolYearAndStudent(subject, gradingPeriod, schoolYear, student);
                        tableModel.setValueAt(grade.getValue(), row, col);
                    } else if (col == 3) {
                        int gradingPeriod = 3;
                        Grade grade = gradeDaoImpl.getGradeBySubjectGradingPeriodSchoolYearAndStudent(subject, gradingPeriod, schoolYear, student);
                        tableModel.setValueAt(grade.getValue(), row, col);
                    } else if (col == 4) {
                        int gradingPeriod = 4;
                        Grade grade = gradeDaoImpl.getGradeBySubjectGradingPeriodSchoolYearAndStudent(subject, gradingPeriod, schoolYear, student);
                        tableModel.setValueAt(grade.getValue(), row, col);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        panel_toppanel = new javax.swing.JPanel();
        panel_footer = new javax.swing.JPanel();
        jbtnCancel = new javax.swing.JButton();
        jbtnRefresh = new javax.swing.JButton();
        jbtnPrint = new javax.swing.JButton();
        panel_gradingTable = new javax.swing.JPanel();
        panel_gradingtop = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblReportCard = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jlblReportCardGeneralAverage = new javax.swing.JLabel();
        jtfReportCardGeneralAverage = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        panel_details1 = new javax.swing.JPanel();
        lbl_name2 = new javax.swing.JLabel();
        jcmbStudentId = new javax.swing.JComboBox<>();
        lbl_name9 = new javax.swing.JLabel();
        jcmbGradeLevel = new javax.swing.JComboBox<>();
        lbl_name10 = new javax.swing.JLabel();
        lbl_name11 = new javax.swing.JLabel();
        jcmbSection = new javax.swing.JComboBox<>();
        jcmbStudentName = new javax.swing.JComboBox<>();
        lbl_name16 = new javax.swing.JLabel();
        jcmbAdviser = new javax.swing.JComboBox<>();
        lbl_name17 = new javax.swing.JLabel();
        jcmbSession = new javax.swing.JComboBox<>();
        jpnlHeader1 = new javax.swing.JPanel();
        jpnlClassListLogo = new javax.swing.JPanel(){
            public void paintComponent(Graphics g){
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.8f));
            g2d.drawImage(schoolLogo, 0, 0, getWidth(), getHeight(), null);
            panel_gradingTable.repaint();
        }
    };
    jPanel14 = new javax.swing.JPanel();
    jLabel34 = new javax.swing.JLabel();
    jLabel35 = new javax.swing.JLabel();
    jLabel36 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Student Report Card");
    setMinimumSize(new java.awt.Dimension(800, 600));
    setModal(true);
    getContentPane().setLayout(new java.awt.GridBagLayout());

    panel_toppanel.setMinimumSize(new java.awt.Dimension(800, 700));
    panel_toppanel.setLayout(new java.awt.GridBagLayout());

    panel_footer.setMinimumSize(new java.awt.Dimension(795, 30));
    panel_footer.setPreferredSize(new java.awt.Dimension(795, 30));
    panel_footer.setLayout(new java.awt.GridBagLayout());

    jbtnCancel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jbtnCancel.setText("Cancel");
    jbtnCancel.setMaximumSize(new java.awt.Dimension(69, 40));
    jbtnCancel.setMinimumSize(new java.awt.Dimension(100, 30));
    jbtnCancel.setPreferredSize(new java.awt.Dimension(100, 30));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_footer.add(jbtnCancel, gridBagConstraints);

    jbtnRefresh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jbtnRefresh.setText("Refresh");
    jbtnRefresh.setMinimumSize(new java.awt.Dimension(100, 30));
    jbtnRefresh.setPreferredSize(new java.awt.Dimension(100, 30));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_footer.add(jbtnRefresh, gridBagConstraints);

    jbtnPrint.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jbtnPrint.setText("Print");
    jbtnPrint.setMinimumSize(new java.awt.Dimension(100, 30));
    jbtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_footer.add(jbtnPrint, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_toppanel.add(panel_footer, gridBagConstraints);

    panel_gradingTable.setBackground(new java.awt.Color(255, 255, 255));
    panel_gradingTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    panel_gradingTable.setMinimumSize(new java.awt.Dimension(570, 570));
    panel_gradingTable.setPreferredSize(new java.awt.Dimension(570, 500));
    panel_gradingTable.setLayout(new java.awt.GridBagLayout());

    panel_gradingtop.setBackground(new java.awt.Color(255, 255, 255));
    panel_gradingtop.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    panel_gradingtop.setMinimumSize(new java.awt.Dimension(780, 300));
    panel_gradingtop.setPreferredSize(new java.awt.Dimension(780, 400));
    panel_gradingtop.setLayout(new java.awt.GridBagLayout());

    jScrollPane2.setMinimumSize(new java.awt.Dimension(20, 400));
    jScrollPane2.setPreferredSize(new java.awt.Dimension(400, 400));

    jtblReportCard.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jtblReportCard.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Subject", "1st", "2nd", "3rd", "4th", "FG (Raw)", "FG (Transmuted)"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jtblReportCard.getTableHeader().setReorderingAllowed(false);
    jScrollPane2.setViewportView(jtblReportCard);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_gradingtop.add(jScrollPane2, gridBagConstraints);

    jPanel9.setBackground(new java.awt.Color(255, 255, 255));
    jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    jPanel9.setMinimumSize(new java.awt.Dimension(400, 40));
    jPanel9.setPreferredSize(new java.awt.Dimension(500, 40));
    jPanel9.setLayout(new java.awt.GridBagLayout());

    jlblReportCardGeneralAverage.setBackground(new java.awt.Color(255, 255, 255));
    jlblReportCardGeneralAverage.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jlblReportCardGeneralAverage.setForeground(new java.awt.Color(0, 0, 0));
    jlblReportCardGeneralAverage.setText("General Average");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridheight = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    jPanel9.add(jlblReportCardGeneralAverage, gridBagConstraints);

    jtfReportCardGeneralAverage.setColumns(4);
    jtfReportCardGeneralAverage.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
    jtfReportCardGeneralAverage.setDisabledTextColor(new java.awt.Color(0, 0, 0));
    jtfReportCardGeneralAverage.setEnabled(false);
    jtfReportCardGeneralAverage.setMinimumSize(new java.awt.Dimension(50, 23));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridheight = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    jPanel9.add(jtfReportCardGeneralAverage, gridBagConstraints);

    jPanel10.setBackground(new java.awt.Color(255, 255, 255));
    jPanel10.setForeground(new java.awt.Color(0, 0, 0));
    jPanel10.setMinimumSize(new java.awt.Dimension(380, 40));
    jPanel10.setPreferredSize(new java.awt.Dimension(380, 40));
    jPanel10.setLayout(new java.awt.GridBagLayout());

    jLabel6.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
    jLabel6.setForeground(new java.awt.Color(0, 0, 0));
    jLabel6.setText("Outstanding 90-100 (Passed), Very Satisfactory 85-89 (Passed) Didn't meet expectations Below 75 (Failed)");
    jLabel6.setPreferredSize(new java.awt.Dimension(377, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 0.5;
    jPanel10.add(jLabel6, gridBagConstraints);

    jLabel18.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
    jLabel18.setForeground(new java.awt.Color(0, 0, 0));
    jLabel18.setText("Satisfactory 80-84 (Passed), Fairly Satisfactory 75-79 (Passed), ");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.weighty = 0.5;
    jPanel10.add(jLabel18, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    jPanel9.add(jPanel10, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_gradingtop.add(jPanel9, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_gradingTable.add(panel_gradingtop, gridBagConstraints);

    panel_details1.setBackground(new java.awt.Color(255, 255, 255));
    panel_details1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    panel_details1.setMinimumSize(new java.awt.Dimension(795, 80));
    panel_details1.setPreferredSize(new java.awt.Dimension(795, 80));
    panel_details1.setLayout(new java.awt.GridBagLayout());

    lbl_name2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    lbl_name2.setText("Student No :");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_details1.add(lbl_name2, gridBagConstraints);

    jcmbStudentId.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jcmbStudentId.setEnabled(false);
    jcmbStudentId.setMinimumSize(new java.awt.Dimension(100, 25));
    jcmbStudentId.setPreferredSize(new java.awt.Dimension(150, 25));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_details1.add(jcmbStudentId, gridBagConstraints);

    lbl_name9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    lbl_name9.setText("Grade Level :");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_details1.add(lbl_name9, gridBagConstraints);

    jcmbGradeLevel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jcmbGradeLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0" }));
    jcmbGradeLevel.setSelectedIndex(-1);
    jcmbGradeLevel.setEnabled(false);
    jcmbGradeLevel.setMinimumSize(new java.awt.Dimension(100, 25));
    jcmbGradeLevel.setPreferredSize(new java.awt.Dimension(150, 25));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_details1.add(jcmbGradeLevel, gridBagConstraints);

    lbl_name10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    lbl_name10.setText("Student Name :");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_details1.add(lbl_name10, gridBagConstraints);

    lbl_name11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    lbl_name11.setText("Section :");
    lbl_name11.setToolTipText("");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_details1.add(lbl_name11, gridBagConstraints);

    jcmbSection.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jcmbSection.setEnabled(false);
    jcmbSection.setMinimumSize(new java.awt.Dimension(100, 25));
    jcmbSection.setPreferredSize(new java.awt.Dimension(150, 25));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_details1.add(jcmbSection, gridBagConstraints);

    jcmbStudentName.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jcmbStudentName.setEnabled(false);
    jcmbStudentName.setMinimumSize(new java.awt.Dimension(100, 25));
    jcmbStudentName.setPreferredSize(new java.awt.Dimension(150, 25));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_details1.add(jcmbStudentName, gridBagConstraints);

    lbl_name16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    lbl_name16.setText("Adviser :");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_details1.add(lbl_name16, gridBagConstraints);

    jcmbAdviser.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jcmbAdviser.setEnabled(false);
    jcmbAdviser.setMinimumSize(new java.awt.Dimension(100, 25));
    jcmbAdviser.setPreferredSize(new java.awt.Dimension(150, 25));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_details1.add(jcmbAdviser, gridBagConstraints);

    lbl_name17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    lbl_name17.setText("Session :");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_details1.add(lbl_name17, gridBagConstraints);

    jcmbSession.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jcmbSession.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM", "WD" }));
    jcmbSession.setSelectedIndex(-1);
    jcmbSession.setEnabled(false);
    jcmbSession.setMinimumSize(new java.awt.Dimension(100, 25));
    jcmbSession.setPreferredSize(new java.awt.Dimension(150, 25));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_details1.add(jcmbSession, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_gradingTable.add(panel_details1, gridBagConstraints);

    jpnlHeader1.setBackground(new java.awt.Color(255, 255, 255));
    jpnlHeader1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    jpnlHeader1.setForeground(new java.awt.Color(0, 0, 0));
    jpnlHeader1.setLayout(new java.awt.GridBagLayout());

    jpnlClassListLogo.setBackground(new java.awt.Color(255, 255, 255));
    jpnlClassListLogo.setForeground(new java.awt.Color(0, 0, 0));
    jpnlClassListLogo.setMinimumSize(new java.awt.Dimension(70, 70));
    jpnlClassListLogo.setPreferredSize(new java.awt.Dimension(70, 70));
    jpnlClassListLogo.setLayout(new java.awt.GridBagLayout());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    jpnlHeader1.add(jpnlClassListLogo, gridBagConstraints);

    jPanel14.setBackground(new java.awt.Color(255, 255, 255));
    jPanel14.setForeground(new java.awt.Color(0, 0, 0));
    jPanel14.setMinimumSize(new java.awt.Dimension(400, 18));
    jPanel14.setPreferredSize(new java.awt.Dimension(400, 18));
    jPanel14.setLayout(new java.awt.GridBagLayout());

    jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel34.setForeground(new java.awt.Color(0, 102, 204));
    jLabel34.setText("Mother of Perpetual Help School");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    jPanel14.add(jLabel34, gridBagConstraints);

    jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel35.setForeground(new java.awt.Color(51, 51, 51));
    jLabel35.setText("Iris Street Dahlia, West Fairview Quezon City");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    jPanel14.add(jLabel35, gridBagConstraints);

    jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel36.setForeground(new java.awt.Color(51, 51, 51));
    jLabel36.setText("1118 Metro Manila, Philippines");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
    jPanel14.add(jLabel36, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    jpnlHeader1.add(jPanel14, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_gradingTable.add(jpnlHeader1, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    panel_toppanel.add(panel_gradingTable, gridBagConstraints);

    jScrollPane1.setViewportView(panel_toppanel);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    getContentPane().add(jScrollPane1, gridBagConstraints);

    pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnPrint;
    private javax.swing.JButton jbtnRefresh;
    private javax.swing.JComboBox<String> jcmbAdviser;
    private javax.swing.JComboBox<String> jcmbGradeLevel;
    private javax.swing.JComboBox<String> jcmbSection;
    private javax.swing.JComboBox<String> jcmbSession;
    private javax.swing.JComboBox<String> jcmbStudentId;
    private javax.swing.JComboBox<String> jcmbStudentName;
    private javax.swing.JLabel jlblReportCardGeneralAverage;
    private javax.swing.JPanel jpnlClassListLogo;
    private javax.swing.JPanel jpnlHeader1;
    private javax.swing.JTable jtblReportCard;
    private javax.swing.JTextField jtfReportCardGeneralAverage;
    private javax.swing.JLabel lbl_name10;
    private javax.swing.JLabel lbl_name11;
    private javax.swing.JLabel lbl_name16;
    private javax.swing.JLabel lbl_name17;
    private javax.swing.JLabel lbl_name2;
    private javax.swing.JLabel lbl_name9;
    private javax.swing.JPanel panel_details1;
    private javax.swing.JPanel panel_footer;
    private javax.swing.JPanel panel_gradingTable;
    private javax.swing.JPanel panel_gradingtop;
    private javax.swing.JPanel panel_toppanel;
    // End of variables declaration//GEN-END:variables
}
