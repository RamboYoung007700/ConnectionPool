package date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestConnectionPool {
	private class worker extends Thread{
		private ConnectionPool cp;
		private Stu stu;
		public worker(ConnectionPool cp) {
			this.cp=cp;
			stu=new Stu("王五","34243243","JAVA工程师","2018.3.20","家里蹲",
					2343,"http://xxxxx.com","必胜必胜！","李四师兄","知乎看到");
		}
		public void run() {
			Connection c=cp.getConnection();
			String sql = "insert into stu values(null,?,?,?,?,?,?,?,?,?,?)";
			try ( PreparedStatement ps = c.prepareStatement(sql)) {
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cp.returnConnection(c);
	
		}
	}
	public static void main(String[] args) {
		ConnectionPool cp=new ConnectionPool(10);
		TestConnectionPool tcp=new TestConnectionPool();
		System.out.println("连接池方式测试开始");
		long start=System.currentTimeMillis();
		List<Thread> ts=new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Thread t=tcp.new worker(cp);
			t.start();
			ts.add(t);
		}
		for(Thread t:ts) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long end=System.currentTimeMillis();
		long cost=end-start;
		System.out.println("连接池方式开100个线程插入100条数据耗时为"+cost+"毫秒");
		
		System.out.println("传统方式测试开始");
		Stu stu=new Stu("张三11","34243243","JAVA工程师","2018.3.20","家里蹲",
				2343,"http://xxxxx.com","必胜必胜！","李四师兄","知乎看到");
		start=System.currentTimeMillis();
		List<Thread> ts2=new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Thread t=new Thread() {
				public void run() {
					StuDAO sd=new StuDAO();
					sd.add(stu);
				}
			};
			t.start();
			ts2.add(t);
		}
		for(Thread t:ts2) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		end=System.currentTimeMillis();
		cost=end-start;
		System.out.println("传统方式开100个线程插入100条数据耗时为"+cost+"毫秒");
	}
}
