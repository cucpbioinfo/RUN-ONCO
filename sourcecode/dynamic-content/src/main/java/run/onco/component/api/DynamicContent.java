package run.onco.component.api;

import java.io.Console;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import run.onco.component.api.common.ApplicationConfiguration;
import run.onco.component.api.dto.UserDto;
import run.onco.component.api.service.DynamicContentService;
import run.onco.component.api.util.StringUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class DynamicContent {
	private static ApplicationContext context;
	private static DynamicContentService dynamicContentService;

	static String username;
	static String password;
	static String dynaComponent;
	static UserDto user;
	
	static Console console;

	public static void main(String[] args) {
		// System.out.println("I:--START--:--Create DynamicComponent--");

		// StopWatch stopWatch = new StopWatch("Starting StopWatch");
		// stopWatch.start("initializing");

		context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		dynamicContentService = (DynamicContentService) context.getBean("dynamicContentService");

		try {

			console = System.console();
			
			if (console == null) {
	            System.out.println("Console is not supported");
	            System.exit(1);
	        }
			console.printf("-------------------------------------------------\n");
			console.printf(" Welcome to RUN-ONCO Dynamic Component Generator \n");
			console.printf("-------------------------------------------------\n");
			loginToSystem();
			readComponent();
			dynamicContentService.createDynamicContent(user, dynaComponent);
			System.out.println(String.format("Generate component %s successfully", dynaComponent));

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		// stopWatch.stop();
		// System.out.println(String.format("O:--SUCCESS:--Create
		// DynamicComponent--:ElapsedSeconds/%s", stopWatch.getTotalTimeSeconds()));
	}

	private static void readUsername() {
		boolean isValid = false;
		
		do {
			username = console.readLine("Enter Username : ");
			// System.out.println("You entered : " + username);
			if (!StringUtil.isNullOrWhitespace(username)) {
				isValid = true;
			} else {
				System.out.println("Username is required");
			}
		} while (!isValid);
	}
	
	private static void readPassword() {
		boolean isValid = false;
		
		do {
			System.out.print("Enter Password : ");
			char[] passString = console.readPassword();
			password = new String(passString);
			// System.out.println("You entered : " + password);

			if (!StringUtil.isNullOrWhitespace(password)) {
				isValid = true;
			} else {
				System.out.println("Password is required");
			}
		
		} while (!isValid);
	}
	
	private static void loginToSystem() {
		do {
			readUsername();
			readPassword();

			try {
				user = dynamicContentService.login(username, password);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		} while (user == null);
	}
	
	private static void readComponent() {
		boolean isValid = false;
		
		do {
			dynaComponent = console.readLine("Enter Component (e.g. tmb-score) : ");

			if (!StringUtil.isNullOrWhitespace(dynaComponent)) {
				isValid = true;
			} else {
				System.out.println("Component is required");
			}
		} while (!isValid);
	}
}
