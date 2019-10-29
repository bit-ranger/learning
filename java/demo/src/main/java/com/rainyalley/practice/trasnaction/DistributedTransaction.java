package com.rainyalley.practice.trasnaction;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DistributedTransaction {

    public static void main(String[] args) throws SQLException {
        XADataSource dataSourceFoo = getDataSource("jdbc:mysql://127.0.0.1:3306/mx2", "root", "root");
        XADataSource dataSourceBar = getDataSource("jdbc:mysql://127.0.0.1:3307/mx2", "root", "root");
        Xid xidFoo = new MysqlXid(new byte[]{0x00}, new byte[]{0x00}, 0);
        Xid xidBar = new MysqlXid(new byte[]{0x00}, new byte[]{0x01}, 0);

        XAConnection xacFoo = getXAConnetion(dataSourceFoo);
        XAResource xarFoo = xacFoo.getXAResource();
        Connection conFoo = getConnection(xacFoo);
        Statement stmtFoo = conFoo.createStatement();


        XAConnection xacBar = getXAConnetion(dataSourceBar);
        XAResource xarBar = xacBar.getXAResource();
        Connection conBar = getConnection(xacBar);
        Statement stmtBar = conBar.createStatement();


        // XAConnection xacBar = getXAConnetion(dataSourceBar);

    }


    private static XADataSource getDataSource(String url, String user, String password) {
        MysqlXADataSource dataSource = new MysqlXADataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }


    public static XAConnection getXAConnetion(XADataSource dataSource) {
        XAConnection XAConn = null;
        try {
            XAConn = dataSource.getXAConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return XAConn;
    }

    public static Connection getConnection(XAConnection XAConn) {
        Connection conn = null;
        try {
            conn = XAConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("连接关闭失败");
        }
    }
}
