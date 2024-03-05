import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AccountsModule } from './accounts/accounts.module';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { GenresModule } from './genres/genres.module';
import { ActorsModule } from './actors/actors.module';
import { FilesModule } from './files/files.module';
import { ConfigModule } from '@nestjs/config';
import { DirectorsModule } from './directors/directors.module';
import { MoviesModule } from './movies/movies.module';
import {
  Account,
  Actor,
  Comment,
  Director,
  Episode,
  Genre,
  Movie,
  MovieActor,
  MovieDirector,
  MovieGenre,
  Rating,
} from './entities';
import { EpisodesModule } from './episodes/episodes.module';
import { RatingsModule } from './ratings/ratings.module';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    TypeOrmModule.forRoot({
      type: 'postgres',
      host: 'ep-curly-unit-a4wdd3c5-pooler.us-east-1.aws.neon.tech',
      port: 5432,
      username: 'default',
      password: '1vtLJCciguj3',
      database: 'verceldb',
      entities: [
        Account,
        Actor,
        Comment,
        Director,
        Episode,
        Genre,
        Movie,
        MovieActor,
        MovieDirector,
        MovieGenre,
        Rating,
      ],
      synchronize: true,
      ssl: true,
    }),
    AccountsModule,
    GenresModule,
    ActorsModule,
    FilesModule,
    DirectorsModule,
    MoviesModule,
    EpisodesModule,
    RatingsModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
