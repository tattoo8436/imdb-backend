package com.example.demoimdb.dto.comment;

import com.example.demoimdb.dto.AccountAdmin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentRequestDTO {
    @NotNull(message = "Tài khoản user là bắt buộc")
    private AccountAdmin accountAdmin;
    @NotNull(message = "Nội dung là bắt buộc")
    private String content;
    private Long movieId;
    private Long episodeId;
}
