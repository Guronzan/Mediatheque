package fr.guronzan.mediatheque.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import lombok.extern.slf4j.Slf4j;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.ProxySOCKS5;
import com.jcraft.jsch.Session;

@Slf4j
public class DataSource {

	private Connection connection;
	private Session session;

	public DataSource() {
		openConnection();
	}

	private void openConnection() {
		if (this.session != null && this.session.isConnected()) {
			log.info("Closing SSH Connection");
			this.session.disconnect();
		}
		final int lport = 3306;
		final String rhost = "localhost";
		final String host = "5.135.147.2";
		final int rport = 3306;
		final String user = "testRemoteConnection";
		// final String password = "QyfkRg%MB$*C3hLn1Jgf";
		final String dbuserName = "remoteAccess";
		final String dbpassword = "G5e@CFdj0k!hWM";
		final String url = "jdbc:mysql://localhost:" + lport + "/mediatheque";
		final String driverName = "com.mysql.jdbc.Driver";
		try {
			// Set StrictHostKeyChecking property to no to avoid
			// UnknownHostKey
			// issue
			final java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			final JSch jsch = new JSch();
			JSch.setLogger(new JSCHLogger());
			jsch.addIdentity(
					getClass().getResource("/puttyKeys/privateKey.ppk").toURI()
					.getPath(), "rsa-key-MediathequeClient");
			this.session = jsch.getSession(user, host, 2222);
			this.session.setPassword("");
			this.session.setConfig(config);

			// Set proxy for Tor
			this.session.setProxy(new ProxySOCKS5("localhost", 9050));
			log.info("Connecting...");
			this.session.connect();
			log.info("Connected");
			final int assinged_port = this.session.setPortForwardingL(lport,
					rhost, rport);
			log.info("localhost:" + assinged_port + " -> " + rhost + ":"
					+ rport);
			log.info("Port Forwarded");

			// // mysql database connectivity
			Class.forName(driverName).newInstance();
			this.connection = DriverManager.getConnection(url, dbuserName,
					dbpassword);
			log.info("Database connection established");
			try (final Statement statement = this.connection.createStatement()) {
				final String request = "Select nick_name from user";
				try (final ResultSet rset = statement.executeQuery(request)) {
					while (rset.next()) {
						log.info(rset.getString("nick_name"));
					}
				}
			}
			log.info("DONE");
		} catch (final Exception e) {
			log.error("Error while opening remote connection", e);
			this.connection = null;
			// } finally {
			// if (session != null && session.isConnected()) {
			// log.info("Closing SSH Connection");
			// session.disconnect();
			// }
		}
	}

}
