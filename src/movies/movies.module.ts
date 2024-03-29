import {
  MiddlewareConsumer,
  Module,
  NestModule,
  RequestMethod,
} from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AuthMiddleware } from 'src/middlewares/auth/auth.middleware';
import { MoviesController } from './movies.controller';
import { MoviesService } from './movies.service';
import {
  Actor,
  Director,
  Genre,
  Movie,
  MovieActor,
  MovieDirector,
  MovieGenre,
  Rating,
} from 'src/entities';

@Module({
  imports: [
    TypeOrmModule.forFeature([
      Movie,
      Genre,
      MovieGenre,
      Actor,
      MovieActor,
      Director,
      MovieDirector,
      Rating,
    ]),
  ],
  controllers: [MoviesController],
  providers: [MoviesService],
})
export class MoviesModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(AuthMiddleware)
      .exclude(
        { path: 'api/movies/search', method: RequestMethod.POST },
        { path: 'api/movies/(.*)', method: RequestMethod.GET },
      )
      .forRoutes(MoviesController);
  }
}
