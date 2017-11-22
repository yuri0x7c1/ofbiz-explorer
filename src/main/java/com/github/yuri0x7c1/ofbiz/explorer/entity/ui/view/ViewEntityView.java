package com.github.yuri0x7c1.ofbiz.explorer.entity.ui.view;

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
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.ViewEntity;
import com.github.yuri0x7c1.ofbiz.explorer.generator.util.EntityGenerator;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizUtil;
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
@SpringView(name = "view-entities")
@SideBarItem(sectionId = Sections.VIEWS, caption = "View Entities", order = 1)
@VaadinFontIcon(VaadinIcons.DATABASE)
public class ViewEntityView extends CommonView implements View {

	private static final String VIEW_ENTITY_NAME_COL_ID = "entityName";
	private static final String VIEW_ENTITY_DESCRIPTION_COL_ID = "description";

	@Autowired
	private I18N i18n;

	@Autowired
	private OfbizInstance ofbizInstance;

	@Autowired
	private EntityGenerator entityGenerator;

	@Autowired
	private Environment env;

	private Grid<ViewEntity> viewEntityGrid = new Grid<>();

	private GridCellFilter<ViewEntity> viewEntityGridFilter;

	public ViewEntityView() {
		setHeight(100.0f, Unit.PERCENTAGE);
		viewEntityGrid.setHeight(100.0f, Unit.PERCENTAGE);
		addComponent(viewEntityGrid);
		setExpandRatio(viewEntityGrid, 1.0f);
	}

	@PostConstruct
	public void init() {
		setHeaderText(i18n.get("ViewEntities"));

		// entity grid
		viewEntityGrid.setItems(ofbizInstance.getAllViewEntites().values());
		viewEntityGrid.setWidth("100%");
		viewEntityGrid.addColumn(ViewEntity::getEntityName).setCaption(i18n.get("Entity.name")).setId(VIEW_ENTITY_NAME_COL_ID);
		viewEntityGrid.addColumn(ViewEntity::getDescription).setCaption(i18n.get("Description")).setId(VIEW_ENTITY_DESCRIPTION_COL_ID);

		viewEntityGrid.addColumn(viewEntity -> i18n.get("Generate"), // generate entity button
				new ButtonRenderer<ViewEntity>(clickEvent -> {

					Entity entity = OfbizUtil.viewEntityToEntity(clickEvent.getItem(), ofbizInstance);
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
		viewEntityGridFilter = new GridCellFilter<>(viewEntityGrid, ViewEntity.class);
		viewEntityGridFilter.setTextFilter(VIEW_ENTITY_NAME_COL_ID, true, false);
		viewEntityGridFilter.setTextFilter(VIEW_ENTITY_DESCRIPTION_COL_ID, true, false);
	}

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
