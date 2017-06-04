package com.github.yuri0x7c1.ofbiz.explorer.service.ui.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;

import com.github.yuri0x7c1.ofbiz.explorer.common.navigation.util.NavigationUtil;
import com.github.yuri0x7c1.ofbiz.explorer.common.ui.view.CommonView;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Attribute;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Service;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizUtil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringView(name = ServiceDetailView.NAME)
public class ServiceDetailView extends CommonView implements View {
	private static final long serialVersionUID = 719002900696360148L;

	public static final String NAME = "service-detail";

	@Autowired
	private I18N i18n;

	@Autowired
	private OfbizInstance ofbizInstance;

	private Grid<Attribute> parametersGrid;

	@PostConstruct
	public void init() {
		// back button
		addHeaderComponent(
			new MButton(VaadinIcons.ARROW_BACKWARD, i18n.get("Back"), (ClickListener) event -> NavigationUtil.back())
			.withStyleName(ValoTheme.BUTTON_PRIMARY)
		);

		// field grid
		parametersGrid = new Grid<>();
		parametersGrid.setWidth("100%");
		parametersGrid.setCaption(i18n.get("Parameters"));
		parametersGrid.addColumn(Attribute::getName).setCaption(i18n.get("Parameter.name"));
		addComponent(parametersGrid);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// validate params
		if (event.getParameters() == null || event.getParameters().isEmpty()) {
			log.error("Parameters is empty");
			NavigationUtil.back();
		}

		String serviceName = event.getParameters();
		Service service = ofbizInstance.getAllServices().get(serviceName);

		parametersGrid.setItems(OfbizUtil.filterServiceAttributes(service.getAutoAttributesOrAttribute()));

		setHeaderText(serviceName);
	}
}
