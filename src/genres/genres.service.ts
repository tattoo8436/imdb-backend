import { BadRequestException, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Genre } from 'src/entities/Genre';
import { initSearch } from 'src/utils';
import { Repository } from 'typeorm';
import { BaseGenre } from './dtos/BaseGenre.dto';
import { GenreSearch } from './dtos/GenreSearch.dto';
import { GenreUpdate } from './dtos/GenreUpdate.dto';

@Injectable()
export class GenresService {
  constructor(
    @InjectRepository(Genre) private genreRepository: Repository<Genre>,
  ) {}

  async searchGenres(searchRaw: GenreSearch) {
    const search = initSearch(searchRaw);
    const queryBuilder = this.genreRepository.createQueryBuilder('genre');
    queryBuilder
      .where('genre.name ILIKE :name', { name: `%${search.name ?? ''}%` })
      .skip((search.page - 1) * search.limit)
      .take(search.limit)
      .orderBy(search.sortBy, search.orderBy);
    const [data, totals] = await queryBuilder.getManyAndCount();
    return { data, totals };
  }

  async createGenre(genre: BaseGenre) {
    const foundGenre = await this.genreRepository.findOneBy({
      name: genre.name,
    });
    if (foundGenre) {
      throw new BadRequestException('Thể loại đã tồn tại');
    }
    return this.genreRepository.save(genre);
  }

  async updateGenre(genre: GenreUpdate) {
    const existedGenre = await this.genreRepository.findOneBy({
      id: genre.id,
    });
    if (!existedGenre) {
      throw new BadRequestException('Không tìm thấy thể loại');
    }
    const foundGenre = await this.genreRepository.findOneBy({
      name: genre.name,
    });
    if (
      foundGenre &&
      foundGenre.id !== genre.id &&
      foundGenre.name === genre.name
    ) {
      throw new BadRequestException('Thể loại đã tồn tại');
    }
    return this.genreRepository.save(genre);
  }

  async deleteGenre(id: number) {
    try {
      const res = await this.genreRepository.delete({ id });
      return res;
    } catch (error) {
      throw new BadRequestException();
    }
  }
}
