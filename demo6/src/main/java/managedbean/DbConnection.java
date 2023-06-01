package managedbean;

import java.sql.*;
public class DbConnection {
    private String dbURL = "jdbc:mysql://localhost:3306/register";
    private String username = "root";
    private String password = "";
    public Connection connection;
    public DbConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL,username,password);
            if(connection!=null){
                System.out.println("Success");
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return connection;
    }
    public void insert(String fname, String surname, String mNumber, String password, String dateOfBirth, String gender){
        try {
            String sqlQuery = "INSERT INTO user(fname, surname,mNumber,password,dateOfBirth,gender) VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, fname);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, mNumber);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, dateOfBirth);
            preparedStatement.setString(6, gender);
            int noOfRowsInserted = preparedStatement.executeUpdate();
            if(noOfRowsInserted>0){
                System.out.println(noOfRowsInserted + " rows inserted!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
