package date;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
	List<Connection> connections=new ArrayList<>();
	int size;
	public ConnectionPool(int size) {
		this.size=size;
		System.out.println("连接池大小为"+size);
		//调用初始化方法
		ini();
	}
	//初始化方法，创建size个连接
	private void ini() {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			for (int i = 0; i < size; i++) {
				System.out.println("开始建立连接"+i);
				Connection c=DriverManager.getConnection("jdbc:mysql://139.199.105.214:3306"
			+ "/student?characterEncoding=UTF-8", "test",
						"123abc");
				connections.add(c);
				System.out.println("连接"+i+"建立完毕");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public synchronized Connection getConnection() {
		while(connections.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Connection c=connections.remove(0);
		return c;
	}
	
	public synchronized void returnConnection(Connection c) {
		connections.add(c);
		this.notifyAll();
	}

}
