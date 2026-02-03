package hk.ust;

import org.apache.flink.table.api.FormatDescriptor;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.TableDescriptor;
import org.apache.flink.table.api.TableEnvironment;
import static org.apache.flink.table.api.DataTypes.*;
import java.nio.file.Path;

public class TpchTableDefinitions {
    private final Path dataDir;

    public TpchTableDefinitions(Path dataDir) {
        this.dataDir = dataDir;
    }

    public void createLineitemTable(TableEnvironment tableEnv) {
        Schema schema = Schema.newBuilder()
                .column("l_orderkey", BIGINT())
                .column("l_partkey", BIGINT())
                .column("l_suppkey", BIGINT())
                .column("l_linenumber", INT())
                .column("l_quantity", DECIMAL(15, 2))
                .column("l_extendedprice", DECIMAL(15, 2))
                .column("l_discount", DECIMAL(15, 2))
                .column("l_tax", DECIMAL(15, 2))
                .column("l_returnflag", STRING())
                .column("l_linestatus", STRING())
                .column("l_shipdate", DATE())
                .column("l_commitdate", DATE())
                .column("l_receiptdate", DATE())
                .column("l_shipinstruct", STRING())
                .column("l_shipmode", STRING())
                .column("l_comment", STRING())
                .build();

        TableDescriptor descriptor = TableDescriptor.forConnector("filesystem")
                .schema(schema)
                .option("path", dataDir.resolve("lineitem.tbl").toString())
                .format(FormatDescriptor.forFormat("csv")
                        .option("field-delimiter", "|")
                        .option("ignore-parse-errors", "true")
                        .build())
                .build();

        tableEnv.createTable("lineitem", descriptor);
    }
}
