package net.mhgoi.blog.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 少年
 */
public abstract class JsonResponseHandler {
    /**
     * 输出Json
     *
     * @param response
     * @param data
     * @throws IOException
     * @throws ServletException
     */
    protected void writeJSON(HttpServletResponse response,
                             Object data) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        //输出JSON
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(data));
        out.flush();
        out.close();
    }
}
