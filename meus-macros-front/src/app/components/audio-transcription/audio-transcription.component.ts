import { Component, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-audio-transcription',
  templateUrl: './audio-transcription.component.html',
  styleUrls: ['./audio-transcription.component.css'],
  imports: [
    NgIf
  ]
})
export class AudioTranscriptionComponent {
  isRecording = false;
  transcription = '';
  recognition: any;

  constructor(private cdr: ChangeDetectorRef, private router: Router) {
    // Inicializa o reconhecimento de voz
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
        this.transcription = transcript.trim();
        this.cdr.detectChanges(); // Atualiza a UI
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
      this.transcription = '';  // Limpa qualquer transcrição anterior
      this.recognition.start();
    }
  }

  stopRecording() {
    this.isRecording = false;
    if (this.recognition) {
      this.recognition.stop();
      // Ao parar, o conteúdo total captado até o momento será exibido
      console.log('Texto completo captado:', this.transcription);
    }
  }

  calculateMacros() {
    // Aqui você pode adicionar lógica para enviar a transcrição ao back-end, se necessário.
    console.log('Transcrição enviada:', this.transcription);

    // Redireciona para a tela de exibição dos macronutrientes
    this.router.navigate(['/generate-macros']);
  }
}
