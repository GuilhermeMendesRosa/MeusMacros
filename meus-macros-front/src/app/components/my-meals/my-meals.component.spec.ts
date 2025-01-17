import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyMealsComponent } from './my-meals.component';

describe('MinhasRefeicoesComponent', () => {
  let component: MyMealsComponent;
  let fixture: ComponentFixture<MyMealsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyMealsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyMealsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
