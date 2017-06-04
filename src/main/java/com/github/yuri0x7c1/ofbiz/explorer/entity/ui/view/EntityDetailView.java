package com.github.yuri0x7c1.ofbiz.explorer.entity.ui.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;

import com.github.yuri0x7c1.ofbiz.explorer.common.navigation.util.NavigationUtil;
import com.github.yuri0x7c1.ofbiz.explorer.common.ui.view.CommonView;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Field;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringView(name = EntityDetailView.NAME)
public class EntityDetailView extends CommonView implements View {
	private static final long serialVersionUID = 719002900696360148L;

	public static final String NAME = "entity-detail";

	@Autowired
	private I18N i18n;

	@Autowired
	private OfbizInstance ofbizInstance;

	private Grid<Field> fieldGrid;

	@PostConstruct
	public void init() {
		// back button
		addHeaderComponent(
			new MButton(VaadinIcons.ARROW_BACKWARD, i18n.get("Back"), (ClickListener) event -> NavigationUtil.back())
			.withStyleName(ValoTheme.BUTTON_PRIMARY)
		);

		// field grid
		fieldGrid = new Grid<>();
		fieldGrid.addColumn(Field::getName).setCaption(i18n.get("Field.name"));
		fieldGrid.addColumn(Field::getType).setCaption(i18n.get("Field.type"));

		addComponent(fieldGrid);


	}

	@Override
	public void enter(ViewChangeEvent event) {
		// validate params
		if (event.getParameters() == null || event.getParameters().isEmpty()) {
			log.error("Parameters is empty");
			NavigationUtil.back();
		}

		String entityName = event.getParameters();
		Entity entity = ofbizInstance.getAllEntities().get(entityName);

		fieldGrid.setItems(entity.getField());

		setHeaderText(entityName);
	}
}
