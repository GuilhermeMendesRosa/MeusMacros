import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AudioTransciptionComponent } from './audio-transciption.component';

describe('CalcularMacronutrientesComponent', () => {
  let component: AudioTransciptionComponent;
  let fixture: ComponentFixture<AudioTransciptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AudioTransciptionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AudioTransciptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
