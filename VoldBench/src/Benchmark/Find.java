package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;
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
    }

    @Override
    protected void runVoldemort() {
        Random rnd = new Random();
        for(long i=0L ; i< Option.getLoop() ; ++i) {
            m_CLIENT.get("Key_" + rnd.nextLong() % Option.getDBSize());
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
