/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Plan;
import java.sql.*;
import model.Department;
import model.PlanCampaign;
import model.Product;

/**
 *
 * @author Ad
 */
public class PlanDBContext extends DBContext<Plan> {

    @Override
    public void insert(Plan model) {
        try {
            connection.setAutoCommit(false);
            String sql_insert_plan = "INSERT INTO [Plan]\n"
                    + "           ([startTime]\n"
                    + "           ,[endTime]\n"
                    + "           ,[did])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";
            String sql_select_plan = "SELECT @@IDENTITY as plid";
            String sql_insert_campaign = "INSERT INTO [PlanCampaign]\n"
                    + "           ([plid]\n"
                    + "           ,[pid]\n"
                    + "           ,[quantity]\n"
                    + "           ,[unitEffort])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm_insert_plan = connection.prepareStatement(sql_insert_plan);
            stm_insert_plan.setDate(1, model.getStartTime());
            stm_insert_plan.setDate(2, model.getEndTime());
            stm_insert_plan.setInt(3, model.getD().getId());
            stm_insert_plan.executeUpdate();

            PreparedStatement stm_select_plan = connection.prepareStatement(sql_select_plan);
            ResultSet rs = stm_select_plan.executeQuery();

            while (rs.next()) {
                model.setId(rs.getInt("plid"));
            }
            for (PlanCampaign campaign : model.getPc()) {
                PreparedStatement stm_insert_campaign = connection.prepareStatement(sql_insert_campaign);
                stm_insert_campaign.setInt(1, model.getId());
                stm_insert_campaign.setInt(2, campaign.getP().getId());
                stm_insert_campaign.setInt(3, campaign.getQuantity());
                stm_insert_campaign.setFloat(4, campaign.getEffort());
                stm_insert_campaign.executeUpdate();
            }

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Plan model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Plan model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Plan> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Plan get(int id) {
        PreparedStatement stm = null;
        String sql = "SELECT p.[plid]\n"
                + "      ,[startTime]\n"
                + "      ,[endTime]\n"
                + "      ,[did],c.quantity,c.unitEffort,pr.pname,camid,pr.pid\n"
                + "  FROM [Assignment].[dbo].[Plan] p \n"
                + "  Inner Join [dbo].PlanCampaign c On p.plid = c.plid\n"
                + "  inner join [dbo].Product pr on c.pid = pr.pid"
                + " WHERE p.[plid] = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Plan p = new Plan();
                p.setId(rs.getInt("plid"));
                p.setStartTime(rs.getDate("startTime"));
                p.setEndTime(rs.getDate("endTime"));

                Department d = new Department();
                d.setId(rs.getInt("did"));
                p.setD(d);
                p.setPc(new ArrayList<>());

                for (PlanCampaign campaign : p.getPc()) {
                    Product pr = new Product();
                    pr.setId(rs.getInt("pr.pid"));
                    pr.setName(rs.getString("pr.pname"));
                    
                    
                    campaign.setId(rs.getInt("camid"));
                    campaign.setPl(p);
                    campaign.setP(pr);
                    campaign.setQuantity(rs.getInt("c.quantity"));
                    campaign.setEffort(rs.getInt("c.unitEffort"));
                    p.getPc().add(campaign);
                }
                return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
