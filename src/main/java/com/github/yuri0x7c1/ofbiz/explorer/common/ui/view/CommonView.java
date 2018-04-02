package com.github.yuri0x7c1.ofbiz.explorer.common.ui.view;

import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public abstract class CommonView extends VerticalLayout {
	private static final long serialVersionUID = 5974911975517183399L;

	private HorizontalLayout headerLayout;
	private Label headerLabel;
	private HorizontalLayout headerComponentLayout;

	public CommonView() {
		setMargin(new MarginInfo(false, false, false, false));

		headerLabel = new MLabel()
			.withStyleName(ValoTheme.LABEL_H2);

		headerComponentLayout = new MHorizontalLayout()
			.withSpacing(true);

		headerLayout = new MHorizontalLayout(
			headerLabel,
			headerComponentLayout)
			.withAlign(headerLabel, Alignment.MIDDLE_LEFT)
			.withAlign(headerComponentLayout, Alignment.MIDDLE_RIGHT)
			.withFullWidth();

		addComponent(headerLayout);
	}

	public void setHeaderText(String text) {
		headerLabel.setValue(text);
	}

	public void addHeaderComponent(Component c) {
		headerComponentLayout.addComponent(c);
	}
}
