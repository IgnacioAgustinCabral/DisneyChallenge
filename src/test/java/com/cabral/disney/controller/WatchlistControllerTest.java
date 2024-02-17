package com.cabral.disney.controller;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.models.Movie;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    @WithMockUser(roles = "USER")
    public void userRemoveMovieFromWatchlistShouldReturn200_OKAndMovieTitle() throws Exception {
        when(this.watchlistService.removeMovieFromWatchlist(anyLong(), anyString())).thenReturn("Aladdin");

        mockMvc.perform(delete("/watchlist/{movieId}/remove", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Aladdin was removed from your watchlist."));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void userRemoveNonExistantMovieFromWatchlistShouldReturn404_NOT_FOUND_AND_Message() throws Exception {
        when(this.watchlistService.removeMovieFromWatchlist(anyLong(), anyString())).thenThrow(new MovieNotFoundException("Movie not found in your watchlist"));

        mockMvc.perform(delete("/watchlist/{movieId}/remove", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Movie not found in your watchlist"));
    }
}

