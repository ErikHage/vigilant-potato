package com.tfr.vigilant.controller;

import com.tfr.vigilant.VigilantPotatoApplication;
import com.tfr.vigilant.consumer.MessageConsumer;
import com.tfr.vigilant.model.message.Message;
import com.tfr.vigilant.queue.MessageQueue;
import com.tfr.vigilant.utils.Constants;
import com.tfr.vigilant.utils.UuidUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest(classes = VigilantPotatoApplication.class)
public class MessageControllerTest {

    @MockBean
    @Qualifier("MessageQueue")
    private MessageQueue messageQueue;
    @MockBean
    @Qualifier("MessageConsumer")
    private MessageConsumer messageConsumer;
    @MockBean
    @Qualifier("UuidUtils")
    private UuidUtils uuidUtils;

    @Autowired
    private MessageController controller;

    private final String testId = "some-test-id";

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(controller).build();

        when(uuidUtils.getUuid()).thenReturn(testId);
    }

    @Test
    public void testAcceptMessage_GivenValidMessage_Expect202() throws Exception {
        String requestBody = "{\"type\":\"TEST_HP\",\"content\":\"Some content here\"}";

        doNothing().when(messageQueue).add(any(Message.class));

        mockMvc.perform(post("/messages/enqueue")
                .contentType(Constants.APPLICATION_JSON)
                .header("vigilant-service", "service1")
                .header("vigilant-key", "replaceme")
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(Constants.APPLICATION_JSON))
                .andExpect(jsonPath("messageId", is(testId)))
                .andExpect(jsonPath("response", is("message received and queued")));

        verify(messageQueue, times(1)).add(any(Message.class));
        verifyNoMoreInteractions(messageQueue);
    }

    @Test
    public void testAcceptMessage_GivenInvalidType_Expect400() throws Exception {
        String requestBody = "{\"type\":\"INVALID_TYPE\",\"content\":\"Some content here\"}";

        doNothing().when(messageQueue).add(any(Message.class));

        mockMvc.perform(post("/messages/enqueue")
                .contentType(Constants.APPLICATION_JSON)
                .header("vigilant-service", "service1")
                .header("vigilant-key", "replaceme")
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(Constants.APPLICATION_JSON))
                .andExpect(jsonPath("messageId", is("NA")))
                .andExpect(jsonPath("response", is("Invalid message type provided")));

        verifyNoInteractions(messageQueue);
    }

    @Test
    public void testAcceptMessage_GivenInternalServerError_Expect500() throws Exception {
        String requestBody = "{\"type\":\"TEST_HP\",\"content\":\"Some content here\"}";
        RuntimeException expectedException = new RuntimeException("test message");

        doThrow(expectedException).when(messageQueue).add(any(Message.class));

        mockMvc.perform(post("/messages/enqueue")
                .contentType(Constants.APPLICATION_JSON)
                .header("vigilant-service", "service1")
                .header("vigilant-key", "replaceme")
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(Constants.APPLICATION_JSON))
                .andExpect(jsonPath("messageId", is("NA")))
                .andExpect(jsonPath("response", is("Unexpected error: test message")));

        verify(messageQueue, times(1)).add(any(Message.class));
        verifyNoMoreInteractions(messageQueue);
    }

    @Test
    public void testAcceptMessage_GivenNoAuthKeyHeader_Expect401() throws Exception {
        String requestBody = "{\"type\":\"TEST_HP\",\"content\":\"Some content here\"}";
        RuntimeException expectedException = new RuntimeException("test message");

        doThrow(expectedException).when(messageQueue).add(any(Message.class));

        mockMvc.perform(post("/messages/enqueue")
                        .contentType(Constants.APPLICATION_JSON)
                        .header("vigilant-service", "service1")
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(Constants.APPLICATION_JSON))
                .andExpect(jsonPath("messageId", is("NA")))
                .andExpect(jsonPath("response", is("Request Unauthorized")));

        verifyNoMoreInteractions(messageQueue);
    }

    @Test
    public void testAcceptMessage_GivenNoAuthServiceHeader_Expect401() throws Exception {
        String requestBody = "{\"type\":\"TEST_HP\",\"content\":\"Some content here\"}";
        RuntimeException expectedException = new RuntimeException("test message");

        doThrow(expectedException).when(messageQueue).add(any(Message.class));

        mockMvc.perform(post("/messages/enqueue")
                        .contentType(Constants.APPLICATION_JSON)
                        .header("vigilant-key", "replaceme")
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(Constants.APPLICATION_JSON))
                .andExpect(jsonPath("messageId", is("NA")))
                .andExpect(jsonPath("response", is("Request Unauthorized")));

        verifyNoMoreInteractions(messageQueue);
    }
}
