package ru.graduation.voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.DishTestData.MENU;
import static ru.graduation.voting.DishTestData.MENU_MATCHER;
import static ru.graduation.voting.TestUtil.userHttpBasic;
import static ru.graduation.voting.UserTestData.USER;

class MenuRestControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = MenuRestController.REST_URL + '/';

    @Test
    void getAllToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(List.of()));
    }

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("date", "2020-08-20")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(MENU));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }
}