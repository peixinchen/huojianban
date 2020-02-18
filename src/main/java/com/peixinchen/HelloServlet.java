package com.peixinchen;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("127.0.0.1");
        dataSource.setPort(3306);
        dataSource.setUser("root");
        dataSource.setPassword("");
        dataSource.setDatabaseName("huojianban");
        dataSource.setUseSSL(false);
        dataSource.setCharacterEncoding("utf8");

        try (Connection c = dataSource.getConnection()) {
            String sql = "SELECT message FROM messages";
            try (PreparedStatement statement = c.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        resp.getWriter().println(rs.getString("message"));
                    }
                }
            }
        } catch (SQLException e) {
            resp.getWriter().println(e.getMessage());
        }
    }
}
