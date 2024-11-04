/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mytag;

/**
 *
 * @author Ad
 */
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;

public class ToVietnameseDate extends SimpleTagSupport {

    private Date value;

    public void setValue(Date value) {
        this.value = value;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        if (value != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            out.print(sdf.format(value));
        }
    }
}
