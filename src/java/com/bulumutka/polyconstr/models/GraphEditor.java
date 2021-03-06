package com.bulumutka.polyconstr.models;

import com.bulumutka.polyconstr.models.graphlib.GraphBuilder;
import com.bulumutka.polyconstr.models.graphlib.MetricGraph;
import com.bulumutka.polyconstr.ui.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Stack;

public class GraphEditor {
    private final GraphBuilder builder = new GraphBuilder();
    private final ObservableList<Drawable> components =
            FXCollections.observableList(new ArrayList<>());
    private final Stack<Vertex2D<Integer>> stack = new Stack<>();
    private EditMode mode = EditMode.NONE;
    private int vertexNumber = 0;
    private boolean isEmpty = true;
    private boolean hasStartVertex;

    public void setEditMode(EditMode mode) {
        System.out.println("setEditMode: " + mode);
        this.mode = mode;
    }

    public boolean hasStartVertex() {
        return hasStartVertex;
    }

    public GraphEditor(GraphCanvas canvas) {
        canvas.setData(components);
        canvas.setOnMouseClicked(event -> {
            System.out.println("canvas was clicked at: " + event.getX() + " " + event.getY());

            switch (mode) {
                case ADD_EDGE:
                    addEdge(event.getX(), event.getY());
                    break;
                case ADD_VERTEX:
                    addVertex(event.getX(), event.getY());
                    break;
                case START_VERTEX:
                    hasStartVertex = true;
                    var vertex = getVertex(event.getX(), event.getY());
                    if (vertex != null) {
                        builder.setRoot(vertex.getVertex());
                        for (var c : components) {
                            if (c instanceof Vertex2D) {
                                ((Vertex2D) c).setIsStart(false);
                            }
                        }
                        vertex.setIsStart(true);
                        canvas.draw();
                    }
                    break;
                default:
                    System.out.println("NONE action.");
                    break;
            }
        });

        components.addListener((ListChangeListener<? super Drawable>) changeEvent -> {
            System.out.println("Data components changed.");
            canvas.draw();
        });
    }

    public void reset() {
        hasStartVertex = false;
        isEmpty = true;
        builder.reset();
        stack.clear();
        vertexNumber = 0;
        mode = EditMode.NONE;
        components.clear();
    }

    public boolean hasGraph() {
        return !isEmpty;
    }

    private void addVertex(double x, double y) {
        isEmpty = false;
        builder.addVertex();
        components.add(new Vertex2D<>(vertexNumber++, x, y));
    }

    private void addEdge(double x, double y) {
        isEmpty = false;
        Vertex2D<Integer> vertex = getVertex(x, y);
        if (vertex == null) {
            return;
        }
        stack.push(vertex);
        if (stack.size() == 2) {
            var source = stack.pop();
            var target = stack.pop();
            DialogWindow.edgeWeightSymbDialog().ifPresent(time -> {
                components.add(new Edge2D<>(source, target, time));
                builder.addEdge(source.getVertex(), target.getVertex(), time);
            });
        }
    }

    private Vertex2D<Integer> getVertex(double x, double y) {
        var element = components.filtered(d -> d instanceof Vertex2D && d.contains(x, y));
        if (element.isEmpty()) {
            return null;
        }
        return (Vertex2D<Integer>) element.get(0);
    }

    public MetricGraph getGraph() {
        return builder.build();
    }
}
