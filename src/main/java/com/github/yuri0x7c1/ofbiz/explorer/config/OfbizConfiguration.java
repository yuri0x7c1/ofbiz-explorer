package com.github.yuri0x7c1.ofbiz.explorer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizUtil;

@Configuration
public class OfbizConfiguration {

	@Bean
	public OfbizInstance ofbizInstance() {
		return OfbizUtil.readInstance();
	}
}
