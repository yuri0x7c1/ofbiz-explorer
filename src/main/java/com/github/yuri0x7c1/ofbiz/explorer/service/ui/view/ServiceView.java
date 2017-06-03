package com.github.yuri0x7c1.ofbiz.explorer.service.ui.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;

import com.github.yuri0x7c1.ofbiz.explorer.common.ui.sidebar.Sections;
import com.github.yuri0x7c1.ofbiz.explorer.common.ui.view.CommonView;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Service;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = "services")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Services", order = 2)
@VaadinFontIcon(VaadinIcons.COG)
public class ServiceView extends CommonView implements View {
	@Autowired
	private I18N i18n;

	@Autowired
	private OfbizInstance ofbizInstance;

	private Grid<Service> serviceGrid = new Grid<>();

	@PostConstruct
	public void init() {
		setHeaderText(i18n.get("Services"));

		serviceGrid.setItems(ofbizInstance.getAllServices().values());
		serviceGrid.setWidth("100%");
		serviceGrid.addColumn(Service::getName).setCaption(i18n.get("Service.name"));
		serviceGrid.addColumn(Service::getDescription).setCaption(i18n.get("Description"));

		addComponent(serviceGrid);

	}

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
