import { MiddlewareConsumer, Module, NestModule } from '@nestjs/common';
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
    consumer.apply(AuthMiddleware).forRoutes(RatingsController);
  }
}
