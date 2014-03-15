package com.qopuir.taskcontrol.quartz.tasklets;

import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_EJECUTION_DATE;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_NAME;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_SCHEDULE;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.qopuir.taskcontrol.entities.MailMessage;
import com.qopuir.taskcontrol.entities.UserVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.service.MailService;
import com.qopuir.taskcontrol.service.UserService;

public class ProjectionSemanalTasklet implements Tasklet {
	private static final Logger logger = LoggerFactory.getLogger(ProjectionSemanalTasklet.class);
	
	private final ControlName controlName = ControlName.PROJECTION_SEMANAL;
	
	@Value("${projectionSemanal.simulate}")
	private Boolean simulate;
	@Value("${projectionSemanal.url}")
	private String projectionUrl;
	@Value("${projectionSemanal.subject}")
	private String mailSubject;
	@Value("${projectionSemanal.from}")
	private String mailFrom;
	@Value("${projectionSemanal.mailTemplate}")
	private String mailTemplate;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	UserService userService;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info("Job {} (schedule {}) running", getParameter(chunkContext, PARAM_JOB_NAME), getParameter(chunkContext, PARAM_JOB_SCHEDULE));
		
		logger.debug("received parameter {} with value: {}", PARAM_EJECUTION_DATE, getParameter(chunkContext, PARAM_EJECUTION_DATE));
		
		List<UserVO> controlUsers = userService.listControlUsers(controlName);
		
		logger.debug("Users under control {}: {}", controlName, StringUtils.join(controlUsers, ","));
		
		for (UserVO userVO : controlUsers) {
			logger.debug("Checking projection of user {}", userVO.getUsername());
			
			if (simulate) {
				logger.info("Simulating...");
			} else {
				if (checkProjectionIncompleteUntilToday(userVO)) {
					logger.warn("User <{}> has projection incomplete at {}. Sending an email to <{}>", userVO.getUsername(), getParameter(chunkContext, PARAM_EJECUTION_DATE), userVO.getEmail());
					
					mailService.send(new MailMessage().setReceiverEmail(userVO.getEmail()).setSenderEmail(mailFrom).setSubject(mailSubject).setHtmlBody(templateEngine.process(mailTemplate, new Context())));
				}
			}
		}
		
		return RepeatStatus.FINISHED;
	}
	
	private boolean checkProjectionIncompleteUntilToday(UserVO userVO) {
		WebDriver driver = new HtmlUnitDriver();
        driver.get(projectionUrl);

        Select userField = new Select(driver.findElement(By.cssSelector("select#form_auth_name")));
        userField.selectByVisibleText(userVO.getUsername());

        WebElement element = driver.findElement(By.cssSelector("input#form_auth_password"));
        element.sendKeys(userVO.getPassword());
        
        element.submit();

        driver.get(projectionUrl + "/?page=home");
        
        List<WebElement> incompleteDays = driver.findElements(By.cssSelector("td.calendar-day p.day-number-red"));

        driver.quit();
        
        return incompleteDays.size() > 0;
	}
	
	private String getParameter(ChunkContext context, String paramName) {
		return context.getStepContext().getStepExecution().getJobParameters().getString(paramName);
	}
}