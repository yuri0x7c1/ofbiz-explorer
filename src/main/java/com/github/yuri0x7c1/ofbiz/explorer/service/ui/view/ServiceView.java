package com.github.yuri0x7c1.ofbiz.explorer.service.ui.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.vaadin.gridutil.cell.GridCellFilter;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;

import com.github.yuri0x7c1.ofbiz.explorer.common.ui.sidebar.Sections;
import com.github.yuri0x7c1.ofbiz.explorer.common.ui.view.CommonView;
import com.github.yuri0x7c1.ofbiz.explorer.generator.util.ServiceFormGenerator;
import com.github.yuri0x7c1.ofbiz.explorer.generator.util.ServiceGenerator;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Service;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.renderers.ButtonRenderer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringView(name = "services")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Services", order = 2)
@VaadinFontIcon(VaadinIcons.COG)
public class ServiceView extends CommonView implements View {

	private static final String SERVICE_NAME_COL_ID = "name";
	private static final String SERVICE_DESCRIPTION_COL_ID = "description";

	@Autowired
	private I18N i18n;

	@Autowired
	private OfbizInstance ofbizInstance;

	@Autowired
	private Environment env;

	private Grid<Service> serviceGrid = new Grid<>();

	private GridCellFilter<Service> serviceGridFilter;


	@Autowired
	ServiceGenerator serviceGenerator;

	@Autowired
	ServiceFormGenerator serviceFormGenerator;

	public ServiceView() {
		setHeight(100.0f, Unit.PERCENTAGE);
		serviceGrid.setHeight(100.0f, Unit.PERCENTAGE);
		addComponent(serviceGrid);
		setExpandRatio(serviceGrid, 1.0f);
	}

	@PostConstruct
	public void init() {
		setHeaderText(i18n.get("Services"));

		serviceGrid.setItems(ofbizInstance.getAllServices().values());
		serviceGrid.setWidth("100%");
		serviceGrid.addColumn(Service::getName).setCaption(i18n.get("Service.name")).setId(SERVICE_NAME_COL_ID);
		serviceGrid.addColumn(Service::getDescription).setCaption(i18n.get("Description")).setId(SERVICE_DESCRIPTION_COL_ID);
		serviceGrid.addColumn(entity -> i18n.get("View"),
				new ButtonRenderer<Service>(clickEvent -> {
					getUI().getNavigator().navigateTo(ServiceDetailView.NAME + "/" + clickEvent.getItem().getName());
			    }));

		serviceGrid.addColumn(entity -> i18n.get("Generate"),
				new ButtonRenderer<Service>(clickEvent -> {

					Service service = clickEvent.getItem();
					log.debug("Service name : {}", service.getName());


					try {
						serviceGenerator.generate(service);
						serviceFormGenerator.generate(service);
						String msg = String.format("Service %s generated successfully to %s", service.getName(), env.getProperty("generator.destination_path"));
						log.info(msg);
						new Notification(msg,
							Notification.Type.HUMANIZED_MESSAGE)
							.show(Page.getCurrent());
					}
					catch (Exception e) {
						String msg = String.format("Generate service %s failed", service.getName());
						log.error(msg, e);
						new Notification(msg,
						    Notification.Type.ERROR_MESSAGE)
						    .show(Page.getCurrent());
					}
			    }));


		// init filters
		serviceGridFilter = new GridCellFilter<>(serviceGrid, Service.class);
		serviceGridFilter.setTextFilter(SERVICE_NAME_COL_ID, true, false);
		serviceGridFilter.setTextFilter(SERVICE_DESCRIPTION_COL_ID, true, false);
	}

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
