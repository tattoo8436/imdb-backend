import {
  MiddlewareConsumer,
  Module,
  NestModule,
  RequestMethod,
} from '@nestjs/common';
import { FilesController } from './files.controller';
import { FilesService } from './files.service';
import { AuthMiddleware } from 'src/middlewares/auth/auth.middleware';

@Module({
  controllers: [FilesController],
  providers: [FilesService],
})
export class FilesModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(AuthMiddleware)
      .exclude({ path: 'api/files/image/(.*)', method: RequestMethod.GET })
      .forRoutes(FilesController);
  }
}
