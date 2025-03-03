import { ChangeDetectorRef, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';
import { CalculationService } from '../../services/calculation.service';
import { Transcription } from '../../models/Transcription';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-audio-transcription',
  templateUrl: './audio-transciption.component.html',
  styleUrls: ['./audio-transciption.component.css'],
  imports: [NgIf]
})
export class AudioTransciptionComponent {
  isRecording = false;
  isLoading = false;
  transcription: Transcription = {
    transcriptFood: ""
  };
  recognition: any;

  constructor(
    private calculationsService: CalculationService,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private router: Router
  ) {
    if ('webkitSpeechRecognition' in window) {
      this.recognition = new (window as any).webkitSpeechRecognition();
    } else if ('SpeechRecognition' in window) {
      this.recognition = new (window as any).SpeechRecognition();
    } else {
      alert('Reconhecimento de fala não é suportado neste navegador.');
    }

    if (this.recognition) {
      this.recognition.continuous = true;
      this.recognition.interimResults = true;

      this.recognition.onresult = (event: any) => {
        let transcript = '';
        for (let i = event.resultIndex; i < event.results.length; i++) {
          transcript += event.results[i][0].transcript;
        }
        this.transcription.transcriptFood = transcript.trim();
        this.cdr.detectChanges();
      };

      this.recognition.onerror = (event: any) => {
        console.error('Erro no reconhecimento de fala:', event.error);
      };

      this.recognition.onstart = () => {
        console.log('Reconhecimento de fala iniciado.');
      };

      this.recognition.onend = () => {
        console.log('Reconhecimento de fala finalizado.');
      };
    }
  }

  ngOnInit() {
    this.authService.me().subscribe(me => {
    });
  }

  toggleRecording() {
    if (this.isRecording) {
      this.stopRecording();
    } else {
      this.startRecording();
    }
  }

  startRecording() {
    this.isRecording = true;
    if (this.recognition) {
      this.transcription.transcriptFood = '';  // Limpa qualquer transcrição anterior
      this.recognition.start();
    }
  }

  stopRecording() {
    this.isRecording = false;
    if (this.recognition) {
      this.recognition.stop();
      console.log('Texto completo captado:', this.transcription.transcriptFood);
    }
  }

  navigateToResultados() {
    this.isLoading = true; // Inicia o loading
    this.calculationsService.calculateMeal(this.transcription).subscribe({
      next: meal => {
        this.calculationsService.meal = meal;
        this.router.navigate(['/calculated-macros']);
      },
      error: error => {
        console.error('Erro ao calcular a refeição:', error);
        this.isLoading = false;
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }
}
