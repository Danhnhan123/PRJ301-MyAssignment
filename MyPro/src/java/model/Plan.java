/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Ad
 */
public class Plan {
    private int id;
    private Date startTime;
    private Date endTime;
    private Department d;
    
    private ArrayList<PlanCampaign> pc = new ArrayList<>();

    public ArrayList<PlanCampaign> getPc() {
        return pc;
    }

    public void setPc(ArrayList<PlanCampaign> pc) {
        this.pc = pc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Department getD() {
        return d;
    }

    public void setD(Department d) {
        this.d = d;
    }
    
    
}
