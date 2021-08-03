/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.threads;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

/**
 *
 * @author mdjukanovic
 */
public class ClockThread extends Thread{
    
    private JLabel lblClock;

    public ClockThread(JLabel lblClock) {
        this.lblClock = lblClock;
    }

    @Override
    public void run() {
        while(true) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            lblClock.setText(simpleDateFormat.format(new Date()));
        }
    }
    
}
