package com.example.demoimdb.dto.rating;

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
public class RatingRequestDTO {
    @NotNull(message = "Tài khoản user là bắt buộc")
    private AccountAdmin accountAdmin;
    @NotNull(message = "Điểm là bắt buộc")
    private Integer score;
    private Long movieId;
    private Long episodeId;
}
