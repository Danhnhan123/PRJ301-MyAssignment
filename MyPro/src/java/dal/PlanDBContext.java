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
        try {
            connection.setAutoCommit(false);

            // Cập nhật bảng Plan
            String sql_update_plan = "UPDATE [Plan] SET [startTime] = ?, [endTime] = ?, [did] = ? WHERE [plid] = ?";
            PreparedStatement stm_update_plan = connection.prepareStatement(sql_update_plan);
            stm_update_plan.setDate(1, model.getStartTime());
            stm_update_plan.setDate(2, model.getEndTime());
            stm_update_plan.setInt(3, model.getD().getId());
            stm_update_plan.setInt(4, model.getId());
            stm_update_plan.executeUpdate();

            // Thêm các PlanCampaign mới
            String sql_insert_campaign = "UPDATE [PlanCampaign] SET [plid] = ?, [pid] = ?, [quantity] = ?, [unitEffort] = ? WHERE[camid] = ?";
            PreparedStatement stm_insert_campaign = connection.prepareStatement(sql_insert_campaign);
            for (PlanCampaign campaign : model.getPc()) {
                stm_insert_campaign.setInt(1, model.getId());
                stm_insert_campaign.setInt(2, campaign.getP().getId());
                stm_insert_campaign.setInt(3, campaign.getQuantity());
                stm_insert_campaign.setFloat(4, campaign.getEffort());
                stm_insert_campaign.setInt(5, campaign.getId());
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
    public void delete(Plan model) {
        String sql_update = "DELETE FROM [dbo].[Plan]\n"
                + "      WHERE plid=?";

        PreparedStatement stm_update = null;
        try {
            stm_update = connection.prepareStatement(sql_update);
            stm_update.setInt(1, model.getId());
            stm_update.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Plan> list() {
        ArrayList<Plan> plans = new ArrayList<>();
        PreparedStatement stm = null;
        String sql = "SELECT p.[plid], p.[startTime], p.[endTime], p.[did], pl.[pid], pl.[quantity], pl.[unitEffort], pl.[camid], pr.[pname] "
                + "FROM [Plan] p "
                + "LEFT JOIN [PlanCampaign] pl ON p.plid = pl.plid "
                + "LEFT JOIN [Product] pr ON pl.pid = pr.pid";

        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            Plan currentPlan = null;
            int currentPlanId = -1;

            while (rs.next()) {
                int planId = rs.getInt("plid");

                // Nếu planId thay đổi, tạo đối tượng Plan mới
                if (planId != currentPlanId) {
                    currentPlan = new Plan();
                    currentPlan.setId(planId);
                    currentPlan.setStartTime(rs.getDate("startTime"));
                    currentPlan.setEndTime(rs.getDate("endTime"));

                    Department dept = new Department();
                    dept.setId(rs.getInt("did"));
                    currentPlan.setD(dept);

                    currentPlan.setPc(new ArrayList<>());  // Khởi tạo danh sách PlanCampaign

                    plans.add(currentPlan);  // Thêm Plan vào danh sách
                    currentPlanId = planId;
                }

                // Xử lý dữ liệu của PlanCampaign nếu có
                if (rs.getInt("camid") != 0) { // Kiểm tra có PlanCampaign hay không
                    PlanCampaign campaign = new PlanCampaign();

                    Product product = new Product();
                    product.setId(rs.getInt("pid"));
                    product.setName(rs.getString("pname"));

                    campaign.setId(rs.getInt("camid"));
                    campaign.setPl(currentPlan);
                    campaign.setP(product);
                    campaign.setQuantity(rs.getInt("quantity"));
                    campaign.setEffort(rs.getFloat("unitEffort"));

                    currentPlan.getPc().add(campaign);  // Thêm PlanCampaign vào Plan
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return plans;
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

            Plan p = null;  // Khởi tạo Plan là null trước
            while (rs.next()) {
                if (p == null) {
                    // Khởi tạo Plan khi có kết quả đầu tiên từ ResultSet
                    p = new Plan();
                    p.setId(rs.getInt("plid"));
                    p.setStartTime(rs.getDate("startTime"));
                    p.setEndTime(rs.getDate("endTime"));

                    Department d = new Department();
                    d.setId(rs.getInt("did"));
                    p.setD(d);

                    // Khởi tạo danh sách PlanCampaign rỗng
                    p.setPc(new ArrayList<>());
                }

                // Tạo mới PlanCampaign cho từng dòng kết quả
                PlanCampaign campaign = new PlanCampaign();

                Product pr = new Product();
                pr.setId(rs.getInt("pid"));
                pr.setName(rs.getString("pname"));

                campaign.setId(rs.getInt("camid"));
                campaign.setPl(p);
                campaign.setP(pr);
                campaign.setQuantity(rs.getInt("quantity"));
                campaign.setEffort(rs.getFloat("unitEffort"));

                // Thêm PlanCampaign vào danh sách
                p.getPc().add(campaign);
            }

            return p;
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
