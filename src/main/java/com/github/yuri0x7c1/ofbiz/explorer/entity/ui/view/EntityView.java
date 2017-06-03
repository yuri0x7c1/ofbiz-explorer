package com.github.yuri0x7c1.ofbiz.explorer.entity.ui.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;

import com.github.yuri0x7c1.ofbiz.explorer.common.ui.sidebar.Sections;
import com.github.yuri0x7c1.ofbiz.explorer.common.ui.view.CommonView;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = "entities")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Entities", order = 1)
@VaadinFontIcon(VaadinIcons.DATABASE)
public class EntityView extends CommonView implements View {
	@Autowired
	private I18N i18n;

	@Autowired
	private OfbizInstance ofbizInstance;

	private Grid<Entity> entityGrid = new Grid<>();

	@PostConstruct
	public void init() {
		setHeaderText(i18n.get("Entities"));

		entityGrid.setItems(ofbizInstance.getAllEntities().values());
		entityGrid.setWidth("100%");
		entityGrid.addColumn(Entity::getEntityName).setCaption(i18n.get("Entity.name"));
		entityGrid.addColumn(Entity::getDescription).setCaption(i18n.get("Description"));

		addComponent(entityGrid);

	}

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
