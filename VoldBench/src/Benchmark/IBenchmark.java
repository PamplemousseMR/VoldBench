package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;
import java.sql.Statement;

public abstract class IBenchmark {

    final StoreClient<String, String> m_CLIENT;
    final Statement m_SQL;
    private final String m_NAME;

    IBenchmark(String _name, StoreClient<String, String> _client, Statement _sql) {
        m_NAME = _name;
        m_CLIENT = _client;
        m_SQL = _sql;
    }

    public void bench() {
        System.out.print("...");
        setup();
        {
            System.out.print(" ...");
            long startTime = System.nanoTime();
            runVoldemort();
            long endTime = System.nanoTime();

            System.out.print(" ...");
            long duration = (endTime - startTime);
            System.out.println(" (Voldemort) Benchmark on " + Option.getLoop() + " loops : " + m_NAME + " => " + duration / 1000000000.0 + " s : " + Option.getLoop() / (duration / 1000000000.0) + " l/s");
        }
        {
            System.out.print("...");
            System.out.print(" ...");
            long startTime = System.nanoTime();
            runSQL();
            long endTime = System.nanoTime();

            System.out.print(" ...");
            long duration = (endTime - startTime);
            System.out.println(" (SQL) Benchmark on " + Option.getLoop() + " loops : " + m_NAME + " => " + duration / 1000000000.0 + " s : " + Option.getLoop() / (duration / 1000000000.0) + " l/s");
        }
        clean();
    }

    protected void setup() {
    }

    protected abstract void runVoldemort();

    protected abstract void runSQL();

    protected void clean(){

    }

}
