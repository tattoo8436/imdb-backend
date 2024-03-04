import { BadRequestException, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Director } from 'src/entities/Director';
import { initSearch } from 'src/utils';
import { Repository } from 'typeorm';
import { DirectorSearch } from './dtos/DirectorSearch';
import { BaseDirector } from './dtos/BaseDirector';
import { DirectorUpdate } from './dtos/DirectorUpdate';

@Injectable()
export class DirectorsService {
  constructor(
    @InjectRepository(Director)
    private directorRepository: Repository<Director>,
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
