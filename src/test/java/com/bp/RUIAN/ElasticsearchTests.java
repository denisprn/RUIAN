package com.bp.RUIAN;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.junit.jupiter.api.*;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ElasticsearchTests {
    private static final ElasticsearchContainer container =
            new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:7.13.1").withExposedPorts(9200);

    private static final NodeSelector INGEST_NODE_SELECTOR = nodes -> {
        final Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();

            if (node.getRoles() != null && !node.getRoles().isIngest()) {
                iterator.remove();
            }
        }
    };

    private static final String INDEX_NAME = "ruian";
    private static RestHighLevelClient client;

    @BeforeAll
    public static void startElasticsearchCreateLocalClient() {
        container.start();

        HttpHost host = new HttpHost("localhost", container.getMappedPort(9200));
        final RestClientBuilder builder = RestClient.builder(host);
        builder.setNodeSelector(INGEST_NODE_SELECTOR);
        client = new RestHighLevelClient(builder);
    }

    @AfterAll
    public static void closeResources() throws Exception {
        client.close();
    }

    @BeforeEach
    public void deleteRuianIndex() throws Exception {
        try {
            client.indices().delete(new DeleteIndexRequest(INDEX_NAME), RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Test
    public void testClusterVersion() throws Exception {
        final ClusterHealthResponse response = client.cluster().health(new ClusterHealthRequest(), RequestOptions.DEFAULT);
        // check for yellow or green cluster health
        Assertions.assertNotSame(response.getStatus(), ClusterHealthStatus.RED);


        // async
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<ClusterHealthResponse> reference = new AtomicReference<>();
        final ActionListener<ClusterHealthResponse> listener = ActionListener.wrap(
                r -> { reference.set(r); latch.countDown(); },
                e -> { e.printStackTrace(); latch.countDown(); });
        client.cluster().healthAsync(new ClusterHealthRequest(), RequestOptions.DEFAULT, listener);
        latch.await(10, TimeUnit.SECONDS);
        Assertions.assertNotSame(reference.get().getStatus(), ClusterHealthStatus.RED);
    }
}
