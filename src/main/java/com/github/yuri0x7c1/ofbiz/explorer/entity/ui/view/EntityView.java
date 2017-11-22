package com.github.yuri0x7c1.ofbiz.explorer.entity.ui.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.vaadin.gridutil.cell.GridCellFilter;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;

import com.github.yuri0x7c1.ofbiz.explorer.common.ui.sidebar.Sections;
import com.github.yuri0x7c1.ofbiz.explorer.common.ui.view.CommonView;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.generator.util.EntityGenerator;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringView(name = "entities")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Entities", order = 1)
@VaadinFontIcon(VaadinIcons.DATABASE)
public class EntityView extends CommonView implements View {

	private static final String ENTITY_NAME_COL_ID = "entityName";
	private static final String ENTITY_DESCRIPTION_COL_ID = "description";

	@Autowired
	private I18N i18n;

	@Autowired
	private OfbizInstance ofbizInstance;

	@Autowired
	private EntityGenerator entityGenerator;

	@Autowired
	private Environment env;

	private Grid<Entity> entityGrid = new Grid<>();

	private GridCellFilter<Entity> entityGridFilter;

	private Button generateAllButton = new Button("Generate all");

	public EntityView() {
		setHeight(100.0f, Unit.PERCENTAGE);
		addHeaderComponent(generateAllButton);
		entityGrid.setHeight(100.0f, Unit.PERCENTAGE);
		addComponent(entityGrid);
		setExpandRatio(entityGrid, 1.0f);
	}

	@PostConstruct
	public void init() {
		setHeaderText(i18n.get("Entities"));

		// generate all button
		generateAllButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		generateAllButton.addClickListener(event -> {
			List<String> errorEntities = new ArrayList<>();
			log.info("Generating all entities");
			for (Entity entity : ofbizInstance.getAllEntities().values()) {
				try {
					entityGenerator.generate(entity);
					String msg = String.format("Entity %s generated successfully to %s", entity.getEntityName(), env.getProperty("generator.destination_path"));
					log.info(msg);
				}
				catch (Exception e) {
					errorEntities.add(entity.getEntityName());
					String msg = String.format("Generate entity %s failed", entity.getEntityName());
					log.error(msg, e);
				}
			}

			if (errorEntities.isEmpty()) {
				new Notification("Entities generated!",
						Notification.Type.HUMANIZED_MESSAGE)
						.show(Page.getCurrent());
			}
			else {
				new Notification("Entities generated with problems in:" + String.join(", ", errorEntities),
						Notification.Type.WARNING_MESSAGE)
						.show(Page.getCurrent());
			}
		});


		// entity grid
		entityGrid.setItems(ofbizInstance.getAllEntities().values());
		entityGrid.setWidth("100%");
		entityGrid.addColumn(Entity::getEntityName).setCaption(i18n.get("Entity.name")).setId(ENTITY_NAME_COL_ID);
		entityGrid.addColumn(Entity::getDescription).setCaption(i18n.get("Description")).setId(ENTITY_DESCRIPTION_COL_ID);
		entityGrid.addColumn(entity -> i18n.get("View"), // view entity button
				new ButtonRenderer<Entity>(clickEvent -> {
					getUI().getNavigator().navigateTo(EntityDetailView.NAME + "/" + clickEvent.getItem().getEntityName());
			    }));

		entityGrid.addColumn(entity -> i18n.get("Generate"), // generate entity button
				new ButtonRenderer<Entity>(clickEvent -> {

					Entity entity = clickEvent.getItem();
					log.debug("Entity name : {}", entity.getEntityName());

					try {
						entityGenerator.generate(entity);
						String msg = String.format("Entity %s generated successfully to %s", entity.getEntityName(), env.getProperty("generator.destination_path"));
						log.info(msg);
						new Notification(msg,
							Notification.Type.HUMANIZED_MESSAGE)
							.show(Page.getCurrent());
					}
					catch (Exception e) {
						String msg = String.format("Generate entity %s failed", entity.getEntityName());
						log.error(msg, e);
						new Notification(msg,
						    Notification.Type.ERROR_MESSAGE)
						    .show(Page.getCurrent());
					}
			    }));

		// init filters
		entityGridFilter = new GridCellFilter<>(entityGrid, Entity.class);
		entityGridFilter.setTextFilter(ENTITY_NAME_COL_ID, true, false);
		entityGridFilter.setTextFilter(ENTITY_DESCRIPTION_COL_ID, true, false);
	}

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
