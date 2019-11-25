package io.raven.vertx.jmxmp;

import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.util.HashMap;

@Log4j2
public class JmxMpServer implements Verticle {

  private Vertx vertx;

  private JsonObject config;

  private int port;

  private JMXConnectorServer cs;

  @Override
  public Vertx getVertx() {
    return vertx;
  }

  @Override
  public void init(Vertx vertx, Context context) {
    try {
      this.vertx = vertx;
      this.config = context.config();
      this.port = config.getInteger("jmxmp-port");
      log.info("Starting JMXMP server on port: {}", port);
      MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
      HashMap<String, ?> env = new HashMap<>();
      JMXServiceURL url = new JMXServiceURL("jmxmp", null, port);
      cs = JMXConnectorServerFactory.newJMXConnectorServer(url, env, mbs);
    } catch (Exception e) {
      log.error("Error starting JMXMP server on port: {}", port, e);
    }
  }

  @Override
  @Deprecated
  public void start(Future<Void> future) throws Exception {
    cs.start();
    log.info("Started JMXMP server on port: {} successfully", port);
    future.complete();
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    cs.start();
    log.info("Started JMXMP server on port: {} successfully", port);
    startPromise.complete();
  }

  @Override
  @Deprecated
  public void stop(Future<Void> future) throws Exception {
    log.info("Stopping JMXMP server");
    cs.stop();
    log.info("Stopped JMXMP server successfully");
    future.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    log.info("Stopping JMXMP server");
    cs.stop();
    log.info("Stopped JMXMP server successfully");
    stopPromise.complete();
  }
}
