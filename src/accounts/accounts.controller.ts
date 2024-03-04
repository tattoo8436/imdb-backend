import { Body, Controller, Post } from '@nestjs/common';
import { ApiOperation, ApiTags } from '@nestjs/swagger';
import { AccountsService } from './accounts.service';
import { AccountRegister } from './dtos/AccountRegister.dto';
import { BaseAccount } from './dtos/BaseAccount.dto';

@ApiTags('Accounts')
@Controller('api/accounts')
export class AccountsController {
  constructor(private accountService: AccountsService) {}

  @ApiOperation({ summary: 'Register' })
  @Post('/register')
  register(@Body() account: AccountRegister) {
    return this.accountService.register(account);
  }

  @ApiOperation({ summary: 'User login' })
  @Post('/login')
  loginUser(@Body() account: BaseAccount) {
    return this.accountService.loginUser(account);
  }

  @ApiOperation({ summary: 'Admin login' })
  @Post('/login-admin')
  loginAdmin(@Body() account: BaseAccount) {
    return this.accountService.loginAdmin(account);
  }
}
