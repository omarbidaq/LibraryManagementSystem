package com.maidscc.lms;

import com.maidscc.lms.Configuration.SecurityConfig;
import com.maidscc.lms.Controller.PatronController;
import com.maidscc.lms.Entity.Patron;
import com.maidscc.lms.Service.PatronService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatronController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class))
public class PatronControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    private Patron patron;

    @BeforeEach
    void setUp() {
        patron = new Patron();
        patron.setId(1);
        patron.setName("Omar");
        patron.setContactInfo("Cairo");
    }

    @AfterEach
    void tearDown() {
        reset(patronService);
    }

    @Test
    @WithMockUser(username="user",roles={"USER","ADMIN"})
    void getAllPatronsTest() throws Exception {
        given(patronService.getAllPatrons()).willReturn(List.of(patron));
        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(patron.getId()))
                .andExpect(jsonPath("$[0].name").value(patron.getName()))
                .andExpect(jsonPath("$[0].contactInfo").value(patron.getContactInfo()));
    }

    @Test
    @WithMockUser(username="user",roles={"USER","ADMIN"})
    void getPatronByIdTest() throws Exception {
        given(patronService.getPatronById(1)).willReturn(patron);
        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patron.getId()))
                .andExpect(jsonPath("$.name").value(patron.getName()))
                .andExpect(jsonPath("$.contactInfo").value(patron.getContactInfo()));
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void createPatronTest() throws Exception {
        given(patronService.addPatron(any(Patron.class))).willReturn(patron);
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Omar\",\"contactInfo\":\"Cairo\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Omar"))
                .andExpect(jsonPath("$.contactInfo").value("Cairo"));
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void updatePatronTest() throws Exception {
        given(patronService.updatePatron(eq(1),any(Patron.class))).willReturn(patron);
        patron.setName("Omar Emad");
        patron.setContactInfo("Giza");

        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"omar\",\"contactInfo\":\"Cairo\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Omar Emad"))
                .andExpect(jsonPath("$.contactInfo").value("Giza"));
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void deletePatronTest() throws Exception {
        willDoNothing().given(patronService).deletePatronById(1);
        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isOk());
    }
}
