import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RateListComponent } from './rate-list/rate-list.component';
import { AccountListComponent } from './account-list/account-list.component';
import { HomeComponent } from './home/home.component';
import { RateBuyComponent } from './rate-buy/rate-buy.component';
import { TransactionListComponent } from './transaction-list/transaction-list.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: 'home', component: HomeComponent },
  { path: 'rate-list', component: RateListComponent },
  { path: 'rate-buy', component: RateBuyComponent },
  { path: 'account-list', component: AccountListComponent },
  { path: 'transaction-list', component: TransactionListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
