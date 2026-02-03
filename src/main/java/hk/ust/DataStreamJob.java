/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hk.ust;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

/**
 * Skeleton for a Flink DataStream Job.
 *
 * <p>For a tutorial how to write a Flink application, check the
 * tutorials and examples on the <a href="https://flink.apache.org">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class DataStreamJob {

	public static void main(String[] args) throws Exception {

		EnvironmentSettings settings = EnvironmentSettings.newInstance()
				.inBatchMode()
				.build();

		TableEnvironment tableEnv= TableEnvironment.create(settings);

		String tpchDataDir = System.getenv("TPCH_DATA_DIR");
		if (tpchDataDir == null || tpchDataDir.trim().isEmpty()) {
			throw new IllegalStateException("TPCH_DATA_DIR is not set");
		}

		Path tpchDataPath = Paths.get(tpchDataDir);
		TpchTableDefinitions tpchTables = new TpchTableDefinitions(tpchDataPath);
		tpchTables.createLineitemTable(tableEnv);

		// Just to verify that the table is created successfully
		System.out.printf("Tables in the catalog: %s\n", String.join(", ", tableEnv.listTables()));
	}
}
