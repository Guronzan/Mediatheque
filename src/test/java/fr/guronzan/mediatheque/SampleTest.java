package fr.guronzan.mediatheque;

import java.io.InputStream;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

public class SampleTest extends DBTestCase {
	private IDatabaseTester databaseTester;
	private IDataSet dataSet;

	public SampleTest(final String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		this.databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver",
				"jdbc:hsqldb:sample", "sa", "");

		// initialize your dataset here
		try (final InputStream is = SampleTest.class
				.getResourceAsStream("/dataset.xml")) {
			this.dataSet = new FlatXmlDataSetBuilder().build(is);

			this.databaseTester.setDataSet(this.dataSet);
			// will call default setUpOperation
			this.databaseTester.onSetup();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		// will call default tearDownOperation
		this.databaseTester.onTearDown();
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return this.dataSet;
	}
}