package com.github.yuri0x7c1.ofbiz.explorer.util;

import java.util.HashMap;
import java.util.Map;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Relation;
import com.vaadin.pontus.vizcomponent.model.Graph;

public class GraphUtil {
	public static final Graph createEntityGraph(OfbizInstance ofbizInstance) {
		Graph graph = new Graph("Entities", Graph.DIGRAPH);
		graph.setParam("ratio", "fill");
		// graph.setParam("size", "8.3, 11.7");

		graph.setNodeParameter("shape", "rectangle");
		Map<String, Graph.Node> nodes = new HashMap<>();

		final int LIMIT = 25;

		int i = 0;
		for (Entity entity : ofbizInstance.getAllEntities().values()) {
			if (i > LIMIT) break;
			i++;

			Graph.Node node = new Graph.Node(entity.getEntityName());
			nodes.put(entity.getEntityName(), node);
			// graph.addNode(node);
		}

		i = 0;
		for (Entity entity : ofbizInstance.getAllEntities().values()) {
			if (i > LIMIT) break;
			i++;

			for (Relation rel : entity.getRelation()) {
				Graph.Node node1 = nodes.get(entity.getEntityName());
				Graph.Node node2 = nodes.get(rel.getRelEntityName());

				if (node1 != null && node2 != null) {
					graph.addEdge(node1, node2);
				}
			}
		}

		return graph;
	}

}
