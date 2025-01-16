import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MinhasRefeicoesComponent } from './minhas-refeicoes.component';

describe('MinhasRefeicoesComponent', () => {
  let component: MinhasRefeicoesComponent;
  let fixture: ComponentFixture<MinhasRefeicoesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MinhasRefeicoesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MinhasRefeicoesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
