package com.sb.computerupdatebot;

import java.text.ParseException;

public class MainMethodClass {

	public static void main(String[] args) throws InterruptedException, ParseException {
		
		UpdateBotChecker updateBot = new UpdateBotChecker();
		
		updateBot.accessSite();
		updateBot.loginToAccount();
		updateBot.navigateToOrderPage();
		updateBot.timerFunction();
        //updateBot.Exit();
	}

}
