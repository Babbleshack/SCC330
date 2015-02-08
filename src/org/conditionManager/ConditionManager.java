package org.conditionManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import org.conditionManager.conditionContainers.ActuatorCondition;
import org.conditionManager.consumers.ConditionProcessor;
import org.conditionManager.database.QueryManager;
import org.conditionManager.producers.ConditionProducer;

/**
 *
 * @author Dominic Lindsay
 */
public class ConditionManager {
    private static final int THREAD_COUNT = 1;
    /**
     * create and starts consumer and producer threads, sharing a blocking queue
     * and query manager
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ExecutorService producer = Executors.newFixedThreadPool(THREAD_COUNT);
        ExecutorService consumer = Executors.newFixedThreadPool(THREAD_COUNT);
        BlockingQueue<ActuatorCondition> conditionQueue =
                new SynchronousQueue();
        QueryManager qm = new QueryManager();
        producer.submit(new ConditionProducer(conditionQueue, qm));
        consumer.submit(new ConditionProcessor(conditionQueue, qm));
    }
    
}
