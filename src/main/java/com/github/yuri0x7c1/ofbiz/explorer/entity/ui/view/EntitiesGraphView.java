package com.github.yuri0x7c1.ofbiz.explorer.entity.ui.view;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;

import com.github.yuri0x7c1.ofbiz.explorer.common.ui.sidebar.Sections;
import com.github.yuri0x7c1.ofbiz.explorer.common.ui.view.CommonView;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Relation;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.pontus.vizcomponent.VizComponent;
import com.vaadin.pontus.vizcomponent.model.Graph;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = "entity-graph")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Entity graph", order = 2)
@VaadinFontIcon(VaadinIcons.SPLIT)
public class EntitiesGraphView extends CommonView implements View {
	private static final long serialVersionUID = -7659105089999705645L;

	@Autowired
	private I18N i18n;

	@Autowired
	private OfbizInstance ofbizInstance;

	@PostConstruct
	public void init() {
		setHeaderText(i18n.get("Entity graph"));

		final VizComponent vizComponent = new VizComponent();
		vizComponent.setSizeFull();

		Graph graph = new Graph("Entities", Graph.GRAPH);
		Map<String, Graph.Node> nodes = new HashMap<>();

		int i = 0;
		for (Entity entity : ofbizInstance.getAllEntities().values()) {
			if (i > 100) break;
			i++;

			Graph.Node node = new Graph.Node(entity.getEntityName());
			nodes.put(entity.getEntityName(), node);
			// graph.addNode(node);
		}

		i = 0;
		for (Entity entity : ofbizInstance.getAllEntities().values()) {
			if (i > 100) break;
			i++;

			for (Relation rel : entity.getRelation()) {
				Graph.Node node1 = nodes.get(entity.getEntityName());
				Graph.Node node2 = nodes.get(rel.getRelEntityName());

				if (node1 != null && node2 != null) {
					graph.addEdge(node1, node2);
				}
			}
		}

        setSizeFull();
        vizComponent.drawGraph(graph);
        addComponent(vizComponent);
        setExpandRatio(vizComponent, 1);
        setComponentAlignment(vizComponent, Alignment.MIDDLE_CENTER);


	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}

