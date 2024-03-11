import { MiddlewareConsumer, Module, NestModule } from '@nestjs/common';
import { CommentsController } from './comments.controller';
import { CommentsService } from './comments.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AuthMiddleware } from 'src/middlewares/auth/auth.middleware';
import { Account, Comment, Episode, Movie } from 'src/entities';

@Module({
  imports: [TypeOrmModule.forFeature([Comment, Account, Movie, Episode])],
  controllers: [CommentsController],
  providers: [CommentsService],
})
export class CommentsModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer.apply(AuthMiddleware).forRoutes(CommentsController);
  }
}
