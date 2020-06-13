import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateBuyComponent } from './rate-buy.component';

describe('RateBuyComponent', () => {
  let component: RateBuyComponent;
  let fixture: ComponentFixture<RateBuyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateBuyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RateBuyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
