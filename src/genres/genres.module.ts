import { TypeOrmModule } from '@nestjs/typeorm';
import {
  Module,
  NestModule,
  MiddlewareConsumer,
  RequestMethod,
} from '@nestjs/common';
import { Genre } from 'src/entities/Genre';
import { GenresController } from './genres.controller';
import { GenresService } from './genres.service';
import { AuthMiddleware } from 'src/middlewares/auth/auth.middleware';
import { HttpModule } from '@nestjs/axios';

@Module({
  imports: [HttpModule, TypeOrmModule.forFeature([Genre])],
  controllers: [GenresController],
  providers: [GenresService],
})
export class GenresModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(AuthMiddleware)
      .exclude({ path: 'api/genres/search', method: RequestMethod.POST })
      .forRoutes(GenresController);
  }
}
