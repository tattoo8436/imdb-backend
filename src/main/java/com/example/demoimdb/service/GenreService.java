package com.example.demoimdb.service;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.dto.genre.GenreRequestDTO;
import com.example.demoimdb.dto.genre.GenreResponseDTO;
import com.example.demoimdb.dto.genre.ListGenresResponseDTO;
import com.example.demoimdb.dto.genre.SearchGenreRequestDTO;
import com.example.demoimdb.exception.ApiInputException;
import com.example.demoimdb.model.Genre;
import com.example.demoimdb.repository.GenreRepository;
import com.example.demoimdb.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GenreService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private GenreRepository genreRepository;
    public GenreResponseDTO addGenre(GenreRequestDTO genreRequestDTO) throws IOException {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(genreRequestDTO.getUsername(), genreRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        if(genreRepository.existsByName(genreRequestDTO.getName())){
            throw new ApiInputException("Thể loại đã tồn tại!");
        }
        Genre genre = new Genre();
        genre.setName(genreRequestDTO.getName());
        return ConvertUtils.convert(genreRepository.save(genre), GenreResponseDTO.class);
    }

    public GenreResponseDTO editGenre(GenreRequestDTO genreRequestDTO) throws IOException {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(genreRequestDTO.getUsername(), genreRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        if(genreRepository.existsByName(genreRequestDTO.getName()) && genreRepository.findByName(genreRequestDTO.getName()).getId() != genreRequestDTO.getId()){
            throw new ApiInputException("Thể loại đã tồn tại!");
        }
        try {
            Genre genre = genreRepository.findById(genreRequestDTO.getId()).get();
            genre.setName(genreRequestDTO.getName());
            return ConvertUtils.convert(genreRepository.save(genre), GenreResponseDTO.class);
        } catch (Exception e) {
            throw new ApiInputException("ID không hợp lệ!");
        }
    }

    public String deleteGenre(GenreRequestDTO genreRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(genreRequestDTO.getUsername(), genreRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        try {
            genreRepository.deleteById(genreRequestDTO.getId());
            return "Xoá thành công!";
        } catch (Exception e) {
            throw new ApiInputException("Xoá thất bại!");
        }
    }

    public ListGenresResponseDTO searchGenre(SearchGenreRequestDTO searchGenreRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(searchGenreRequestDTO.getUsername(), searchGenreRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        searchGenreRequestDTO.validateInput();
        Pageable pageable;
        if ("ASC".equals(searchGenreRequestDTO.getOrderBy())) {
            pageable = PageRequest.of(searchGenreRequestDTO.getPageIndex() - 1, searchGenreRequestDTO.getPageSize(), Sort.by(searchGenreRequestDTO.getSortBy()).ascending());
        } else {
            pageable = PageRequest.of(searchGenreRequestDTO.getPageIndex() - 1, searchGenreRequestDTO.getPageSize(), Sort.by(searchGenreRequestDTO.getSortBy()).descending());
        }
        Page<Genre> pageGenres = genreRepository.searchGenre(searchGenreRequestDTO.getName(), pageable);
        List<Genre> listGenres = pageGenres.toList();
        List<GenreResponseDTO> listGenresDto = ConvertUtils.convertList(listGenres, GenreResponseDTO.class);
        ListGenresResponseDTO listGenresResponseDTO = new ListGenresResponseDTO();
        listGenresResponseDTO.setListGenres(listGenresDto);
        listGenresResponseDTO.setPageIndex(searchGenreRequestDTO.getPageIndex());
        listGenresResponseDTO.setPageSize(searchGenreRequestDTO.getPageSize());
        listGenresResponseDTO.setTotalRecords(pageGenres.getTotalElements());
        return listGenresResponseDTO;
    }
}
