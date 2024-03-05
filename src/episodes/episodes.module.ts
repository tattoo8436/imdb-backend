import {
  MiddlewareConsumer,
  Module,
  NestModule,
  RequestMethod,
} from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Episode, Movie } from 'src/entities';
import { AuthMiddleware } from 'src/middlewares/auth/auth.middleware';
import { EpisodesController } from './episodes.controller';
import { EpisodesService } from './episodes.service';

@Module({
  imports: [TypeOrmModule.forFeature([Episode, Movie])],
  controllers: [EpisodesController],
  providers: [EpisodesService],
})
export class EpisodesModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(AuthMiddleware)
      .exclude({ path: 'api/episodes/(.*)', method: RequestMethod.GET })
      .forRoutes(EpisodesController);
  }
}
