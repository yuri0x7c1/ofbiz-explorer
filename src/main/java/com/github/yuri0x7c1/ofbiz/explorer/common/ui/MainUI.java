package com.github.yuri0x7c1.ofbiz.explorer.common.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.vaadin.spring.i18n.I18N;

import com.github.yuri0x7c1.ofbiz.explorer.common.ui.menu.CommonMenu;
import com.github.yuri0x7c1.ofbiz.explorer.common.ui.view.ErrorView;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

@SpringUI
@Theme("mytheme")
@Push(transport = Transport.WEBSOCKET)
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class MainUI extends UI {

	private static final long serialVersionUID = 1727100435082469359L;

	@Autowired
	I18N i18n;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    SpringViewProvider springViewProvider;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle(i18n.get("Application.name"));

        CommonMenu menu = applicationContext.getBean(CommonMenu.class);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        Navigator navigator = new Navigator(this, menu.getContent());

        navigator.addProvider(springViewProvider);
        navigator.setErrorView(ErrorView.class);
        navigator.navigateTo(navigator.getState());

        layout.addComponent(menu);

        setContent(menu);
    }
}
