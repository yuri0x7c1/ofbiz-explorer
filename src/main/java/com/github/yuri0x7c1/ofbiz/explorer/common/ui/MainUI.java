package com.github.yuri0x7c1.ofbiz.explorer.common.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.sidebar.components.ValoSideBar;

import com.github.yuri0x7c1.ofbiz.explorer.common.ui.view.ErrorView;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

@SpringUI
@Theme("mytheme")
@Push(transport = Transport.WEBSOCKET)
// @Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class MainUI extends UI {

	@Autowired
	I18N i18n;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    SpringViewProvider springViewProvider;

    @Autowired
    ValoSideBar sideBar;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle(i18n.get("Application.name"));

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        layout.addComponent(sideBar);

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("view-container");
        viewContainer.setSizeFull();
        layout.addComponent(viewContainer);
        layout.setExpandRatio(viewContainer, 1f);

        Navigator navigator = new Navigator(this, viewContainer);

        navigator.addProvider(springViewProvider);
        navigator.setErrorView(ErrorView.class);
        navigator.navigateTo(navigator.getState());

        setContent(layout); // Call this here because the Navigator must have been configured before the Side Bar can be attached to a UI.
    }
}
