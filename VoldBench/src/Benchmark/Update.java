package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;
import voldemort.versioning.Versioned;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.sql.Statement;

public class Update extends IBenchmark {

    public Update(StoreClient<String, String> _client, Statement _sql) {
        super("Update", _client, _sql);
    }

    @Override
    protected void setup() {
        for(long i = 0; i< Option.getDBSize() ; ++i) {
            m_CLIENT.put("Key_" + i, String.valueOf(1));
        }
        try{
            m_SQL.executeUpdate("create table testUpdate(" +
                    "m_key VARCHAR(20) NOT NULL," +
                    "m_value VARCHAR(50) NOT NULL," +
                    "PRIMARY KEY (m_key));");
            for (long i = 0L; i < Option.getDBSize(); ++i) {
                m_SQL.executeUpdate("INSERT INTO testUpdate VALUE ('Key_" + i + "','" + 1 + "')");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void runVoldemort() {
        Random rnd = new Random();
        for(long i=0L ; i< Option.getLoop() ; ++i) {
            String key = "Key_" + (rnd.nextLong() & 0xffffffffL % Option.getDBSize());
            Versioned<String> versioned = m_CLIENT.get(key);
            m_CLIENT.put(key, String.valueOf(Integer.valueOf(versioned.getValue()) + 1));
        }
    }

    @Override
    protected void runSQL() {
        Random rnd = new Random();
        try {
            for (long i = 0L; i < Option.getLoop(); ++i) {
                String key = "Key_" + (rnd.nextLong() & 0xffffffffL % Option.getDBSize());
                ResultSet row = m_SQL.executeQuery("SELECT * FROM testUpdate WHERE m_key = '" + key + "'");
                if(row.next()){
                    m_SQL.executeUpdate("UPDATE testUpdate SET m_value = '"+ (Integer.valueOf(row.getString(2)) + 1) + "' WHERE m_key = '" + key + "'");
                }
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
            m_SQL.executeUpdate("drop table testUpdate");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
