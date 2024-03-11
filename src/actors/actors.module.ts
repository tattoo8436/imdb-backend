import {
  Module,
  NestModule,
  MiddlewareConsumer,
  RequestMethod,
} from '@nestjs/common';
import { ActorsController } from './actors.controller';
import { ActorsService } from './actors.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Actor } from 'src/entities/Actor';
import { AuthMiddleware } from 'src/middlewares/auth/auth.middleware';
import { Movie } from 'src/entities';

@Module({
  imports: [TypeOrmModule.forFeature([Actor, Movie])],
  controllers: [ActorsController],
  providers: [ActorsService],
})
export class ActorsModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(AuthMiddleware)
      .exclude(
        { path: 'api/actors/search', method: RequestMethod.POST },
        { path: 'api/actors/(.*)', method: RequestMethod.GET },
      )
      .forRoutes(ActorsController);
  }
}
