package com.example.demoimdb.service;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.dto.episode.EpisodeRequestDTO;
import com.example.demoimdb.dto.movie.ListMoviesResponseDTO;
import com.example.demoimdb.dto.movie.MovieRequestDTO;
import com.example.demoimdb.dto.movie.SearchMovieRequestDTO;
import com.example.demoimdb.exception.ApiInputException;
import com.example.demoimdb.model.*;
import com.example.demoimdb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private MovieRepository movieRepository;

    public Episode addEpisode(EpisodeRequestDTO episodeRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(episodeRequestDTO.getAccountAdmin().getUsername(),
                episodeRequestDTO.getAccountAdmin().getPassword());
        accountService.checkAdmin(baseAccountDTO);
        try {
            Movie movie = movieRepository.findById(episodeRequestDTO.getMovieId()).get();
            List<Episode> listEpisodes = movieRepository.findById(episodeRequestDTO.getMovieId()).get().getListEpisodes();
            int totalEpisode = 0;
            for (int i = 0; i < listEpisodes.size(); i++) {
                if (listEpisodes.get(i).getSeason() == episodeRequestDTO.getSeason()) {
                    totalEpisode += 1;
                }
            }
            Episode episode = new Episode(movie, null, totalEpisode + 1, episodeRequestDTO.getSeason(),
                    episodeRequestDTO.getName(), episodeRequestDTO.getDescription(),
                    episodeRequestDTO.getImage(), episodeRequestDTO.getReleaseDate(), episodeRequestDTO.getDuration(),
                    0, null, null, null);
            Episode episodeSaved = episodeRepository.save(episode);
            return episodeSaved;
        } catch (Exception e) {
            throw new ApiInputException("Thêm thất bại!");
        }
    }

    public Episode editEpisode(EpisodeRequestDTO episodeRequestDTO){
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(episodeRequestDTO.getAccountAdmin().getUsername(),
                episodeRequestDTO.getAccountAdmin().getPassword());
        accountService.checkAdmin(baseAccountDTO);
        try{
            Episode episode = episodeRepository.findById(episodeRequestDTO.getId()).get();
            episode.setName(episodeRequestDTO.getName());
            episode.setDescription(episodeRequestDTO.getDescription());
            episode.setImage(episodeRequestDTO.getImage());
            episode.setReleaseDate(episodeRequestDTO.getReleaseDate());
            episode.setDuration(episodeRequestDTO.getDuration());
            Episode episodeSaved = episodeRepository.save(episode);
            return episodeSaved;
        }catch (Exception e){
            throw new ApiInputException("Sửa thất bại!");
        }
    }

    public String deleteEpisode(EpisodeRequestDTO episodeRequestDTO){
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(episodeRequestDTO.getAccountAdmin().getUsername(),
                episodeRequestDTO.getAccountAdmin().getPassword());
        accountService.checkAdmin(baseAccountDTO);
        try{
            Episode episode = episodeRepository.findById(episodeRequestDTO.getId()).get();
            episodeRepository.deleteById(episodeRequestDTO.getId());
            return "Xoá thành công!";
        } catch (Exception e){
            throw new ApiInputException("Xoá thất bại!");
        }
    }

}
