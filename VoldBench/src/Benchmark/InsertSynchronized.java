package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;
import voldemort.versioning.Versioned;
import java.sql.Statement;

public class InsertSynchronized extends IBenchmark {

    public InsertSynchronized(StoreClient<String, String> _client, Statement _sql) {
        super("Insert synchronized", _client, _sql);
    }

    @Override
    protected void setup() {
        for(long i=0 ; i< Option.getLoop() ; ++i) {
            Versioned<String> versioned = new Versioned<>(String.valueOf(1));
            m_CLIENT.put("Key_" + i, versioned);
        }
    }

    @Override
    protected void runVoldemort() {
        for(long i=0 ; i< Option.getLoop() ; ++i) {
            // Get a versioned version
            Versioned<String> versioned = m_CLIENT.get("Key_" + i);
            // Upgrade by one
            versioned.setObject(String.valueOf(Integer.valueOf(versioned.getValue()) + 1));
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
    }

    @Override
    protected void clean() {
        for(long i=0L ; i< Option.getLoop() ; ++i) {
            m_CLIENT.delete("Key_" + i);
        }
    }

}
