package com.github.yuri0x7c1.ofbiz.explorer.common.ui.view;

import javax.annotation.PostConstruct;

import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.label.MLabel;

import com.github.yuri0x7c1.ofbiz.explorer.common.ui.sidebar.Sections;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance.Component;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance.ComponentDirectory;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizUtil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = "")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Home", order = 0)
@VaadinFontIcon(VaadinIcons.HOME)
public class HomeView extends VerticalLayout implements View {
	private Label tree = new MLabel().withContentMode(ContentMode.HTML);
	private OfbizInstance ofbizInstance;

	@PostConstruct
    public void init() {
    	ofbizInstance = OfbizUtil.readInstance();
    	StringBuilder html = new StringBuilder("<ul>");

    	for (ComponentDirectory componentDirectory : ofbizInstance.getComponentDirectories()) {
    		log.info("component directory {}", componentDirectory.getName());
    		html.append("<li>" + componentDirectory.getName());
    		html.append("<ul>");
    		for (Component component : componentDirectory.getComponents()) {
    			log.info("component  {}", component.getName());
    			html.append("<li>" + component.getName());
    			html.append("<ul>");
    			for (Entity entity : component.getEntities()) {
    				html.append("<li>" + entity.getEntityName() + "</li>");
    			}
    			html.append("</ul>");
    			html.append("</li>");
    		}
    		html.append("</ul>");
    		html.append("</li>");
    	}
    	html.append("</ul>");

    	tree.setValue(html.toString());

    	addComponent(tree);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
