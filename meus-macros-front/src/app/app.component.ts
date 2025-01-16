import { Component } from '@angular/core';
import {AudioTranscriptionComponent} from './components/audio-transcription/audio-transcription.component';
import {GeneratedMacrosComponent} from './components/generated-macros/generated-macros.component';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  imports: [
    AudioTranscriptionComponent,
    GeneratedMacrosComponent,
    RouterOutlet
  ],
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-component';
}
