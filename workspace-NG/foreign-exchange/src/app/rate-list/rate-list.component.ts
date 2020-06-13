import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';

@Component({
  selector: 'app-rate-list',
  templateUrl: './rate-list.component.html',
  styleUrls: ['./rate-list.component.css']
})
export class RateListComponent implements OnInit {
  rates;
  base;
  date;

  constructor(public dataService: DataService) {}

  ngOnInit() {
    this.dataService.getRatesList().subscribe(rates => {
      this.rates = rates.rates;
      this.base = rates.base;
      this.date = rates.date;
    });
  }
}
