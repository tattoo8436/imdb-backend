import {
  MiddlewareConsumer,
  Module,
  NestModule,
  RequestMethod,
} from '@nestjs/common';
import { RatingsController } from './ratings.controller';
import { RatingsService } from './ratings.service';
import { Account, Episode, Movie, Rating } from 'src/entities';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AuthMiddleware } from 'src/middlewares/auth/auth.middleware';

@Module({
  imports: [TypeOrmModule.forFeature([Rating, Movie, Episode, Account])],
  controllers: [RatingsController],
  providers: [RatingsService],
})
export class RatingsModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(AuthMiddleware)
      .exclude(
        {
          path: `api/ratings/movie/get-by-account/(.*)`,
          method: RequestMethod.GET,
        },
        {
          path: `api/ratings/episode/get-by-account/(.*)`,
          method: RequestMethod.GET,
        },
      )
      .forRoutes(RatingsController);
  }
}
