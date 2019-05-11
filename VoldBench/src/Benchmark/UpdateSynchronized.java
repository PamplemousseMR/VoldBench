package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;
import voldemort.versioning.Versioned;
import java.sql.Statement;
import java.util.Random;

public class UpdateSynchronized extends IBenchmark {

    public UpdateSynchronized(StoreClient<String, String> _client, Statement _sql) {
        super("Insert synchronized", _client, _sql);
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
    }

    @Override
    protected void clean() {
        for(long i=0L ; i< Option.getDBSize() ; ++i) {
            m_CLIENT.delete("Key_" + i);
        }
    }

}
