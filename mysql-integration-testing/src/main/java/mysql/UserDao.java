package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    private String fname;
    private String lname;
    private String username;
    private static final String insertSQL = "insert into users values (?,?,?)";

    public UserDao(String fname, String lname, String username) {
        this.fname = fname;
        this.lname = lname;
        this.username = removeTheDots(username);
    }

    public Connection connection;
    public String dbUrl;
    public String dbUser;
    public String dbPwd;

    public boolean createUser(UserDao user) throws SQLException {

        connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);

        PreparedStatement ps = connection.prepareStatement(insertSQL);
        ps.setString(1, user.getFname());
        ps.setString(2, user.getLname());
        ps.setString(3, user.getUsername());

        boolean rs = ps.execute();

        return rs;
    }

    private String removeTheDots(String username){
        return username.replace(".","");
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
