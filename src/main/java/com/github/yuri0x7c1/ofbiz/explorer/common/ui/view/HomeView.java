package com.github.yuri0x7c1.ofbiz.explorer.common.ui.view;

import javax.annotation.PostConstruct;

import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;

import com.github.yuri0x7c1.ofbiz.explorer.common.ui.sidebar.Sections;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance.Component;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance.ComponentGroup;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizUtil;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = "")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Home", order = 0)
@VaadinFontIcon(VaadinIcons.HOME)
public class HomeView extends VerticalLayout implements View {
	private Tree<String> tree;
	private OfbizInstance ofbizInstance;

	@PostConstruct
    public void init() {
    	ofbizInstance = OfbizUtil.readInstance();

    	TreeData<String> treeData = new TreeData<>();
    	for (ComponentGroup componentGroup : ofbizInstance.getComponentGroups()) {
    		treeData.addItem(null, componentGroup.getName());

    		for (Component component : componentGroup.getComponents()) {
    			treeData.addItem(componentGroup.getName(), component.getName());
    		}
    	}

    	tree = new Tree<>(new TreeDataProvider<>(treeData));
    	addComponent(tree);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
