package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;
import voldemort.versioning.Versioned;

import java.util.Random;
import java.sql.Statement;

public class Update extends IBenchmark {

    public Update(StoreClient<String, String> _client, Statement _sql) {
        super("Find", _client, _sql);
    }

    @Override
    protected void setup() {
        for(long i = 0; i< Option.getDBSize() ; ++i) {
            m_CLIENT.put("Key_" + i, String.valueOf(1));
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
    }

    @Override
    protected void clean() {
        for(long i = 0; i< Option.getDBSize() ; ++i) {
            m_CLIENT.delete("Key_" + i);
        }
    }
}
