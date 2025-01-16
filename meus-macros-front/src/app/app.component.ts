import { Component } from '@angular/core';
import {AudioTranscriptionComponent} from './components/audio-transcription/audio-transcription.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  imports: [
    AudioTranscriptionComponent
  ],
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-component';
}
