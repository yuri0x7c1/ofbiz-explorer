package com.github.yuri0x7c1.ofbiz.explorer.entity.ui.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;

import com.github.yuri0x7c1.ofbiz.explorer.common.navigation.util.NavigationUtil;
import com.github.yuri0x7c1.ofbiz.explorer.common.ui.view.CommonView;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Field;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Relation;
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

	private Grid<Relation> relationGrid;

	@PostConstruct
	public void init() {
		// back button
		addHeaderComponent(
			new MButton(VaadinIcons.ARROW_BACKWARD, i18n.get("Back"), (ClickListener) event -> NavigationUtil.back())
			.withStyleName(ValoTheme.BUTTON_PRIMARY)
		);

		// field grid
		fieldGrid = new Grid<>();
		fieldGrid.setWidth("100%");
		fieldGrid.setCaption(i18n.get("Fields"));
		fieldGrid.addColumn(Field::getName).setCaption(i18n.get("Field.name"));
		fieldGrid.addColumn(Field::getType).setCaption(i18n.get("Field.type"));
		fieldGrid.addColumn(Field::getDescription).setCaption(i18n.get("Description"));
		addComponent(fieldGrid);

		// relation grid
		relationGrid = new Grid<>();
		relationGrid.setWidth("100%");
		relationGrid.setCaption(i18n.get("Relations"));
		relationGrid.addColumn(Relation::getRelEntityName).setCaption(i18n.get("Relation.name"));
		relationGrid.addColumn(Relation::getFkName).setCaption(i18n.get("Relation.fkName"));
		relationGrid.addColumn(Relation::getType).setCaption(i18n.get("Relation.type"));
		relationGrid.addColumn(Relation::getDescription).setCaption(i18n.get("Description"));
		addComponent(relationGrid);
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
		relationGrid.setItems(entity.getRelation());

		setHeaderText(entityName);
	}
}
