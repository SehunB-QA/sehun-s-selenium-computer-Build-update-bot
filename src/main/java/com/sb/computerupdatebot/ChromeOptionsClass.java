package com.sb.computerupdatebot;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeOptionsClass {
	
	// Designed to return ChromeOptions to configure new ChromeDrivers in Selenium
			public static ChromeOptions chromeCfg() {
			 Map<String, Object> prefs = new HashMap<String, Object>();
			 ChromeOptions cOptions = new ChromeOptions();
			  
			 // Settings
			// prefs.put("profile.default_content_setting_values.cookies", 2);
			 //prefs.put("network.cookie.cookieBehavior", 2);
			 //prefs.put("profile.block_third_party_cookies", true);
			 //prefs.put("profile.default_content_settings.cookies", 2);

			 // Create ChromeOptions to disable Cookies pop-up
			 cOptions.setExperimentalOption("prefs", prefs);

			 return cOptions;
			 }

}
