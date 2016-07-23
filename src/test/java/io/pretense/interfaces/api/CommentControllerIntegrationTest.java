package io.pretense.interfaces.api;

import helper.ControllerIntegrationTestHelper;
import io.pretense.domain.Comment;
import io.pretense.infrastructure.jpa.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerIntegrationTest extends ControllerIntegrationTestHelper {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Autowired
    private CommentRepository commentRepository;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void 댓글_등록_200_OK() throws Exception {

        //given
        String json = "{\"body\":\"댓글본문\"}";

        //when
        String body = mvc.perform(post("/api/article/9/comment").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
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

    @Test
    public void 댓글_등록시_댓글내용이_없는경우_403_ERROR() throws Exception {

        //given
        String json = "{\"body\":null}";

        //when
        mvc.perform(post("/api/article/1/comment").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 댓글_수정시_200_OK() throws Exception {

        //given
        String json = "{\"id\":1,\"body\":\"수정본문\"}";

        //when
        String body = mvc.perform(post("/api/article/9/comment").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(body, containsString("{\"id\":1,\"body\":\"수정본문\""));
    }

    @Test
    public void 댓글_존재하지않은_삭제시도시_403_ERROR() throws Exception {
        //given
        //when
        mvc.perform(delete("/api/article/9/comment/9999").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 댓글_삭제시_200_OK() throws Exception {
        //given
        //when
        mvc.perform(delete("/api/article/9/comment/2").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void 대댓글_등록시_200_OK() throws Exception {

        //given
        String json = "{\"body\":\"댓글본문\", \"parentId\": 1}";

        //when
        mvc.perform(post("/api/article/9/comment").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());

        //then
        assertThatByRepository();
    }

    private void assertThatByRepository() {
        Comment comment = commentRepository.findOne(1L);
        assertThat(comment.getChildComments().size(), is(1));
    }
}
