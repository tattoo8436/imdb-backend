package com.example.demoimdb.service;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.dto.file.ImageRequestDTO;
import com.example.demoimdb.exception.ApiInputException;
import com.example.demoimdb.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    private AccountService accountService;
    public String uploadImage(ImageRequestDTO imageRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(imageRequestDTO.getUsername(), imageRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        try{
            return FileUtils.saveImage(imageRequestDTO.getImage());
        } catch (Exception e){
            throw new ApiInputException("Lưu file thất bại!");
        }
    }
}
