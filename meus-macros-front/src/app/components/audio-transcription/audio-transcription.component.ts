import { Component, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-audio-transcription',
  templateUrl: './audio-transcription.component.html',
  styleUrls: ['./audio-transcription.component.css'],
})
export class AudioTranscriptionComponent {
  isRecording = false;
  transcription = '';
  recognition: any;

  constructor(private cdr: ChangeDetectorRef) {
    if ('webkitSpeechRecognition' in window) {
      this.recognition = new (window as any).webkitSpeechRecognition();
    } else if ('SpeechRecognition' in window) {
      this.recognition = new (window as any).SpeechRecognition();
    } else {
      alert('Speech recognition not supported in your browser.');
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
        console.log('Transcription:', this.transcription);
        this.cdr.detectChanges(); // Atualiza a UI
      };

      this.recognition.onerror = (event: any) => {
        console.error('Speech recognition error:', event.error);
      };

      this.recognition.onstart = () => {
        console.log('Speech recognition started.');
      };

      this.recognition.onend = () => {
        console.log('Speech recognition ended.');
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
    if (this.recognition) this.recognition.start();
  }

  stopRecording() {
    this.isRecording = false;
    if (this.recognition) this.recognition.stop();
  }
}
