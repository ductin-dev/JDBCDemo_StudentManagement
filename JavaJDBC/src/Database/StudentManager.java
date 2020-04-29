package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class StudentManager {

    public StudentManager() {
    }

    private Connection connection;

    public Connection getConn() {
	return connection;
    }

    public void setConn(Connection conn) {
	this.connection = conn;
    }

    public Connection getConnect() throws ClassNotFoundException, SQLException {
	Class.forName(DatabaseInfo.driverName);
	connection = DriverManager.getConnection(DatabaseInfo.dbURL, DatabaseInfo.dbUser, DatabaseInfo.dbPass);
	System.out.println("CONNECTTED!");
	return connection;
    }

    public boolean checkStudent(String Sno) throws SQLException, ClassNotFoundException {
	// Kết nối database
	connection = getConnect();

	// Kiểm tra sinh viên có trong database hay chưa
	String sql = "Select * from Students where Sno = '" + Sno + "'";
	Statement stm1 = connection.createStatement();
	ResultSet rs = stm1.executeQuery(sql);

	// Trả về kết quả
	if (!rs.next()) {
	    connection.close();
	    return false;
	}
	connection.close();
	return true;
    }

    public Vector<Vector<String>> getAll() throws ClassNotFoundException, SQLException {
	Vector<Vector<String>> data = new Vector<>();

	// Kết nối database
	connection = getConnect();

	// Tạo câu lệnh SQL (Cách 1: dùng Statement)
	Statement stmt = connection.createStatement();
	ResultSet rs = stmt.executeQuery("Select * from Students");
	while (rs.next()) {

	    // Lấy dữ liệu từ ResultSet
	    String Sno = rs.getString(1);
	    String Sname = rs.getString(2);
	    String Sgender = rs.getString(3);
	    String Sclass = rs.getString(4);
	    String Saddress = rs.getString(5);
	    String Semail = rs.getString(6);

	    // Ghi vào vector
	    Vector<String> temp = new Vector<>();
	    temp.add(Sno);
	    temp.add(Sname);
	    temp.add(Sgender);
	    temp.add(Sclass);
	    temp.add(Saddress);
	    temp.add(Semail);

	    // Thêm dữ liệu vào data vector chính
	    data.add(temp);
	}
	return data;
    }

    public int update(String Sno, String Sname, String Sgender, String Sclass, String Sadress, String Semail)
                    throws ClassNotFoundException, SQLException {
	int updateStatus = 0;
	// Kết nối database
	Connection conn = getConnect();

	// Tạo câu lệnh SQL
	String sql = "UPDATE Students set Sname='" + Sname + "',Sgender'" + Sgender + "',Sclass='" + Sclass + "',Semail ='"
	                + Semail + "',Sadress='" + Sadress + "' WHERE Sno='" + Sno + "'";
	Statement stm1 = conn.createStatement();
	updateStatus = stm1.executeUpdate(sql);
	conn.close();
	return updateStatus;
    }

    public void addNew(String Sno, String Sname, String Sgender, String Sclass, String Sadress, String Semail)
                    throws ClassNotFoundException, SQLException {
	// Kết nối database
	connection = getConnect();

	// Tạo câu lệnh SQL (Cách 2: sử dụng PreparedStatement)
	String sql = "INSERT INTO Students(Sno,Sname,Sgender,Sclass,Sadress,Semail) VALUES(?,?,?,?,?,?)";
	PreparedStatement stmt = connection.prepareStatement(sql);
	stmt.setString(1, Sno);
	stmt.setString(2, Sname);
	stmt.setString(3, Sgender);
	stmt.setString(4, Sclass);
	stmt.setString(5, Sadress);
	stmt.setString(6, Semail);

	// Thực hiện lệnh SQL
	stmt.executeUpdate();

	// Đóng kết nối
	connection.close();
    }

    public int delete(String Sno) throws SQLException, ClassNotFoundException {
	int deleteStatus = 0;

	// Kết nối database
	connection = getConnect();

	// Xóa sinh viên
	String sql = "DELETE FROM STUDENTS WHERE Sno='" + Sno + "'";
	Statement stm1 = connection.createStatement();
	deleteStatus = stm1.executeUpdate(sql);

	// Trả về kết quả int (có xóa thành công hay không)
	connection.close();
	return deleteStatus;
    }
}
