import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneratedMacrosComponent } from './generated-macros.component';

describe('GeneratedMacrosComponent', () => {
  let component: GeneratedMacrosComponent;
  let fixture: ComponentFixture<GeneratedMacrosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GeneratedMacrosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GeneratedMacrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
