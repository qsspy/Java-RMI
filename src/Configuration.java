public class Configuration {

    //defines default matrix dimension
    public static final int DEFAULT_MATRIX_DIMENSION = 2;
    //defines name of server in RMI registry
    public static final String SERVER_SERVICE_IMPL_NAME = "server";
    //defines port of RMI registry on local machine - for agents
    public static final int RMI_REGISTRY_PORT = 1112;
    //defines port of remote object in RMI Registry
    public static final int REMOTE_OBJECT_PORT = 0;
    //defines simulated agent sleep min time
    public static final long AGENT_MIN_SLEEP_TIME = 1000;
    //defines simulated agent sleep max time
    public static final long AGENT_MAX_SLEEP_TIME = 2000;
    //defines max agent id (agent can have id from 1 to MAX_AGENT_ID inclusive)
    public static final int MAX_AGENT_ID = 100;
    //defines agents in use
    public static final int AGENTS_IN_USE = 4;
}
