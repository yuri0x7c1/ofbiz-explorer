package com.github.yuri0x7c1.ofbiz.explorer.entity.ui.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;

import com.github.yuri0x7c1.ofbiz.explorer.common.ui.sidebar.Sections;
import com.github.yuri0x7c1.ofbiz.explorer.common.ui.view.CommonView;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.ViewEntity;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = "view-entities")
@SideBarItem(sectionId = Sections.VIEWS, caption = "View Entities", order = 1)
@VaadinFontIcon(VaadinIcons.DATABASE)
public class ViewEntityView extends CommonView implements View {
	@Autowired
	private I18N i18n;

	@Autowired
	private OfbizInstance ofbizInstance;

	@Autowired
	private Environment env;

	private Grid<ViewEntity> viewEntityGrid = new Grid<>();

	@PostConstruct
	public void init() {
		setHeaderText(i18n.get("ViewEntities"));

		// entity grid
		viewEntityGrid.setItems(ofbizInstance.getAllViewEntites().values());
		viewEntityGrid.setWidth("100%");
		viewEntityGrid.addColumn(ViewEntity::getEntityName).setCaption(i18n.get("Entity.name"));
		viewEntityGrid.addColumn(ViewEntity::getDescription).setCaption(i18n.get("Description"));
		
		addComponent(viewEntityGrid);

	}

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
