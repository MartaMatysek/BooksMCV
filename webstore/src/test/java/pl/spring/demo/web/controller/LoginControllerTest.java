package pl.spring.demo.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.spring.demo.constants.ModelConstants;
import pl.spring.demo.controller.LoginController;
import pl.spring.demo.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private LoginController loginController;

	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).setViewResolvers(viewResolver).build();
	}
	
	@Test
	public void shouldLogin() throws Exception {
		//given when
		ResultActions resultActions = mockMvc.perform(get("/login"));

		// then
		resultActions.andExpect(view().name("login"));
	}
	
	@Test
	public void shouldCheckLoginError() throws Exception {
		//given when
		ResultActions resultActions = mockMvc.perform(get("/loginfailed"));

		// then
		resultActions.andExpect(view().name("login"));
	}
	
	@Test
	public void shouldLogout() throws Exception {
		//given when
		ResultActions resultActions = mockMvc.perform(get("/logout"));

		// then
		resultActions.andExpect(view().name("login"));
	}
	
	@Test
	public void shouldCheckAccessDeinedForUser() throws Exception{
		//given when
		ResultActions resultActions = mockMvc.perform(get("/403"));
		
		//then
		resultActions.andExpect(view().name("403")).andExpect(model().attribute(ModelConstants.ERROR_MESSAGE, "No valid user!"));
	}
	
	@Test
	public void shouldCheckAccessDeinedForAdmin() throws Exception{
		//given
		Principal user = new Principal() {
			@Override
			public String getName() {
				return "admin";
			}
		};
		
		//when
		ResultActions resultActions = mockMvc.perform(get("/403").principal(user));
		
		//then
		resultActions.andExpect(view().name("403")).andExpect(model().attribute(ModelConstants.ERROR_MESSAGE, "Action unavailable for " + user.getName()));
	}
	
	@Test
	public void shouldCheckAccessDeinedForLib() throws Exception{
		//given
		Principal user = new Principal() {
			@Override
			public String getName() {
				return "lib";
			}
		};
		
		//when
		ResultActions resultActions = mockMvc.perform(get("/403").principal(user));
		
		//then
		resultActions.andExpect(view().name("403")).andExpect(model().attribute(ModelConstants.ERROR_MESSAGE, "Action unavailable for lib"));
	}
	
			
}
