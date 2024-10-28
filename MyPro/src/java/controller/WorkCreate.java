/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import controller.accesscontrol.BaseRBACController;
import dal.DepartmentDBContext;
import dal.EmployeeDBContext;
import dal.ProductDBContext;
import dal.ScheduleDBContext;
import dal.WokerScheduleDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Employee;
import model.Product;
import model.Schedule;
import model.WokerSchedule;
import model.accesscontrol.User;

/**
 *
 * @author Ad
 */
public class WorkCreate extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        ScheduleDBContext ScheduleDB = new ScheduleDBContext();
        EmployeeDBContext emp = new EmployeeDBContext();
        ProductDBContext db = new ProductDBContext();
        DepartmentDBContext dbd = new DepartmentDBContext();

        Schedule sche = ScheduleDB.get(Integer.parseInt(request.getParameter("scheId")));
        ArrayList<Employee> employees = emp.list();
        ArrayList<Product> products = db.list();

        request.setAttribute("depts", dbd.get("workshop"));
        request.setAttribute("employees", employees);
        request.setAttribute("schedule", sche);
        request.setAttribute("products", products);

        request.getRequestDispatcher("../view/work/create.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        int scheId = Integer.parseInt(request.getParameter("scheId"));

        WokerScheduleDBContext db = new WokerScheduleDBContext();
        ArrayList<WokerSchedule> ws = new ArrayList<>();
        EmployeeDBContext emp = new EmployeeDBContext();
        ArrayList<Employee> emps = emp.list();

        String[] workerIds = request.getParameterValues("workerId");
        String[] quantities = request.getParameterValues("quantity");

        for (int i = 0; i < workerIds.length; i++) {
            if (quantities[i] == null || quantities[i].trim().isEmpty()) {
                // Bỏ qua nếu quantity rỗng
                continue;
            }
            try {
                int quantity = Integer.parseInt(quantities[i]);

                // Khởi tạo đối tượng WorkerSchedule mới cho từng worker
                WokerSchedule workerSchedule = new WokerSchedule();

                // Lấy employee id từ form
                int employeeId = Integer.parseInt(workerIds[i]);

                // Tạo đối tượng Schedule và Employee tương ứng
                Schedule schedule = new Schedule();
                schedule.setId(scheId);
                Employee employee = new Employee();
                employee.setId(employeeId);

                // Đặt các giá trị cho workerSchedule
                workerSchedule.setSc(schedule);
                workerSchedule.setE(employee);
                workerSchedule.setQuantity(quantity);

                // Thêm vào danh sách workerSchedules
                ws.add(workerSchedule);

            } catch (NumberFormatException e) {
                // Nếu quantity không phải là số hợp lệ thì bỏ qua
                continue;
            }
        }

        try {
            db.insertWorker(ws);  // Chèn tất cả các schedule cùng lúc
            response.sendRedirect("../work/list");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to insert Worker Schedule");
        }
    }

}
