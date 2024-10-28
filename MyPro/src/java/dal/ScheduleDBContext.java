/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Plan;
import model.PlanCampaign;
import model.Product;
import model.Schedule;

/**
 *
 * @author Ad
 */
public class ScheduleDBContext extends DBContext<Schedule> {

    // Thêm phương thức để thực hiện nhiều lần insert với giao dịch
    public void insertSchedules(ArrayList<Schedule> schedules) {
        String sql = "INSERT INTO [dbo].[ScheduleCampaign]\n"
                + "           ([camid]\n"
                + "           ,[date]\n"
                + "           ,[K]\n"
                + "           ,[quantity])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;

        try {
            connection.setAutoCommit(false);  // Bắt đầu giao dịch

            stm = connection.prepareStatement(sql);
            for (Schedule model : schedules) {
                stm.setInt(1, model.getCam().getId());
                stm.setDate(2, model.getDate());
                stm.setString(3, model.getK());
                stm.setInt(4, model.getQuantity());
                stm.executeUpdate();  // Thực hiện lệnh insert
            }

            connection.commit();  // Commit sau khi insert hết các bản ghi
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (connection != null) {
                    connection.rollback();  // Rollback nếu có lỗi
                }
            } catch (SQLException ex1) {
                Logger.getLogger(ScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (stm != null) {
                try {
                    stm.close();  // Đóng PreparedStatement sau khi hoàn thành
                } catch (SQLException ex) {
                    Logger.getLogger(ScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                connection.setAutoCommit(true);  // Đặt lại chế độ auto-commit
                connection.close();  // Đóng kết nối
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Other methods (update, delete, list, get)...
    @Override
    public void update(Schedule model) {
        String sql_update = "UPDATE [dbo].[ScheduleCampaign]\n"
                + "   SET [camid] = ?\n"
                + "      ,[date] = ?\n"
                + "      ,[K] = ?\n"
                + "      ,[quantity] = ?\n"
                + " WHERE scid=?";

        PreparedStatement stm_update = null;
        try {
            stm_update = connection.prepareStatement(sql_update);
            stm_update.setInt(1, model.getCam().getId());
            stm_update.setDate(2, model.getDate());
            stm_update.setString(3, model.getK());
            stm_update.setInt(4, model.getQuantity());
            stm_update.setInt(5, model.getId());
            stm_update.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Schedule model) {
        String sql_update = "DELETE FROM [dbo].[ScheduleCampaign]\n"
                + "      WHERE scid=?";

        PreparedStatement stm_update = null;
        try {
            stm_update = connection.prepareStatement(sql_update);
            stm_update.setInt(1, model.getId());
            stm_update.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Schedule> list() {
        ArrayList<Schedule> sches = new ArrayList<>();
        String sql = "SELECT [scid], [camid], [date], [K], [quantity] "
                + "FROM [dbo].[ScheduleCampaign]";
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Schedule sche = new Schedule();
                sche.setId(rs.getInt("scid"));

                PlanCampaign pc = new PlanCampaign();
                pc.setId(rs.getInt("camid"));
                sche.setCam(pc);

                sche.setDate(rs.getDate("date"));
                sche.setK(rs.getString("K"));
                sche.setQuantity(rs.getInt("quantity"));

                sches.add(sche);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return sches;
    }

    @Override
    public Schedule get(int id) {
        String sql = "SELECT [scid]\n"
                + "      ,sc.[camid]\n"
                + "      ,[date]\n"
                + "      ,[K]\n"
                + "      ,sc.[quantity],pl.pid\n"
                + "  FROM [dbo].[ScheduleCampaign] sc\n"
                + "  inner Join PlanCampaign pl On sc.camid = pl.camid\n"
                + "  Where scid=?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            Schedule sche = new Schedule();
            while (rs.next()) {
                sche.setId(rs.getInt("scid"));
                PlanCampaign pl = new PlanCampaign();
                pl.setId(rs.getInt("camid"));
                Product p = new Product();
                p.setId(rs.getInt("pid"));
                pl.setP(p);
                sche.setCam(pl);
                sche.setDate(rs.getDate("date"));
                sche.setK(rs.getString("K"));
                sche.setQuantity(rs.getInt("quantity"));
            }
            return sche;
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public void insert(Schedule model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
