import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RateListComponent } from './rate-list/rate-list.component';
import { HomeComponent } from './home/home.component';
import { RateBuyComponent } from './rate-buy/rate-buy.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: 'home', component: HomeComponent },
  { path: 'rate-list', component: RateListComponent },
  { path: 'rate-buy', component: RateBuyComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
