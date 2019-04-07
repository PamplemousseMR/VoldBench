import joptsimple.OptionParser;
import joptsimple.OptionSet;

class Option {

    private static String s_URL = "tcp://localhost";
    private static int s_PORT = 6666;
    private static String s_DB_NAME = "test";

    static String getUrl() {
        return s_URL;
    }

    static int getPort() {
        return s_PORT;
    }

    static String getDBName() {
        return s_DB_NAME;
    }

    static void parseArgs(String[] _args) {
        try {
            OptionParser parser = new OptionParser();
            parser.accepts("h");
            parser.accepts("u").withRequiredArg().ofType(String.class);
            parser.accepts("p").withRequiredArg().ofType(Integer.class);
            parser.accepts("n").withRequiredArg().ofType(String.class);

            OptionSet options = parser.parse(_args);

            if (options.has("h")) {
                displayHelp();
                System.exit(0);
            }
            if (options.has("u")) {
                s_URL = (String) options.valueOf("u");
            }
            if (options.has("p")) {
                s_PORT = (Integer) options.valueOf("p");
            }
            if (options.has("n")) {
                s_DB_NAME = (String) options.valueOf("n");
            }
        } catch (Throwable _t) {
            displayHelp();
            System.exit(1);
        }
    }

    static void displayOptions() {
        System.out.println("url : " + s_URL);
        System.out.println("port : " + s_PORT);
        System.out.println("db name : " + s_DB_NAME);
    }

    private static void displayHelp() {
        System.out.println("-h: display help");
        System.out.println("-u url: set the url");
        System.out.println("-p port: set the port");
        System.out.println("-n : set the db name");
    }

}
