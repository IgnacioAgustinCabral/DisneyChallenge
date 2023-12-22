package com.cabral.disney.controller;

import com.cabral.disney.security.MyUserDetailsService;
import com.cabral.disney.security.SecurityConfig;
import com.cabral.disney.service.WatchlistService;
import com.cabral.disney.service.impl.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WatchlistController.class)
@Import(SecurityConfig.class)
public class WatchlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WatchlistService watchlistService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser(roles = "USER")
    public void userWithROLE_USERAddsMovieToWatchlistEndpointShouldReturn200_OK() throws Exception {

        ResultActions result = mockMvc.perform(post("/watchlist/{movieId}/add-to-watchlist", 1L))
                .andExpect(status().isOk());
    }
}

