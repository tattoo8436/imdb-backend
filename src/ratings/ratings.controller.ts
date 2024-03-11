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

  @Post('/movie/get-by-account')
  getRatingMovieByAccount(@Body() payload: any) {
    return this.ratingService.getRatingMovieByAccount(payload);
  }

  @Post('/episode/get-by-account')
  getRatingEpisodeByAccount(@Body() payload: any) {
    return this.ratingService.getRatingEpisodeByAccount(payload);
  }

  @Get('/movie/statistic/:movieId')
  getStatisticMovie(@Param('movieId', ParseIntPipe) movieId: number) {
    return this.ratingService.getStatisticMovie(movieId);
  }
}
