package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.JasperController;
import controller.TestController;

@Configuration
public class ControllerConfig {

	@Bean
	public TestController testController() {
		return new TestController();
	}
	
	@Bean
	public JasperController jasperController() {
		return new JasperController();
	}
	
	@Bean
	public QRcodeController qrCodeController() {
		return new QRcodeController();
	}
}
