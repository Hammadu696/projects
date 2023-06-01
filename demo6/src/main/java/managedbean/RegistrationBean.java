package managedbean;

import javax.faces.bean.ManagedBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "registration")
public class RegistrationBean {
    private String fName;
    private String surname;
    private String mNumber;
    private String password;
    private String dateOfBirth;
    private String gender;
    private int id;

    private DbConnection db;

    public RegistrationBean() {
        db = new DbConnection();
    }

    public String getFirstName() {
        return fName;
    }

    public void setFirstName(String firstName) {
        this.fName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMobileNumber() {
        return mNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void register() {
        db.insert(fName, surname, mNumber, password, dateOfBirth, gender);
    }

    public List<RegistrationBean> getPersonList() throws SQLException {
        List<RegistrationBean> list = new ArrayList<>();
        PreparedStatement pstmt = db.connection.prepareStatement("SELECT * FROM user");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            RegistrationBean person = new RegistrationBean();
            person.setId(rs.getInt("p_id"));
            person.setFirstName(rs.getString("firstName"));
            person.setSurname(rs.getString("surname"));
            person.setMobileNumber(rs.getString("mobileNumber"));
            person.setPassword(rs.getString("password"));
            person.setDateOfBirth(rs.getString("dateOfBirth"));
            person.setGender(rs.getString("gender"));
            list.add(person);
        }
        return list;
    }

    public void delete(int id) {
        try {
            Connection connection = db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM persondetails WHERE p_id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void update() {
        try {
            Connection connection = db.getConnection();
            String sql = "UPDATE user SET fName = ?, surname = ?, mNumber = ?, password = ?, dateOfBirth = ?, gender = ? WHERE p_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fName);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, mNumber);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, dateOfBirth);
            preparedStatement.setString(6, gender);
            preparedStatement.setInt(7, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(rowsUpdated + " row(s) updated successfully!");
            } else {
                System.out.println("No rows were updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String createNewForm() {
        return "/register.xhtml";
    }
}