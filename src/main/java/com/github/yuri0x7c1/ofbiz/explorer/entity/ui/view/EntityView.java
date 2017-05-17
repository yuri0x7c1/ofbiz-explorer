package com.github.yuri0x7c1.ofbiz.explorer.entity.ui.view;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.core.io.ClassPathResource;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;

import com.github.yuri0x7c1.ofbiz.explorer.common.ui.sidebar.Sections;
import com.github.yuri0x7c1.ofbiz.explorer.common.ui.view.CommonView;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entitymodel;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = "entities")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Entities", order = 1)
@VaadinFontIcon(VaadinIcons.LIST)
public class EntityView extends CommonView implements View {
	private List<Entity> entities = new ArrayList<>();
	private Grid<Entity> entityGrid = new Grid<>();

	public EntityView() {
		setHeaderText("Entities");

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("com.github.yuri0x7c1.ofbiz.explorer.entity.xml");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			Entitymodel entitymodel  = (Entitymodel) unmarshaller.unmarshal(
					new FileInputStream(new ClassPathResource("entitymodel.xml").getFile())
			);

			for (Object o : entitymodel.getEntityOrViewEntityOrExtendEntity()) {
				if (o instanceof Entity) {
					entities.add((Entity)o);
				}
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		entityGrid.setItems(entities);
		entityGrid.setWidth("100%");
		entityGrid.addColumn(Entity::getEntityName).setCaption("Entity name");
		entityGrid.addColumn(Entity::getDescription).setCaption("Desctription");

		addComponent(entityGrid);

	}

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
