package com.example.demoimdb.dto.episode;

import com.example.demoimdb.dto.AccountAdmin;
import com.example.demoimdb.model.Episode;
import com.example.demoimdb.model.Movie;
import com.example.demoimdb.model.MovieActor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EpisodeRequestDTO extends Episode {
    @NotNull(message = "Tài khoản admin là bắt buộc")
    private AccountAdmin accountAdmin;
    private Long movieId;
}
