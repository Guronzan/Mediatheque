package sshtest;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ProxySOCKS5;
import com.jcraft.jsch.Session;

import fr.guronzan.mediatheque.utils.JSCHLogger;

@Slf4j
public class MySqlConnOverSSH {

	/**
	 * Java Program to connect to remote database through SSH using port
	 * forwarding
	 *
	 * @author Pankaj@JournalDev
	 * @throws JSchException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws URISyntaxException
	 */

	@Test
	public void TestJsch() throws JSchException, SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, URISyntaxException {
		final int lport = 2222;
		final String rhost = "localhost";
		final String host = "5.135.147.2";
		final int rport = 3306;
		final String user = "testRemoteConnection";
		// final String password = "QyfkRg%MB$*C3hLn1Jgf";
		final String dbuserName = "remoteAccess";
		final String dbpassword = "G5e@CFdj0k!hWM";
		final String url = "jdbc:mysql://localhost:" + lport + "/mediatheque";
		final String driverName = "com.mysql.jdbc.Driver";
		Session session = null;
		try {
			// Set StrictHostKeyChecking property to no to avoid UnknownHostKey
			// issue
			final java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			final JSch jsch = new JSch();
			JSch.setLogger(new JSCHLogger());
			jsch.addIdentity(
					getClass().getResource("/puttyKeys/privateKey.ppk").toURI()
							.getPath(), "rsa-key-MediathequeClient");
			session = jsch.getSession(user, host, 2222);
		//	session.setPassword("");
			session.setConfig(config);

			// Set proxy for Tor
			session.setProxy(new ProxySOCKS5("localhost", 9050));
			log.info("Connecting...");
			session.connect();
			log.info("Connected");
			final int assinged_port = session.setPortForwardingL(lport, rhost,
					rport);
			log.info("localhost:" + assinged_port + " -> " + rhost + ":"
					+ rport);
			log.info("Port Forwarded");

			// mysql database connectivity
			Class.forName(driverName).newInstance();
			try (Connection conn = DriverManager.getConnection(url, dbuserName,
					dbpassword)) {
				log.info("Database connection established");
				try (final Statement statement = conn.createStatement()) {
					final String request = "Select nick_name from user";
					try (final ResultSet rset = statement.executeQuery(request)) {
						while (rset.next()) {
							log.info(rset.getString("nick_name"));
						}
					}
				}
				log.info("DONE");
			}
		} finally {
			if (session != null && session.isConnected()) {
				log.info("Closing SSH Connection");
				session.disconnect();
			}
		}
	}

}