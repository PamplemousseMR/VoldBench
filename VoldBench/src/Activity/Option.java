package Activity;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Option {

    private static String s_VDM_URL = "tcp://localhost";
    private static int s_VDM_PORT = 6666;
    private static String s_VDM_NAME = "test";
    private static String s_SQL_URL = "jdbc:mysql://localhost:3306/bdd_sdzee?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
    private static String s_SQL_CLASSNAME = "com.mysql.cj.jdbc.Driver";
    private static String s_SQL_LOGIN = "root";
    private static String s_SQL_PWD = "rootpassword";
    private static long s_LOOP = 1000;
    private static long s_DB_SIZE = 10000;

    static String getVDMUrl() {
        return s_VDM_URL;
    }

    static int getVDMPort() {
        return s_VDM_PORT;
    }

    static String getVDMName() {
        return s_VDM_NAME;
    }

    static String getSQLUrl() {
        return s_SQL_URL;
    }

    static String getClassName() { return  s_SQL_CLASSNAME; }

    static String getSQLLogin() {
        return s_SQL_LOGIN;
    }

    static String getSQLPwd() {
        return s_SQL_PWD;
    }

    public static long getLoop() {
        return s_LOOP;
    }

    public static long getDBSize() {
        return s_DB_SIZE;
    }

    static void parseArgs(String[] _args) {
        try {
            OptionParser parser = new OptionParser();
            parser.accepts("h");
            parser.accepts("vdm-u").withRequiredArg().ofType(String.class);
            parser.accepts("vdm-p").withRequiredArg().ofType(Integer.class);
            parser.accepts("vdm-n").withRequiredArg().ofType(String.class);
            parser.accepts("sql-u").withRequiredArg().ofType(String.class);
            parser.accepts("sql-c").withRequiredArg().ofType(String.class);
            parser.accepts("sql-l").withRequiredArg().ofType(String.class);
            parser.accepts("sql-p").withRequiredArg().ofType(String.class);
            parser.accepts("l").withRequiredArg().ofType(Long.class);
            parser.accepts("s").withRequiredArg().ofType(Long.class);

            OptionSet options = parser.parse(_args);

            if (options.has("h")) {
                displayHelp();
                System.exit(0);
            }
            if (options.has("vdm-u")) {
                s_VDM_URL = (String) options.valueOf("vdm-u");
            }
            if (options.has("vdm-p")) {
                s_VDM_PORT = (Integer) options.valueOf("vdm-p");
            }
            if (options.has("vdm-n")) {
                s_VDM_NAME = (String) options.valueOf("vdm-n");
            }
            if (options.has("sql-u")) {
                s_SQL_URL = (String) options.valueOf("sql-u");
            }
            if (options.has("sql-c")) {
                s_SQL_CLASSNAME = (String) options.valueOf("sql-c");
            }
            if (options.has("sql-l")) {
                s_SQL_LOGIN = (String) options.valueOf("sql-l");
            }
            if (options.has("sql-p")) {
                s_SQL_PWD = (String) options.valueOf("sql-p");
            }
            if (options.has("l")) {
                s_LOOP = (Long)options.valueOf("l");
            }
            if (options.has("s")) {
                s_DB_SIZE = (Long)options.valueOf("s");
            }
        } catch (Throwable _t) {
            displayHelp();
            System.exit(1);
        }
    }

    static void displayOptions() {
        System.out.println("voldemort url : " + s_VDM_URL);
        System.out.println("voldemort port : " + s_VDM_PORT);
        System.out.println("voldemort name : " + s_VDM_NAME);
        System.out.println("sql url : " + s_SQL_URL);
        System.out.println("sql classname : " + s_SQL_CLASSNAME);
        System.out.println("sql login : " + s_SQL_LOGIN);
        System.out.println("sql password : " + s_SQL_PWD);
    }

    private static void displayHelp() {
        System.out.println("-h: display help");
        System.out.println("-vdm-u <url> : set the voldemort url");
        System.out.println("-vdm-p <port> : set the voldemort port");
        System.out.println("-vdm-n <name> : set the db voldemort name");
        System.out.println("-sql-u <url> : set the sql url");
        System.out.println("-sql-c <classname> : set the sql classname");
        System.out.println("-sql-l <login> : set the sql login");
        System.out.println("-sql-p <pwd> : set the db sql password");
        System.out.println("-l <loop> : set the number of loop");
        System.out.println("-s <size> : set the database size");
    }

}
