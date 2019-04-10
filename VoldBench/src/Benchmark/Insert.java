package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;

public class Insert extends IBenchmark {

    public Insert(StoreClient<String, String> _client) {
        super("Insert", _client);
    }

    @Override
    protected void run() {
        for(long i=0L ; i< Option.getLoop() ; ++i) {
            m_CLIENT.put("Key_" + i, "Value_" + i);
        }
    }

    @Override
    protected void clean() {
        for(long i=0L ; i< Option.getLoop() ; ++i) {
            m_CLIENT.delete("Key_" + i);
        }
    }
}
