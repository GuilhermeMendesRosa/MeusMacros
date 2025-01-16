import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalcularMacronutrientesComponent } from './calcular-macronutrientes.component';

describe('CalcularMacronutrientesComponent', () => {
  let component: CalcularMacronutrientesComponent;
  let fixture: ComponentFixture<CalcularMacronutrientesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CalcularMacronutrientesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CalcularMacronutrientesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
