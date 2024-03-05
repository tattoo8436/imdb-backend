import { BadRequestException, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Actor } from 'src/entities/Actor';
import { initSearch } from 'src/utils';
import { Repository } from 'typeorm';
import { ActorSearch } from './dtos/ActorSearch';
import { ActorUpdate } from './dtos/ActorUpdate';
import { BaseActor } from './dtos/BaseActor';

@Injectable()
export class ActorsService {
  constructor(
    @InjectRepository(Actor) private actorRepository: Repository<Actor>,
  ) {}

  async searchActors(searchRaw: ActorSearch) {
    const search = initSearch(searchRaw);
    const queryBuilder = this.actorRepository.createQueryBuilder('actor');
    queryBuilder
      .where('actor.name ILIKE :name', { name: `%${search.name ?? ''}%` })
      .skip((search.page - 1) * search.limit)
      .take(search.limit)
      .orderBy(search.sortBy, search.orderBy);
    const [data, totals] = await queryBuilder.getManyAndCount();
    return { data, totals };
  }

  createActor(actor: BaseActor) {
    return this.actorRepository.save(actor);
  }

  updateActor(actor: ActorUpdate) {
    return this.actorRepository.save(actor);
  }

  async deleteActor(id: number) {
    try {
      const res = await this.actorRepository.delete({ id });
      return res;
    } catch (error) {
      throw new BadRequestException();
    }
  }
}
