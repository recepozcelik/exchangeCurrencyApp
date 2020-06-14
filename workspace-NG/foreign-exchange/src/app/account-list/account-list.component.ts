import { Component, OnInit } from '@angular/core';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {
  constructor(public accountService: AccountService) {}

  accounts;

  ngOnInit() {
    this.accountService.getAccountList('demo').subscribe(account => {
      this.accounts = account;
    });
  }
}
