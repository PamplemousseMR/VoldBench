import voldemort.client.StoreClientFactory;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.ClientConfig;
import voldemort.client.StoreClient;

class Main {
    public static void main(String[] args) {
        String bootstrapUrl = "tcp://localhost:6666";
        StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(bootstrapUrl));

        StoreClient<String, String> client = factory.getStoreClient("test");

        // create the value
        client.put("some_key", "someValue");
        System.out.println("done");
    }
}