package managedbean;

import javax.faces.bean.ManagedBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ManagedBean(name = "loginBean")
public class LoginBean {
    private String username;
    private String password;
    private String errorMessage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String login() {
        try {
            DbConnection db = new DbConnection();
            Connection connection = db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM user WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count == 1) {
                return "Login Successful";
            } else {
                setErrorMessage("Invalid username or password!");
                return "Login Failed";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
