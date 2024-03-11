import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  ParseIntPipe,
  Post,
  Put,
} from '@nestjs/common';
import { MoviesService } from './movies.service';
import { MovieSearch } from './dtos/MovieSearch.dto';
import { BaseMovie } from './dtos/BaseMovie.dto';
import { MovieUpdate } from './dtos/MovieUpdate.dto';

@Controller('api/movies')
export class MoviesController {
  constructor(private movieService: MoviesService) {}

  @Post('/search')
  searchMovies(@Body() search: MovieSearch) {
    return this.movieService.searchMovies(search);
  }

  @Get('/trending')
  getTrendingMovie() {
    return this.movieService.getTrendingMovie();
  }

  @Get('/similar/:id')
  getSimilarMovie(@Param('id', ParseIntPipe) id: number) {
    return this.movieService.getMoviesSimilar(id);
  }

  @Get('/:id')
  getMovieById(@Param('id', ParseIntPipe) id: number) {
    return this.movieService.getMovieById(id);
  }

  @Post('')
  createMovie(@Body() movie: BaseMovie) {
    return this.movieService.createMovie(movie);
  }

  @Put('')
  updateMovie(@Body() movie: MovieUpdate) {
    return this.movieService.updateMovie(movie);
  }

  @Delete('/:id')
  deleteMovie(@Param('id', ParseIntPipe) id: number) {
    return this.movieService.deleteMovie(id);
  }

  @Put('/add-season/:id')
  addSeason(@Param('id', ParseIntPipe) id: number) {
    return this.movieService.addSeason(id);
  }
}
