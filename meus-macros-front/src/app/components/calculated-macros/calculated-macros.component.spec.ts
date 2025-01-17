import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalculatedMacrosComponent } from './calculated-macros.component';

describe('ResultadosMacronutrientesComponent', () => {
  let component: CalculatedMacrosComponent;
  let fixture: ComponentFixture<CalculatedMacrosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CalculatedMacrosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CalculatedMacrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
