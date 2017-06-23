package com.github.yuri0x7c1.ofbiz.explorer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.yuri0x7c1.ofbiz.explorer.util.GraphUtil;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizUtil;
import com.vaadin.pontus.vizcomponent.model.Graph;

@Configuration
public class OfbizConfiguration {

	@Bean
	public OfbizInstance ofbizInstance() {
		return OfbizUtil.readInstance();
	}

	@Bean
	public Graph entityGraph() {
		return GraphUtil.createEntityGraph(ofbizInstance());
	}
}
