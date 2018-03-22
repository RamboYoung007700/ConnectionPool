package date;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StuDAO implements DAO {

	// 构造器中完成驱动
	public StuDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 得到连接的方法:
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://139.199.105.214:3306" + "/student?characterEncoding=UTF-8", "test",
				"123abc");
	}

	@Override
	public void add(Stu stu) {
		// TODO Auto-generated method stub
		String sql = "insert into stu values(null,?,?,?,?,?,?,?,?,?,?)";
		try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, stu.name);
			ps.setString(2, stu.qq);
			ps.setString(3, stu.type);
			ps.setString(4, stu.enrolltime);
			ps.setString(5, stu.school);
			ps.setInt(6, stu.onlinenumber);
			ps.setString(7, stu.reportURL);
			ps.setString(8, stu.swearword);
			ps.setString(9, stu.shixiong);
			ps.setString(10, stu.fromwhere);
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				stu.id = id;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Stu stu) {
		// TODO Auto-generated method stub
		String sql = "update stu set name=?, qq=?, type=?, enrolltime=?,"
				+ "school=?, onlinenumber=?, reportURL=?, swearword=?, shixiong=?," + "fromwhere=? where id=?";
		try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, stu.name);
			ps.setString(2, stu.qq);
			ps.setString(3, stu.type);
			ps.setString(4, stu.enrolltime);
			ps.setString(5, stu.school);
			ps.setInt(6, stu.onlinenumber);
			ps.setString(7, stu.reportURL);
			ps.setString(8, stu.swearword);
			ps.setString(9, stu.shixiong);
			ps.setString(10, stu.fromwhere);
			ps.setInt(11, stu.id);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		String sql = "delete from stu where id=?";
		try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Stu get(int id) {
		// TODO Auto-generated method stub
		Stu stu = null;
		try (Connection c = getConnection(); Statement s = c.createStatement()) {
			String sql = "select * from stu where id=" + id;
			ResultSet rs = s.executeQuery(sql);
			if (rs.next()) {
				stu = new Stu();
				String name = rs.getString(2);
				String qq = rs.getString(3);
				String type = rs.getString(4);
				String enrolltime = rs.getString(5);
				String school = rs.getString(6);
				int onlinenumber = rs.getInt(7);
				String reportURL = rs.getString(8);
				String swearword = rs.getString(9);
				String shixiong = rs.getString(10);
				String fromwhere = rs.getString(11);
				stu.name = name;
				stu.qq = qq;
				stu.type = type;
				stu.enrolltime = enrolltime;
				stu.school = school;
				stu.onlinenumber = onlinenumber;
				stu.reportURL = reportURL;
				stu.swearword = swearword;
				stu.shixiong = shixiong;
				stu.fromwhere = fromwhere;
				stu.id = id;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stu;
	}

	@Override
	public List<Stu> GetFromName(String name) {
		// TODO Auto-generated method stub
		List<Stu> stus = new ArrayList<>();
		String sql = "select * from stu where name=?";
		try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Stu stu = new Stu();
				int id = rs.getInt(1);

				String qq = rs.getString(3);
				String type = rs.getString(4);
				String enrolltime = rs.getString(5);
				String school = rs.getString(6);
				int onlinenumber = rs.getInt(7);
				String reportURL = rs.getString(8);
				String swearword = rs.getString(9);
				String shixiong = rs.getString(10);
				String fromwhere = rs.getString(11);
				stu.id = id;
				stu.name = name;
				stu.qq = qq;
				stu.type = type;
				stu.enrolltime = enrolltime;
				stu.school = school;
				stu.onlinenumber = onlinenumber;
				stu.reportURL = reportURL;
				stu.swearword = swearword;
				stu.shixiong = shixiong;
				stu.fromwhere = fromwhere;
				stus.add(stu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stus;
	}

	@Override
	public List<Stu> list() {
		// TODO Auto-generated method stub
		return list(0, Short.MAX_VALUE);
	}

	@Override
	public List<Stu> list(int start, int count) {
		// TODO Auto-generated method stub
		List<Stu> stus = new ArrayList<>();
		String sql = "select * from stu limit ?,?";
		try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Stu stu = new Stu();
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String qq = rs.getString(3);
				String type = rs.getString(4);
				String enrolltime = rs.getString(5);
				String school = rs.getString(6);
				int onlinenumber = rs.getInt(7);
				String reportURL = rs.getString(8);
				String swearword = rs.getString(9);
				String shixiong = rs.getString(10);
				String fromwhere = rs.getString(11);
				stu.id = id;
				stu.name = name;
				stu.qq = qq;
				stu.type = type;
				stu.enrolltime = enrolltime;
				stu.school = school;
				stu.onlinenumber = onlinenumber;
				stu.reportURL = reportURL;
				stu.swearword = swearword;
				stu.shixiong = shixiong;
				stu.fromwhere = fromwhere;
				stus.add(stu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stus;
	}

}
