/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.editschedule;

import controller.schedule.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;

/**
 *
 * @author John Ferdinand Antonio
 */
public class Edit_AssignScheduleController implements ActionListener{

    private JTable jtblFacultyList;
    
    public Edit_AssignScheduleController(JTable jtblFacultyList){
        this.jtblFacultyList = jtblFacultyList;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        assignToSchedule();
    }
    
    private void assignToSchedule(){
        
    }
}
