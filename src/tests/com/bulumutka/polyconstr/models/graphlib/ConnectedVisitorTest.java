package com.bulumutka.polyconstr.models.graphlib;

import com.bulumutka.polyconstr.models.graphlib.graphlib.Algorithms;
import com.bulumutka.polyconstr.models.graphlib.graphlib.GraphBuilder;
import com.bulumutka.polyconstr.models.graphlib.graphlib.MetricGraph;
import com.sun.javafx.scene.traversal.Algorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectedVisitorTest {
    @Test
    public void connectedTest1() {
        GraphBuilder builder = new GraphBuilder(4);
        builder.addEdge(0, 1, 0);
        builder.addEdge(1, 2, 0);
        builder.addEdge(1, 3, 0);
        MetricGraph g = builder.build();

        Assertions.assertTrue(Algorithms.isConnected(g, 0));

        builder.addVertex();
        g = builder.build();
        assertFalse(Algorithms.isConnected(g, 0));
    }

    @Test
    public void connectedTest2() {
        var builder = new GraphBuilder(6);
        builder.addEdge(1, 2, 1);
        builder.addEdge(5, 4, 3);
        builder.addEdge(3, 4, 1);
        builder.addEdge(0, 4, 1);
        builder.addEdge(2, 3, 1);
        builder.setRoot(1);
        MetricGraph g = builder.build();

        assertEquals(6, g.getVertexNumber());
        assertTrue(Algorithms.isConnected(g, 0));
    }

    @Test
    public void connectedTest3() {
        var builder = new GraphBuilder(2);
        builder.setRoot(1);
        MetricGraph g = builder.build();

        assertEquals(2, g.getVertexNumber());
        assertTrue(!Algorithms.isConnected(g, 0));
    }

    @Test
    public void connectedTest4() {
        var builder = new GraphBuilder(7);
        builder.addEdge(0, 1, 0);
        builder.addEdge(0, 2, 0);
        builder.addEdge(1, 3, 0);
        builder.addEdge(1, 4, 0);
        builder.addEdge(1, 5, 0);
        builder.addEdge(4, 6, 0);
        assertTrue(Algorithms.isConnected(builder.build(), 0));

        builder.addEdge(5, 2, 0);
        assertTrue(Algorithms.isConnected(builder.build(), 0));

        builder.addVertex(3);
        builder.addEdge(7, 8, 0);
        builder.addEdge(8, 9, 0);
        for (var i = 0; i < 10; ++i) {
            assertFalse(Algorithms.isConnected(builder.build(), 0));
        }

        builder.addEdge(2, 7, 0);
        assertTrue(Algorithms.isConnected(builder.build(), 0));
        builder.addEdge(0, 7, 0);
        for (var i = 0; i < 10; ++i) {
            assertTrue(Algorithms.isConnected(builder.build(), i));
        }
    }
}
