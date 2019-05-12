package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;
import voldemort.versioning.Versioned;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertSynchronized extends IBenchmark {

    public InsertSynchronized(StoreClient<String, String> _client, Statement _sql) {
        super("Insert synchronized", _client, _sql);
    }

    @Override
    protected void setup() {
        try{
            m_SQL.executeUpdate("create table testInsertSync(" +
                    "m_key VARCHAR(20) NOT NULL," +
                    "m_value VARCHAR(50) NOT NULL," +
                    "PRIMARY KEY (m_key));");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void runVoldemort() {
        for(long i=0 ; i< Option.getLoop() ; ++i) {
            // Get a versioned version
            Versioned<String> versioned = new Versioned<>(String.valueOf(1));
            if (m_CLIENT.putIfNotObsolete("Key_" + i, versioned)) {
                // We continue the job if it's a success
                continue;
            }
            // Else we retry it
            --i;
        }
    }

    @Override
    protected void runSQL() {
        try {
            for (long i = 0L; i < Option.getLoop(); ++i) {
                m_SQL.executeUpdate("INSERT INTO testInsertSync VALUE ('Key_" + i + "','Value_" + i + "')");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void clean() {
        for(long i=0L ; i< Option.getLoop() ; ++i) {
            m_CLIENT.delete("Key_" + i);
        }
        try{
            m_SQL.executeUpdate("drop table testInsertSync");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
