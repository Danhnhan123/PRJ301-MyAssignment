/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attendent;
import java.sql.*;
import model.WokerSchedule;

/**
 *
 * @author Ad
 */
public class AttendentDBContext extends DBContext<Attendent> {

    public void insertAttends(ArrayList<Attendent> attends) {
        String sql = "INSERT INTO [dbo].[Attendent]\n"
                + "           ([wsid]\n"
                + "           ,[quantity]\n"
                + "           ,[anpha])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;

        try {
            connection.setAutoCommit(false);  // Bắt đầu giao dịch

            stm = connection.prepareStatement(sql);
            for (Attendent model : attends) {
                stm.setInt(1, model.getWs().getId());
                stm.setInt(2, model.getQuantity());
                stm.setFloat(3, model.getAlpha());
                stm.executeUpdate();  // Thực hiện lệnh insert
            }

            connection.commit();  // Commit sau khi insert hết các bản ghi
        } catch (SQLException ex) {
            Logger.getLogger(AttendentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (connection != null) {
                    connection.rollback();  // Rollback nếu có lỗi
                }
            } catch (SQLException ex1) {
                Logger.getLogger(AttendentDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (stm != null) {
                try {
                    stm.close();  // Đóng PreparedStatement sau khi hoàn thành
                } catch (SQLException ex) {
                    Logger.getLogger(AttendentDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                connection.setAutoCommit(true);  // Đặt lại chế độ auto-commit
                connection.close();  // Đóng kết nối
            } catch (SQLException ex) {
                Logger.getLogger(AttendentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void insert(Attendent model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Attendent model) {
        String sql = "UPDATE [dbo].[Attendent]\n"
                + "   SET [wsid] = ?\n"
                + "      ,[quantity] = ?\n"
                + "      ,[anpha] = ?\n"
                + " WHERE aid=?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getWs().getId());
            stm.setInt(2, model.getQuantity());
            stm.setFloat(3, model.getAlpha());
            stm.setInt(4, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WokerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(WokerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Attendent model) {
        String sql_update = "DELETE FROM [dbo].[Attendent]\n"
                + "      WHERE aid=?";

        PreparedStatement stm_update = null;
        try {
            stm_update = connection.prepareStatement(sql_update);
            stm_update.setInt(1, model.getId());
            stm_update.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AttendentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Attendent> list() {
        ArrayList<Attendent> attends = new ArrayList<>();
        String sql = "SELECT [aid]\n"
                + "      ,[wsid]\n"
                + "      ,[quantity]\n"
                + "      ,[anpha]\n"
                + "  FROM [dbo].[Attendent]";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Attendent at = new Attendent();
                at.setId(rs.getInt("aid"));
                WokerSchedule w = new WokerSchedule();
                w.setId(rs.getInt("wsid"));
                at.setWs(w);
                at.setQuantity(rs.getInt("quantity"));
                at.setAlpha(rs.getFloat("anpha"));

                attends.add(at);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendentDBContext.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AttendentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return attends;
    }

    @Override
    public Attendent get(int id) {
        String sql = "SELECT [aid]\n"
                + "      ,[wsid]\n"
                + "      ,[quantity]\n"
                + "      ,[anpha]\n"
                + "  FROM [dbo].[Attendent]\n"
                + "WHERE aid=?";
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();

            while (rs.next()) {
                Attendent at = new Attendent();
                at.setId(rs.getInt("aid"));
                WokerSchedule w = new WokerSchedule();
                w.setId(rs.getInt("wsid"));
                at.setWs(w);
                at.setQuantity(rs.getInt("quantity"));
                at.setAlpha(rs.getFloat("anpha"));

                return at;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendentDBContext.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AttendentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
