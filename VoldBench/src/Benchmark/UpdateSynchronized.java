package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;
import voldemort.versioning.Versioned;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class UpdateSynchronized extends IBenchmark {

    public UpdateSynchronized(StoreClient<String, String> _client, Statement _sql) {
        super("Update synchronized", _client, _sql);
    }

    @Override
    protected void setup() {
        for(long i = 0; i< Option.getDBSize() ; ++i) {
            m_CLIENT.put("Key_" + i, String.valueOf(1));
        }
        try{
            m_SQL.executeUpdate("create table testUpdateSync(" +
                    "m_key VARCHAR(20) NOT NULL," +
                    "m_value VARCHAR(50) NOT NULL," +
                    "PRIMARY KEY (m_key));");
            for (long i = 0L; i < Option.getDBSize(); ++i) {
                m_SQL.executeUpdate("INSERT INTO testUpdateSync VALUE ('Key_" + i + "','" + 1 + "')");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void runVoldemort() {
        Random rnd = new Random();
        for(long i=0L ; i< Option.getLoop() ; ++i) {
            // Get a versioned version
            String key = "Key_" + (rnd.nextLong() & 0xffffffffL % Option.getDBSize());
            Versioned<String> versioned = m_CLIENT.get(key);
            // Upgrade by one
            versioned.setObject(String.valueOf(Integer.valueOf(versioned.getValue()) + 1));
            if (m_CLIENT.putIfNotObsolete(key, versioned)) {
                // We continue the job if it's a success
                continue;
            }
            // Else we retry it
            --i;
        }
    }

    @Override
    protected void runSQL() {
        Random rnd = new Random();
        try {
            for (long i = 0L; i < Option.getLoop(); ++i) {
                lock();
                String key = "Key_" + (rnd.nextLong() & 0xffffffffL % Option.getDBSize());
                ResultSet row = m_SQL.executeQuery("SELECT * FROM testUpdateSync WHERE m_key = '" + key + "'");
                if(row.next()){
                    m_SQL.executeUpdate("UPDATE testUpdateSync SET m_value = '"+ (Integer.valueOf(row.getString(2)) + 1) + "' WHERE m_key = '" + key + "'");
                }
                unlock();
            }

        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }

    @Override
    protected void clean() {
        for(long i=0L ; i< Option.getDBSize() ; ++i) {
            m_CLIENT.delete("Key_" + i);
        }
        try{
            m_SQL.executeUpdate("drop table testUpdateSync");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public synchronized void lock() throws SQLException {
        while(!lock(10));
    }

    public synchronized boolean lock(int timeout) throws SQLException {
        if(timeout<0)
            throw new IllegalArgumentException("Timeout must be >= 0");
        ResultSet result = m_SQL.executeQuery("SELECT GET_LOCK('lockUpdSync' , "+ timeout + ") AS locked");
        return result.next() && result.getInt(1)==1;
    }

    public synchronized void unlock() throws SQLException {
        m_SQL.executeQuery("DO RELEASE_LOCK('lockUpdSync')");
    }


}
