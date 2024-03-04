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

@Module({
  imports: [TypeOrmModule.forFeature([Actor])],
  controllers: [ActorsController],
  providers: [ActorsService],
})
export class ActorsModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(AuthMiddleware)
      .exclude({ path: 'api/actors/search', method: RequestMethod.POST })
      .forRoutes(ActorsController);
  }
}
