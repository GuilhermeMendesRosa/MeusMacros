import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultadosMacronutrientesComponent } from './resultados-macronutrientes.component';

describe('ResultadosMacronutrientesComponent', () => {
  let component: ResultadosMacronutrientesComponent;
  let fixture: ComponentFixture<ResultadosMacronutrientesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResultadosMacronutrientesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResultadosMacronutrientesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
