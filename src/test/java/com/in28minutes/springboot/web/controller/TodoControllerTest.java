package com.in28minutes.springboot.web.controller;

import com.in28minutes.springboot.web.controller.TodoController;
import com.in28minutes.springboot.web.service.TodoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoRepository repository;

    @Test
    public void showTodosWithOutUser() throws Exception {
        mvc.perform(get("/list-todos")).andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(value = "in28minutes")
    public void showTodos() throws Exception {
        mvc.perform(get("/list-todos")).andExpect(status().isOk());
    }
}