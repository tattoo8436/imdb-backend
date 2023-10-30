package com.example.demoimdb.service;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.dto.movie.ListMoviesResponseDTO;
import com.example.demoimdb.dto.movie.MovieRequestDTO;
import com.example.demoimdb.dto.movie.SearchMovieRequestDTO;
import com.example.demoimdb.exception.ApiInputException;
import com.example.demoimdb.model.*;
import com.example.demoimdb.repository.*;
import com.example.demoimdb.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieGenreRepository movieGenreRepository;
    @Autowired
    private MovieActorRepository movieActorRepository;
    @Autowired
    private MovieDirectorRepository movieDirectorRepository;
    @Autowired
    private EpisodeRepository episodeRepository;

    public ListMoviesResponseDTO searchMovie(SearchMovieRequestDTO searchMovieRequestDTO) {
        searchMovieRequestDTO.validateInput();
        Pageable pageable;
        if ("ASC".equals(searchMovieRequestDTO.getOrderBy())) {
            pageable = PageRequest.of(searchMovieRequestDTO.getPageIndex() - 1, searchMovieRequestDTO.getPageSize(), Sort.by(searchMovieRequestDTO.getSortBy()).ascending());
        } else {
            pageable = PageRequest.of(searchMovieRequestDTO.getPageIndex() - 1, searchMovieRequestDTO.getPageSize(), Sort.by(searchMovieRequestDTO.getSortBy()).descending());
        }
        Page<Movie> pageMovie = movieRepository.searchMovie(searchMovieRequestDTO.getGenreId(), searchMovieRequestDTO.getName(),
                searchMovieRequestDTO.getType(), searchMovieRequestDTO.getScore(), searchMovieRequestDTO.getReleaseDate(),
                searchMovieRequestDTO.getLanguage(), pageable);
        List<Movie> listMovies = pageMovie.toList();

        ListMoviesResponseDTO listMoviesResponseDTO = new ListMoviesResponseDTO();
        listMoviesResponseDTO.setTotalRecords(pageMovie.getTotalElements());
        listMoviesResponseDTO.setListMovies(listMovies);
        return listMoviesResponseDTO;
    }

    public Movie getMovieById(MovieRequestDTO movieRequestDTO){
        try{
            return movieRepository.findById(movieRequestDTO.getId()).get();
        } catch (Exception e){
            throw new ApiInputException("ID không đúng!");
        }
    }

    public Movie addMovie(MovieRequestDTO movieRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(movieRequestDTO.getAccountAdmin().getUsername(),
                movieRequestDTO.getAccountAdmin().getPassword());
        accountService.checkAdmin(baseAccountDTO);
        Movie movie = new Movie(null, movieRequestDTO.getName(), movieRequestDTO.getDescription(), movieRequestDTO.getImage(),
                movieRequestDTO.getTrailer(), movieRequestDTO.getReleaseDate(), movieRequestDTO.getDuration(), movieRequestDTO.getType(),
                false, null, 0, null, movieRequestDTO.getLanguage(),
                null, null, null, null, null, null);
        if (movieRequestDTO.getType() == 2) {
            movie.setNumberSeason(1);
        }
        Movie movieSaved = movieRepository.save(movie);

        for (int i = 0; i < movieRequestDTO.getListGenreIds().size(); i++) {
            Genre genre = genreRepository.findById(movieRequestDTO.getListGenreIds().get(i)).get();
            movieGenreRepository.save(new MovieGenre(genre, movieSaved, null));
        }
        for (int i = 0; i < movieRequestDTO.getListActors().size(); i++) {
            Actor actor = actorRepository.findById(movieRequestDTO.getListActors().get(i).getId()).get();
            movieActorRepository.save(new MovieActor(actor, movieSaved, null, movieRequestDTO.getListActors().get(i).getNameInMovie()));
        }
        for (int i = 0; i < movieRequestDTO.getListDirectorIds().size(); i++) {
            Director director = directorRepository.findById(movieRequestDTO.getListDirectorIds().get(i)).get();
            movieDirectorRepository.save(new MovieDirector(director, movieSaved, null));
        }
        return movieSaved;
    }

    public Movie editMovie(MovieRequestDTO movieRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(movieRequestDTO.getAccountAdmin().getUsername(),
                movieRequestDTO.getAccountAdmin().getPassword());
        accountService.checkAdmin(baseAccountDTO);
        try {
            Movie movie = movieRepository.findById(movieRequestDTO.getId()).get();
            movie.setName(movieRequestDTO.getName());
            movie.setDescription(movieRequestDTO.getDescription());
            movie.setImage(movieRequestDTO.getImage());
            movie.setTrailer(movieRequestDTO.getTrailer());
            movie.setReleaseDate(movieRequestDTO.getReleaseDate());
            movie.setDuration(movieRequestDTO.getDuration());
            movie.setLanguage(movieRequestDTO.getLanguage());
            movieRepository.save(movie);

            List<MovieGenre> listMovieGenres = movie.getListMovieGenres();
            for (int i = 0; i < listMovieGenres.size(); i++) {
                movieGenreRepository.deleteById(listMovieGenres.get(i).getId());
            }
            List<MovieActor> listMovieActors = movie.getListMovieActors();
            for (int i = 0; i < listMovieActors.size(); i++) {
                movieActorRepository.deleteById(listMovieActors.get(i).getId());
            }
            List<MovieDirector> listMovieDirectors = movie.getListMovieDirectors();
            for (int i = 0; i < listMovieDirectors.size(); i++) {
                movieDirectorRepository.deleteById(listMovieDirectors.get(i).getId());
            }

            for (int i = 0; i < movieRequestDTO.getListGenreIds().size(); i++) {
                Genre genre = genreRepository.findById(movieRequestDTO.getListGenreIds().get(i)).get();
                movieGenreRepository.save(new MovieGenre(genre, movie, null));
            }
            for (int i = 0; i < movieRequestDTO.getListActors().size(); i++) {
                Actor actor = actorRepository.findById(movieRequestDTO.getListActors().get(i).getId()).get();
                movieActorRepository.save(new MovieActor(actor, movie, null, movieRequestDTO.getListActors().get(i).getNameInMovie()));
            }
            for (int i = 0; i < movieRequestDTO.getListDirectorIds().size(); i++) {
                Director director = directorRepository.findById(movieRequestDTO.getListDirectorIds().get(i)).get();
                movieDirectorRepository.save(new MovieDirector(director, movie, null));
            }
            return movie;
        } catch (Exception e) {
            throw new ApiInputException("Sửa thất bại!");
        }
    }

    public String deleteMovie(MovieRequestDTO movieRequestDTO) {
        try {
            Movie movie = movieRepository.findById(movieRequestDTO.getId()).get();

            List<MovieGenre> listMovieGenres = movie.getListMovieGenres();
            for (int i = 0; i < listMovieGenres.size(); i++) {
                movieGenreRepository.deleteById(listMovieGenres.get(i).getId());
            }
            List<MovieActor> listMovieActors = movie.getListMovieActors();
            for (int i = 0; i < listMovieActors.size(); i++) {
                movieActorRepository.deleteById(listMovieActors.get(i).getId());
            }
            List<MovieDirector> listMovieDirectors = movie.getListMovieDirectors();
            for (int i = 0; i < listMovieDirectors.size(); i++) {
                movieDirectorRepository.deleteById(listMovieDirectors.get(i).getId());
            }
            List<Episode> listEpisodes = movie.getListEpisodes();
            for(int i = 0; i < listEpisodes.size(); i++){
                episodeRepository.deleteById(listEpisodes.get(i).getId());
            }
            movieRepository.deleteById(movieRequestDTO.getId());
            return "Xoá thành công!";
        } catch (Exception e) {
            throw new ApiInputException("Xoá thất bại!");
        }
    }

    public Integer addSeason(MovieRequestDTO movieRequestDTO){
        try {
            Movie movie = movieRepository.findById(movieRequestDTO.getId()).get();
            Integer currentNumberSeason = movie.getNumberSeason();
            movie.setNumberSeason(currentNumberSeason + 1);
            movieRepository.save(movie);
            return currentNumberSeason + 1;
        } catch (Exception e){
            throw new ApiInputException("Thêm mùa thất bại!");
        }
    }
}
