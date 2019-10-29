package com.rainyalley.practice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sllx on 15-1-15.
 */
public class WTTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String callback = req.getParameter("callback");
        String a = req.getParameter("a");
        String b = req.getParameter("b");
        resp.getWriter().print(callback + result());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p1 = req.getParameter("p1");
        String p2 = req.getParameter("p2");
        resp.getWriter().print("{a:1,b:2}");
    }

    private String result() {
        return "('hello jsonp');";
    }
}
