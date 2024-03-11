import { BadRequestException, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Director } from 'src/entities/Director';
import { initSearch } from 'src/utils';
import { Repository } from 'typeorm';
import { DirectorSearch } from './dtos/DirectorSearch';
import { BaseDirector } from './dtos/BaseDirector';
import { DirectorUpdate } from './dtos/DirectorUpdate';
import { Movie } from 'src/entities';

@Injectable()
export class DirectorsService {
  constructor(
    @InjectRepository(Director)
    private directorRepository: Repository<Director>,
    @InjectRepository(Movie)
    private movieRepository: Repository<Movie>,
  ) {}

  async searchDirectors(searchRaw: DirectorSearch) {
    const search = initSearch(searchRaw);
    const queryBuilder = this.directorRepository.createQueryBuilder('director');
    queryBuilder
      .where('director.name ILIKE :name', { name: `%${search.name ?? ''}%` })
      .skip((search.page - 1) * search.limit)
      .take(search.limit)
      .orderBy(search.sortBy, search.orderBy);
    const [data, totals] = await queryBuilder.getManyAndCount();
    return { data, totals };
  }

  getDirectorById(id: number) {
    return this.directorRepository.findOneBy({ id });
  }

  async getMoviesByDirector(directorId: number) {
    const queryBuilder = this.movieRepository.createQueryBuilder('movie');
    queryBuilder
      .distinct()
      .leftJoinAndSelect('movie.movieDirectors', 'movieDirectors')
      .leftJoinAndSelect('movieDirectors.director', 'director')
      .where('director.id = :directorId', {
        directorId,
      });
    const movies = await queryBuilder.getMany();
    return movies;
  }

  createDirector(director: BaseDirector) {
    return this.directorRepository.save(director);
  }

  updateDirector(director: DirectorUpdate) {
    return this.directorRepository.save(director);
  }

  async deleteDirector(id: number) {
    try {
      const res = await this.directorRepository.delete({ id });
      return res;
    } catch (error) {
      throw new BadRequestException();
    }
  }
}
