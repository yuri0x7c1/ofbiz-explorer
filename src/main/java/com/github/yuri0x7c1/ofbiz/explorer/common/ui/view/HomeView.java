package com.github.yuri0x7c1.ofbiz.explorer.common.ui.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;

import com.github.yuri0x7c1.ofbiz.explorer.common.ui.sidebar.Sections;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
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

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = "")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Home", order = 0)
@VaadinFontIcon(VaadinIcons.HOME)
public class HomeView extends CommonView implements View {
	private Tree<TreeNode> tree;

	@Autowired
	private OfbizInstance ofbizInstance;

	@RequiredArgsConstructor
	private class TreeNode {
		@Getter
		@Setter
		@NonNull
		private String name;

		@Override
		public String toString() {
			return name;
		}
	}

	@PostConstruct
    public void init() {
		setHeaderText("Home");

    	TreeData<TreeNode> treeData = new TreeData<>();
    	for (ComponentGroup componentGroup : ofbizInstance.getComponentGroups().values()) {
    		TreeNode componentGroupNode = new TreeNode(componentGroup.getName());
    		treeData.addItem(null, componentGroupNode);

    		for (Component component : componentGroup.getComponents().values()) {
    			TreeNode componentNode = new TreeNode(component.getName());
    			treeData.addItem(componentGroupNode, componentNode);

    			if (!component.getEntities().isEmpty()) {
    				TreeNode entitydefNode = new TreeNode(OfbizUtil.ENTITYDEF_DIRECTORY_NAME);
    				treeData.addItem(componentNode, entitydefNode);
    				for (Entity entity : component.getEntities().values()) {
    					treeData.addItem(entitydefNode, new TreeNode(entity.getEntityName()));
    				}
    			}
    		}
    	}

    	tree = new Tree<>(new TreeDataProvider<>(treeData));
    	addComponent(tree);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
