package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;

import java.sql.SQLException;
import java.sql.Statement;

public class Insert extends IBenchmark {

    public Insert(StoreClient<String, String> _client, Statement _sql) {
        super("Insert", _client, _sql);
    }

    @Override
    protected void setup() {
        try{
            m_SQL.executeUpdate("create table testInsert(" +
                                 "m_key VARCHAR(20) NOT NULL," +
                                 "m_value VARCHAR(50) NOT NULL," +
                                 "PRIMARY KEY (m_key));");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void runVoldemort() {
        for(long i=0L ; i< Option.getLoop() ; ++i) {
            m_CLIENT.put("Key_" + i, "Value_" + i);
        }
    }

    @Override
    protected void runSQL() {
        try {
            for (long i = 0L; i < Option.getLoop(); ++i) {
                m_SQL.executeUpdate("INSERT INTO testInsert VALUE ('Key_" + i + "','Value_" + i + "')");
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
            m_SQL.executeUpdate("drop table testInsert");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
