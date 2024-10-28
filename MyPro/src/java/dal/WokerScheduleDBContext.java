/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.WokerSchedule;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.Schedule;

/**
 *
 * @author Ad
 */
public class WokerScheduleDBContext extends DBContext<WokerSchedule> {

    public void insertWorker(ArrayList<WokerSchedule> WokerSchedule) {
        String sql = "INSERT INTO [dbo].[WorkerSchedule]\n"
                + "           ([scid]\n"
                + "           ,[eid]\n"
                + "           ,[quantity])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);

            stm = connection.prepareStatement(sql);
            for (WokerSchedule model : WokerSchedule) {
                stm.setInt(1, model.getSc().getId());
                stm.setInt(2, model.getE().getId());
                stm.setInt(3, model.getQuantity());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(WokerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(WokerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(WokerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(WokerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void insert(WokerSchedule model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(WokerSchedule model) {
        String sql = "UPDATE [dbo].[WorkerSchedule]\n"
                + "   SET [scid] = ?\n"
                + "      ,[eid] = ?\n"
                + "      ,[quantity] = ?\n"
                + " WHERE wsid=?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getSc().getId());
            stm.setInt(2, model.getE().getId());
            stm.setInt(3, model.getQuantity());
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
    public void delete(WokerSchedule model) {
        String sql_update = "DELETE FROM [dbo].[WorkerSchedule]\n"
                + "      WHERE wsid=?";

        PreparedStatement stm_update = null;
        try {
            stm_update = connection.prepareStatement(sql_update);
            stm_update.setInt(1, model.getId());
            stm_update.executeUpdate();
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
    public ArrayList<WokerSchedule> list() {
        ArrayList<WokerSchedule> wss = new ArrayList<>();
        String sql = "SELECT [wsid]\n"
                + "      ,[scid]\n"
                + "      ,[eid]\n"
                + "      ,[quantity]\n"
                + "  FROM [dbo].[WorkerSchedule]";
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                WokerSchedule ws = new WokerSchedule();
                ws.setId(rs.getInt("wsid"));
                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                ws.setE(e);
                Schedule sche = new Schedule();
                sche.setId(rs.getInt("scid"));
                ws.setSc(sche);
                ws.setQuantity(rs.getInt("quantity"));
                wss.add(ws);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WokerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(WokerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return wss;
    }

    @Override
    public WokerSchedule get(int id) {
        String sql = "SELECT [wsid]\n"
                + "      ,[scid]\n"
                + "      ,[eid]\n"
                + "      ,[quantity]\n"
                + "  FROM [dbo].[WorkerSchedule]\n"
                + "  WHERE wsid=?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                WokerSchedule ws = new WokerSchedule();
                ws.setId(rs.getInt("wsid"));
                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                ws.setE(e);
                Schedule sche = new Schedule();
                sche.setId(rs.getInt("scid"));
                ws.setSc(sche);
                ws.setQuantity(rs.getInt("quantity"));
                return ws;
            }
        } catch (SQLException ex) {
            Logger.getLogger(WokerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(WokerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
