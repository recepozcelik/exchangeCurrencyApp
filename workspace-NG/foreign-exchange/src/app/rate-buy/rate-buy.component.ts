import { Component, OnInit } from '@angular/core';
import { RateService } from '../rate.service';
import { AccountService } from '../account.service';
import { TransactionService } from '../transaction.service';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ValidatorFn,
  AbstractControl
} from '@angular/forms';

@Component({
  selector: 'app-rate-buy',
  templateUrl: './rate-buy.component.html',
  styleUrls: ['./rate-buy.component.css']
})
export class RateBuyComponent implements OnInit {
  validatingForm: FormGroup;
  transaction: {
    id;
    userName;
    sourceDebit;
    sourceType;
    targetType;
    targetAmount;
  } = {
    id: null,
    userName: 'demo',
    sourceType: 'USD',
    sourceDebit: 0,
    targetType: 'TRY',
    targetAmount: 0
  };
  rates;
  accounts;
  user;
  value;
  ratio;
  willPay;
  showError = false;

  constructor(
    public rateService: RateService,
    public accountService: AccountService,
    public transactionService: TransactionService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.validatingForm = formBuilder.group(
      {
        min: [null, [Validators.required, Validators.min(1)]],
        required: [null, Validators.required],
        currency: [null, Validators.required],
        account: [null, Validators.required]
      },
      { validator: this.CompareCurrency('currency', 'account') }
    );
  }

  CompareCurrency(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];

      if (matchingControl.errors && matchingControl.errors.mustMatch) {
        return null;
      }

      if (control.value === matchingControl.value) {
        matchingControl.setErrors({ mustMatch: true });
      } else {
        matchingControl.setErrors(null);
        return null;
      }
    };
  }

  ngOnInit() {
    this.rateService.getRatesList().subscribe(rates => {
      this.rates = rates.rates;
    });
    this.accountService.getAccountList('demo').subscribe(account => {
      this.accounts = account;
    });
    this.accountService
      .getAccounDebit(this.transaction.sourceType)
      .subscribe(account => {
        this.transaction.sourceDebit = account[0].debit;
      });
    this.rateService.getRate(this.transaction.targetType).subscribe(rate => {
      this.value = rate.value;
    });
  }

  getQuote() {
    console.log(this.transaction);
    this.rateService
      .getRatePair(this.transaction.sourceType, this.transaction.targetType)
      .subscribe(rates => {
        this.ratio = rates[0].value / rates[1].value;
        console.log(this.transaction.targetAmount);
        this.willPay = this.ratio * this.transaction.targetAmount;
      });
  }

  createTransaction() {
    this.showError = false;
    if (this.transaction.sourceType === this.transaction.targetType) {
      return;
    }
    this.transactionService
      .createTransaction(this.transaction)
      .pipe(catchError(this.handleError))
      .subscribe(
        rate => {
          this.router.navigate(['/account-list', { id: rate }]);
        },
        err => {
          this.showError = true;
        }
      );
  }

  currencyChange() {
    this.rateService.getRate(this.transaction.targetType).subscribe(rate => {
      this.value = rate.value;
    });
  }

  accountChange() {
    this.accountService
      .getAccounDebit(this.transaction.sourceType)
      .subscribe(account => {
        this.transaction.sourceDebit = account[0].debit;
      });
  }

  handleError(error: HttpErrorResponse) {
    return throwError(error.message);
  }
}
