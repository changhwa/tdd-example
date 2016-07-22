package io.pretense.interfaces.api;

import helper.ControllerIntegrationTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerIntegrationTest extends ControllerIntegrationTestHelper {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void 댓글_등록_201_OK() throws Exception {

        //given
        String json = "{\"body\":\"댓글본문\"}";

        //when
        String body = mvc.perform(post("/api/article/9/comment").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        assertThat(body, containsString("\"body\":\"댓글본문\""));
    }

    @Test
    public void 댓글_등록시_게시글이_없는_경우_게시글_예외_발생_403_ERROR() throws Exception {

        //given
        String json = "{\"body\":\"댓글본문\"}";

        //when
        mvc.perform(post("/api/article/99999/comment").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isBadRequest());
    }
}
