import {
  Body,
  Controller,
  Get,
  Param,
  ParseIntPipe,
  Post,
  Req,
} from '@nestjs/common';
import { RatingsService } from './ratings.service';
import { BaseRating } from './dtos/BaseRating';

@Controller('api/ratings')
export class RatingsController {
  constructor(private ratingService: RatingsService) {}

  @Post('/movie')
  createRatingMovie(@Body() rating: BaseRating, @Req() req: any) {
    return this.ratingService.createRatingMovie(rating, req.account?.id);
  }

  @Post('/episode')
  createRatingEpisode(@Body() rating: BaseRating, @Req() req: any) {
    return this.ratingService.createRatingEpisode(rating, req.account?.id);
  }

  @Get('/movie/get-by-account/:id')
  getRatingMovieByAccount(
    @Param('id', ParseIntPipe) id: number,
    @Req() req: any,
  ) {
    return this.ratingService.getRatingMovieByAccount(id, req.account?.id);
  }

  @Get('/episode/get-by-account/:id')
  getRatingEpisodeByAccount(
    @Param('id', ParseIntPipe) id: number,
    @Req() req: any,
  ) {
    return this.ratingService.getRatingEpisodeByAccount(id, req.account?.id);
  }

  @Get('/movie/statistic/:movieId')
  getStatisticMovie(@Param('movieId', ParseIntPipe) movieId: number) {
    return this.ratingService.getStatisticMovie(movieId);
  }
}
