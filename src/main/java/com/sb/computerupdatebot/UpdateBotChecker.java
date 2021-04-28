package com.sb.computerupdatebot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;


public class UpdateBotChecker {


	private String newFirstLineString = "";
	private String newSecondLineString = "";
	private static RemoteWebDriver driver;
	private int numOfEmailsCounter;
	
	
	public String getNewFirstLineString() {
		return newFirstLineString;
	}



	public void setNewFirstLineString(String newFirstLineString) {
		this.newFirstLineString = newFirstLineString;
	}



	public String getNewSecondLineString() {
		return newSecondLineString;
	}



	public void setNewSecondLineString(String newSecondLineString) {
		this.newSecondLineString = newSecondLineString;
	}

	public void timerFunction() throws ParseException {
		Date  date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		dateFormat.format(date);
		
		if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("9:00") ) && dateFormat.parse(dateFormat.format(date)).before(dateFormat.parse("17:00")))
		{
		Timer timer = new Timer();
       
		
		TimerTask updateBotTask = new TimerTask() {
			
	
			@Override
			public void run() 
			{
              orderText();
			}
		};
		
		
       timer.schedule(updateBotTask,0l,  7200000); //3 Hours 10800000, 2 Hours 7200000
		}
		
		else if (dateFormat.parse(dateFormat.format(date)).equals(dateFormat.parse("17:00")))
				{
			    System.out.println("It's now past 5:00pm!");
			    timerFunction();
			    // Exit();			
				}
		//Check time is before 9:00 AM (Company opens at 9AM)
		if (dateFormat.parse(dateFormat.format(date)).before(dateFormat.parse("9:00"))) {
			
			System.out.println("It's not time yet!");
			
			Timer timer = new Timer();
		       
			
			TimerTask CheckTimeTask = new TimerTask() {
				
		
				@Override
				public void run() 
				{
					try {
						// Invoke functiom again to repeat the process time check 
						timerFunction();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			timer.schedule(CheckTimeTask,7200000,  7200000); // 600000  - 10 mintues 
			
			
		}
		
	
	}

	public void accessSite() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1366,768));
		//Website Link
		driver.get("");
        WebElement loginPageLink = driver.findElement(By.xpath(""));
        loginPageLink.click();
	}
	

	
	public void loginToAccount() throws InterruptedException
	{
		// WebDriverWait wait = new WebDriverWait(driver, 120);
		WebElement usernameBox  = driver.findElement(By.xpath(""));
		usernameBox.click();
		usernameBox.sendKeys("");
		
		WebElement passwordBox = driver.findElement(By.xpath(""));
		passwordBox.click();
		passwordBox.sendKeys("");
		
		//TODO: Add better option of a wait // change to a better wait solution 
		
		// Sleep to manually perform Captcha 
		Thread.sleep(90000);
		
		System.out.println("Thread resumed");
		
		
		WebElement loginBox = driver.findElement(By.xpath(""));
	    loginBox.click();
	}
	
	
	public void navigateToOrderPage()
	{
		WebElement orderPageLink = driver.findElement(By.xpath(""));
		orderPageLink.click();
		
		WebElement viewButton = driver.findElement(By.xpath(""));
		viewButton.click();
		
	}
	
	
	public void orderText()
	{
		driver.navigate().refresh();
		WebElement firstLine = driver.findElement(By.xpath(""));
		
		WebElement secondLine = driver.findElement(By.xpath(""));
		
		
		setNewFirstLineString(firstLine.getText());
		setNewSecondLineString(secondLine.getText());
		
		//newFirstLineString = firstLine.getText();
		//newSecondLineString = secondLine.getText();
		
		System.out.println(newFirstLineString);
		System.out.println(newSecondLineString);
		
		driver.navigate().refresh();
		sendUpdateEmail();
		//timerFunction();
	}
	
	
	public void sendUpdateEmail() 
	{
		//Special Credit : https://netcorecloud.com/tutorials/send-email-in-java-using-gmail-smtp/
		// https://stackabuse.com/how-to-get-current-date-and-time-in-java/
		
		//Send email with the current date and time
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String curentDateAndTime = formatter.format(date);
		
		// email address 
		String toEmail = "example@example.com";
		String fromEmail = "example@example.com";
		
		String host = "smtp.gmail.com";
		
		//Get System Properties
		Properties properties = System.getProperties();
		
		//Setup Mail Server
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		//Get Session Object & and login details
		
		//Email Sender Login credentials
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication("example@example.com", "*********");
			}
		});

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(fromEmail));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            // Set Subject: header field
            message.setSubject("Current Computer");

            // Now set the actual message
            message.setText(getNewFirstLineString() + "\n" + getNewSecondLineString() + "\n"
            + curentDateAndTime);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            numOfEmailsCounter++;
            System.out.println("The total number of emails that have been sent is:" + numOfEmailsCounter);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

				
	
	
	
	public void Exit()
	{
		driver.quit();
	}
	
	
	
	
	
	
}
