package com.example.demoimdb.service;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.dto.actor.ActorRequestDTO;
import com.example.demoimdb.dto.actor.ListActorsResponseDTO;
import com.example.demoimdb.dto.actor.SearchActorRequestDTO;
import com.example.demoimdb.dto.comment.CommentRequestDTO;
import com.example.demoimdb.exception.ApiInputException;
import com.example.demoimdb.model.*;
import com.example.demoimdb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Comment addComment(CommentRequestDTO commentRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(commentRequestDTO.getAccountAdmin().getUsername(), commentRequestDTO.getAccountAdmin().getPassword());
        accountService.checkUser(baseAccountDTO);
        Movie movie = null;
        Episode episode = null;
        try{
            if(commentRequestDTO.getMovieId() != null){
                movie = movieRepository.findById(commentRequestDTO.getMovieId()).get();
            }
            if(commentRequestDTO.getEpisodeId() != null){
                episode = episodeRepository.findById(commentRequestDTO.getEpisodeId()).get();
            }
            Account account = accountRepository.findByUsername(commentRequestDTO.getAccountAdmin().getUsername());
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateStr = dateFormat.format(date);
            return commentRepository.save(new Comment(movie, episode, account, null, commentRequestDTO.getContent(), dateStr));
        } catch (Exception e){
            throw new ApiInputException("Bình luận thất bại");
        }
    }

}
