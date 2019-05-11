package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.sql.Statement;

public class Find extends IBenchmark {

    public Find(StoreClient<String, String> _client, Statement _sql) {
        super("Find", _client, _sql);
    }

    @Override
    protected void setup() {
        for(long i = 0; i< Option.getDBSize() ; ++i) {
            m_CLIENT.put("Key_" + i, String.valueOf(1));
        }
        try{
            m_SQL.executeUpdate("create table testFind(" +
                    "m_key VARCHAR(20) NOT NULL," +
                    "m_value VARCHAR(50) NOT NULL," +
                    "PRIMARY KEY (m_key));");
            for (long i = 0L; i < Option.getDBSize(); ++i) {
                m_SQL.executeUpdate("INSERT INTO testFind VALUE ('Key_" + i + "','Value_" + i + "')");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void runVoldemort() {
        Random rnd = new Random();
        for(long i=0L ; i< Option.getLoop() ; ++i) {
            m_CLIENT.get("Key_" + (rnd.nextLong() & 0xffffffffL % Option.getDBSize()));
        }
    }

    @Override
    protected void runSQL() {
        Random rnd = new Random();
        try {
            for (long i = 0L; i < Option.getLoop(); ++i) {
                m_SQL.executeQuery("SELECT * FROM testFind WHERE m_key = 'Key_" + (rnd.nextLong() & 0xffffffffL % Option.getDBSize()) + "'");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void clean() {
        for(long i = 0; i< Option.getDBSize() ; ++i) {
            m_CLIENT.delete("Key_" + i);
        }
        try{
            m_SQL.executeUpdate("drop table testFind");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
