package me.enoi.weather.commons;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *  Clase para el manejo de la conexion a redis
 */
public class RedisClient {
    private JedisPool pool;

    private Jedis jedis;

    private final Util util = new Util();

    /**
     * Recupera una nueva conexion para el pool de redis
     * @return
     */
    public Jedis getConnection() {
        JedisPool pool = new JedisPool(util.env("REDIS_SERVER", "localhost"), 6379);
        jedis = pool.getResource();
        return jedis;
    }

    /**
     * Destruye la conexion realizada a redis.
     */
    public void destroyPool() {
        if (jedis != null) {
            jedis.close();
        }

        if (pool != null) {
            pool.destroy();
        }
    }
}
