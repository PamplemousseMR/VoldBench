package Activity;

import Benchmark.*;
import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

class Main {

    public static void main(String[] _args) {
        try {
            //Set up options
            Option.parseArgs(_args);
            Option.displayOptions();

            //Set up Voldemort connexion
            String serverUrl = Activity.Option.getVDMUrl() + ":" + Activity.Option.getVDMPort();
            StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(serverUrl));
            StoreClient<String, String> client = factory.getStoreClient(Activity.Option.getVDMName());

            //Set up MySql connexion
            Class.forName(Option.getClassName());
            Connection connection = DriverManager.getConnection(Option.getSQLUrl(), Option.getSQLLogin(), Option.getSQLPwd());
            Statement statement = connection.createStatement();

            Insert ins = new Insert(client, statement);
            ins.bench();

            InsertSynchronized insSync = new InsertSynchronized(client, statement);
            insSync.bench();

            Find find = new Find(client, statement);
            find.bench();

            Update update = new Update(client, statement);
            update.bench();

            UpdateSynchronized updateSynchronized = new UpdateSynchronized(client, statement);
            updateSynchronized.bench();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}