package org.darkphoenixs.pool.redis;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Properties;

import org.darkphoenixs.pool.PoolConfig;
import org.junit.Before;
import org.junit.Test;

public class RedisSentinelConnPoolTest {

	@Before
	public void before() throws Exception {

		Thread th = new Thread(new Runnable() {

			private ServerSocket serverSocket;

			@Override
			public void run() {

				try {
					serverSocket = new ServerSocket(RedisConfig.DEFAULT_PORT);

					serverSocket.accept();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		th.setDaemon(true);
		th.start();
	}

	@Test
	public void test() throws Exception {

		try {
			RedisSentinelConnPool pool = new RedisSentinelConnPool("localhost",
					new HashSet<String>());
			pool.close();
		} catch (Exception e) {
		}

		Properties properties = new Properties();

		try {
			RedisSentinelConnPool pool = new RedisSentinelConnPool(
					new PoolConfig(), properties);
			pool.close();
		} catch (Exception e) {
		}

		properties.setProperty(RedisConfig.MASTERNAME_PROPERTY, "mymaster");
		properties
				.setProperty(RedisConfig.SENTINELS_PROPERTY, "localhost:6379");

		properties.setProperty(RedisConfig.CONN_TIMEOUT_PROPERTY,
				RedisConfig.DEFAULT_TIMEOUT + "");
		properties.setProperty(RedisConfig.SO_TIMEOUT_PROPERTY,
				RedisConfig.DEFAULT_TIMEOUT + "");
		properties.setProperty(RedisConfig.DATABASE_PROPERTY,
				RedisConfig.DEFAULT_DATABASE + "");
		properties.setProperty(RedisConfig.CLIENTNAME_PROPERTY, "Test");
		properties.setProperty(RedisConfig.PASSWORD_PROPERTY, "Test");

		try {
			RedisSentinelConnPool pool = new RedisSentinelConnPool(
					new PoolConfig(), properties);
			
			pool.close();
		} catch (Exception e) {
		}
//		
//		try {
//			pool.getConnection();
//		} catch (Exception e) {
//		}
//
//		try {
//			pool.returnConnection(pool.getConnection());
//		} catch (Exception e) {
//		}
//		
//		try {
//			pool.returnConnection(null);
//		} catch (Exception e) {
//		}
//
//		try {
//			pool.invalidateConnection(null);
//		} catch (Exception e) {
//		}
//
//		pool.close();
	}
}
