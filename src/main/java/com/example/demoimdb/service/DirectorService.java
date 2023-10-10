package com.example.demoimdb.service;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.dto.director.DirectorRequestDTO;
import com.example.demoimdb.dto.director.DirectorResponseDTO;
import com.example.demoimdb.dto.director.ListDirectorsResponseDTO;
import com.example.demoimdb.dto.director.SearchDirectorRequestDTO;
import com.example.demoimdb.exception.ApiInputException;
import com.example.demoimdb.model.Director;
import com.example.demoimdb.repository.DirectorRepository;
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
public class DirectorService {
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private AccountService accountService;

    public List<DirectorResponseDTO> getAllDirectors() {
        return ConvertUtils.convertList(directorRepository.findAll(), DirectorResponseDTO.class);
    }

    public DirectorResponseDTO addDirector(DirectorRequestDTO directorRequestDTO) throws IOException {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(directorRequestDTO.getUsername(), directorRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        Director director = new Director(null, directorRequestDTO.getName(), directorRequestDTO.getDescription(), directorRequestDTO.getImage(),
                directorRequestDTO.getDob(), null);
        return ConvertUtils.convert(directorRepository.save(director), DirectorResponseDTO.class);
    }

    public DirectorResponseDTO editDirector(DirectorRequestDTO directorRequestDTO) throws IOException {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(directorRequestDTO.getUsername(), directorRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        try {
            Director director = directorRepository.findById(directorRequestDTO.getId()).get();
            director.setName(directorRequestDTO.getName());
            director.setDescription(directorRequestDTO.getDescription());
            director.setImage(directorRequestDTO.getImage());
            director.setDob(directorRequestDTO.getDob());
            return ConvertUtils.convert(directorRepository.save(director), DirectorResponseDTO.class);
        } catch (Exception e) {
            throw new ApiInputException("ID không hợp lệ!");
        }
    }

    public String deleteDirector(DirectorRequestDTO directorRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(directorRequestDTO.getUsername(), directorRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        try {
            directorRepository.deleteById(directorRequestDTO.getId());
            return "Xoá thành công!";
        } catch (Exception e) {
            throw new ApiInputException("Xoá thất bại!");
        }
    }

    public ListDirectorsResponseDTO searchDirector(SearchDirectorRequestDTO searchDirectorRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(searchDirectorRequestDTO.getUsername(), searchDirectorRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        searchDirectorRequestDTO.validateInput();
        Pageable pageable;
        if ("ASC".equals(searchDirectorRequestDTO.getOrderBy())) {
            pageable = PageRequest.of(searchDirectorRequestDTO.getPageIndex() - 1, searchDirectorRequestDTO.getPageSize(), Sort.by(searchDirectorRequestDTO.getSortBy()).ascending());
        } else {
            pageable = PageRequest.of(searchDirectorRequestDTO.getPageIndex() - 1, searchDirectorRequestDTO.getPageSize(), Sort.by(searchDirectorRequestDTO.getSortBy()).descending());
        }
        Page<Director> pageDirectors = directorRepository.searchDirector(searchDirectorRequestDTO.getName(), pageable);
        List<Director> listDirectors = pageDirectors.toList();
        List<DirectorResponseDTO> listDirectorsDto = ConvertUtils.convertList(listDirectors, DirectorResponseDTO.class);
        ListDirectorsResponseDTO listDirectorsResponseDTO = new ListDirectorsResponseDTO();
        listDirectorsResponseDTO.setListDirectors(listDirectorsDto);
        listDirectorsResponseDTO.setPageIndex(searchDirectorRequestDTO.getPageIndex());
        listDirectorsResponseDTO.setPageSize(searchDirectorRequestDTO.getPageSize());
        listDirectorsResponseDTO.setTotalRecords(pageDirectors.getTotalElements());
        return listDirectorsResponseDTO;
    }

}
