import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-rate-buy',
  templateUrl: './rate-buy.component.html',
  styleUrls: ['./rate-buy.component.css']
})
export class RateBuyComponent implements OnInit {
  transaction: { id; sourceType; targetType; targetAmount } = {
    id: null,
    sourceType: 'USD',
    targetType: 'TRY',
    targetAmount: 0
  };
  rates;
  debit;
  accounts;
  user;
  value;
  ratio;
  willPay;

  constructor(
    public dataService: DataService,
    public accountService: AccountService
  ) {}

  ngOnInit() {
    this.dataService.getRatesList().subscribe(rates => {
      this.rates = rates.rates;
    });
    this.accountService.getAccountList('berkay').subscribe(account => {
      this.accounts = account;
    });
    this.accountService
      .getAccounDebit(this.transaction.sourceType)
      .subscribe(account => {
        this.debit = account.debit;
      });
    this.dataService.getRate(this.transaction.targetType).subscribe(rate => {
      this.value = rate.value;
    });
  }

  createTransaction() {
    console.log(this.transaction);
    this.dataService
      .getRatePair(this.transaction.sourceType, this.transaction.targetType)
      .subscribe(rates => {
        this.ratio = rates[0].value / rates[1].value;
        console.log(this.transaction.targetAmount);
        this.willPay = this.ratio * this.transaction.targetAmount;
      });
  }

  currencyChange() {
    this.dataService.getRate(this.transaction.targetType).subscribe(rate => {
      this.value = rate.value;
    });
  }

  accountChange() {
    this.accountService
      .getAccounDebit(this.transaction.sourceType)
      .subscribe(account => {
        this.debit = account.debit;
      });
  }
}
