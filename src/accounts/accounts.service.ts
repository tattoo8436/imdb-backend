import { BadRequestException, Injectable } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { InjectRepository } from '@nestjs/typeorm';
import { Account } from 'src/entities/Account';
import { Repository } from 'typeorm';
import { AccountRegister } from './dtos/AccountRegister.dto';
import { BaseAccount } from './dtos/BaseAccount.dto';
import * as bcrypt from 'bcrypt';

@Injectable()
export class AccountsService {
  constructor(
    @InjectRepository(Account) private accountRepository: Repository<Account>,
    private jwtService: JwtService,
  ) {}

  async register(account: AccountRegister) {
    const foundAccount = await this.accountRepository.findOneBy({
      username: account.username,
    });
    if (foundAccount) {
      throw new BadRequestException('Tên đăng nhập đã tồn tại');
    }
    const savedAccount = await this.accountRepository.save({
      ...account,
      role: 2,
      password: bcrypt.hashSync(account.password, 10),
    });
    const { password, ...result } = savedAccount;
    return result;
  }

  async loginUser(account: BaseAccount) {
    const foundAccount = await this.accountRepository.findOneBy({
      username: account.username,
    });
    if (!foundAccount) {
      throw new BadRequestException('Sai tài khoản');
    }
    const ok = bcrypt.compareSync(account.password, foundAccount.password);
    if (!ok) {
      throw new BadRequestException('Sai tài khoản');
    }
    const { password, ...result } = foundAccount;
    return {
      ...result,
      accessToken: this.jwtService.sign({
        username: account.username,
        role: foundAccount.role,
      }),
    };
  }

  async loginAdmin(account: BaseAccount) {
    const foundAccount = await this.accountRepository.findOneBy({
      username: account.username,
      role: 1,
    });
    if (!foundAccount) {
      throw new BadRequestException('Sai tài khoản');
    }
    const ok = bcrypt.compareSync(account.password, foundAccount.password);
    if (!ok) {
      throw new BadRequestException('Sai tài khoản');
    }
    const { password, ...result } = foundAccount;
    return {
      ...result,
      accessToken: this.jwtService.sign({
        username: account.username,
        role: foundAccount.role,
      }),
    };
  }
}
