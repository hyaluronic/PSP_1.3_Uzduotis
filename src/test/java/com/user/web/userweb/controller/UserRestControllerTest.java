package com.user.web.userweb.controller;

import com.user.web.userweb.model.User;
import com.user.web.userweb.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(value = UserRestController.class)
class UserRestControllerTest {

    private static final User USER_1 = new User("Vardas", "Pavarde", "865345672", "vardaspavarde@yahoo.com", "adresas 1", "mypasSSsword123456@##");
    private static final User USER_2 = new User("Vardenis", "Pavardenis", "865345675", "pavarde@google.com", "adresas 5", "my34#$%wordAS5s5");
    private static final User USER_3 = new User("Vardenis", "Pavardenis", "512345", "pavarde@google.com", "adresas 5", "my34#$%wordAS5s5");
    private static final User USER_4 = new User("Vardenis", "Pavardenis", "865345675", "pavarde@google.com", "adresas 5", "Asss2");

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(USER_1);
        users.add(USER_2);
        when(userService.findAll()).thenReturn(users);
        RequestBuilder rb = MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String expected = "[" +
                "{\"name\":\"Vardas\",\"surname\":\"Pavarde\",\"phoneNumber\":\"865345672\",\"email\":\"vardaspavarde@yahoo.com\",\"address\":\"adresas 1\",\"password\":\"mypasSSsword123456@##\"}," +
                "{\"id\":0,\"name\":\"Vardenis\",\"surname\":\"Pavardenis\",\"phoneNumber\":\"865345675\",\"email\":\"pavarde@google.com\",\"address\":\"adresas 5\",\"password\":\"my34#$%wordAS5s5\"}" +
                "]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void getUserById() throws Exception {
        when(userService.findById(Mockito.anyInt())).thenReturn(USER_1);
        RequestBuilder rb = MockMvcRequestBuilders
                .get("/users/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String expected = "{\"name\":\"Vardas\",\"surname\":\"Pavarde\",\"phoneNumber\":\"865345672\",\"email\":\"vardaspavarde@yahoo.com\",\"address\":\"adresas 1\",\"password\":\"mypasSSsword123456@##\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void registerUser() throws Exception {
        when(userService.add(Mockito.any(User.class))).thenReturn(USER_2);
        String userJson = "{\"name\":\"Vardenis\",\"surname\":\"Pavardenis\",\"phoneNumber\":\"865345675\",\"email\":\"pavarde@google.com\",\"address\":\"adresas 5\",\"password\":\"my34#$%wordAS5s5\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        JSONAssert.assertEquals(userJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    void updateUserById() throws Exception {
        when(userService.existsById(Mockito.anyInt())).thenReturn(true);
        when(userService.update(Mockito.any(User.class))).thenReturn(USER_2);
        String userJson = "{\"id\":0,\"name\":\"Vardenis\",\"surname\":\"Pavardenis\",\"phoneNumber\":\"865345675\",\"email\":\"pavarde@google.com\",\"address\":\"adresas 5\",\"password\":\"my34#$%wordAS5s5\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/0")
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        JSONAssert.assertEquals(userJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    void deleteUserById() throws Exception {
        //adding user
        when(userService.add(Mockito.any(User.class))).thenReturn(USER_2);
        String userJson = "{\"name\":\"Vardenis\",\"surname\":\"Pavardenis\",\"phoneNumber\":\"865345675\",\"email\":\"pavarde@google.com\",\"address\":\"adresas 5\",\"password\":\"my34#$%wordAS5s5\"}";
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder2)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        JSONAssert.assertEquals(userJson, result.getResponse().getContentAsString(), false);
        //deleting the same user
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/users/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        verify(userService).deleteById(Mockito.anyInt());
        //checking no user exists
        RequestBuilder rb = MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result2 = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String expected = "[]";
        JSONAssert.assertEquals(expected, result2.getResponse().getContentAsString(), false);
    }

    @Test
    void addPhoneValidatorCountryPrefix() throws Exception {
        //added new validator CountryPrefix
        String countryPrefixJson = "{\"prefix\": \"+371\",\"length\": 5,\"trunk\": \"5\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/phoneValidatorCountryPrefix")
                .accept(MediaType.APPLICATION_JSON)
                .param("countryCode", "AA")
                .content(countryPrefixJson)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //add user with phone number to verify new validator CountryPrefix
        when(userService.add(Mockito.any(User.class))).thenReturn(USER_3);
        String userJson = "{\"name\":\"Vardenis\",\"surname\":\"Pavardenis\",\"phoneNumber\":\"512345\",\"email\":\"pavarde@google.com\",\"address\":\"adresas 5\",\"password\":\"my34#$%wordAS5s5\"}";
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder2)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        JSONAssert.assertEquals(userJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    void setCustomPasswordCheckerRules() throws Exception {
        //added new validator CountryPrefix
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/passwordChecker")
                .accept(MediaType.APPLICATION_JSON)
                .param("specialSymbols", "ABC")
                .param("minLength", "5")
                .param("needsUppercase", "true")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //add user with password to verify custom PasswordChecker rules
        when(userService.add(Mockito.any(User.class))).thenReturn(USER_4);
        String userJson = "{\"name\":\"Vardenis\",\"surname\":\"Pavardenis\",\"phoneNumber\":\"865345675\",\"email\":\"pavarde@google.com\",\"address\":\"adresas 5\",\"password\":\"Asss2\"}";
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder2)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        JSONAssert.assertEquals(userJson, result.getResponse().getContentAsString(), false);
    }
}