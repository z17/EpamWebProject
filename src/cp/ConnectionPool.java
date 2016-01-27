package cp;

import org.apache.log4j.Logger;
import settings.ProjectSetting;

import java.sql.*;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *  Пулл соединений
 */
public final class ConnectionPool {
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);

    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;
    public static ConnectionPool INSTANCE;

    public static ConnectionPool getInstance() {
        if (INSTANCE == null) {
            synchronized (ConnectionPool.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConnectionPool();
                }
            }
        }
        return INSTANCE;
    }

    private ConnectionPool() {
        ProjectSetting dbResourceManager = ProjectSetting.getInstance();
        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.user = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        try {
            this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));
        } catch (NumberFormatException e) {
            LOG.error("invalid poolsize format", e);
            this.poolSize = 5;
        }
        initPoolData();
    }

    private void initPoolData() {
        Locale.setDefault(Locale.ENGLISH);

        try {
            Class.forName(driverName);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);

                PooledConnection polledConnection = new PooledConnection(connection, connectionQueue, givenAwayConQueue);
                connectionQueue.add(polledConnection);
            }

        } catch (ClassNotFoundException e) {
            LOG.fatal("error driver not found", e);
        } catch (SQLException e) {
            LOG.fatal("error get connection", e);
        }
    }

    public void dispoce() {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionsQueue(givenAwayConQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            LOG.error("error close connections", e);
        }
    }

    public Connection takeConnection() {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            LOG.error("error take connection", e);
        }
        return connection;
    }

    public void closeConnections(Connection con, Statement st, ResultSet rs) {
        try {
            con.close();
        } catch (SQLException e) {
            LOG.warn("Connection isn't in pool");
        }

        try {
            rs.close();
        } catch (SQLException e) {
            LOG.warn("Result set isnt closed");
        }

        try {
            st.close();
        } catch (SQLException e) {
            LOG.warn("Statement isnt close");
        }
    }


    public void closeConnection(Connection con, Statement st) {
        try {
            con.close();
        } catch (SQLException e) {
            LOG.warn("Connection isn't in pool");
        }

        try {
            st.close();
        } catch (SQLException e) {
            LOG.warn("Statement isnt close");
        }
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }

}
