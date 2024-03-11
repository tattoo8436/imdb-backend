import {
  MiddlewareConsumer,
  Module,
  NestModule,
  RequestMethod,
} from '@nestjs/common';
import { DirectorsController } from './directors.controller';
import { DirectorsService } from './directors.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Director } from 'src/entities/Director';
import { AuthMiddleware } from 'src/middlewares/auth/auth.middleware';
import { Movie } from 'src/entities';

@Module({
  imports: [TypeOrmModule.forFeature([Director, Movie])],
  controllers: [DirectorsController],
  providers: [DirectorsService],
})
export class DirectorsModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(AuthMiddleware)
      .exclude(
        { path: 'api/directors/search', method: RequestMethod.POST },
        { path: 'api/directors/(.*)', method: RequestMethod.GET },
      )
      .forRoutes(DirectorsController);
  }
}
