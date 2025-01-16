import {Routes} from '@angular/router';
import {AudioTranscriptionComponent} from './components/audio-transcription/audio-transcription.component';
import {GeneratedMacrosComponent} from './components/generated-macros/generated-macros.component';

let GenerateMacrosComponent;
export const routes: Routes = [
  {
    path: 'audio-transcription',
    component: AudioTranscriptionComponent,
  },
  {
    path: 'generated-macros',
    component: GeneratedMacrosComponent,
  },
  {
    path: '',
    redirectTo: 'audio-transcription',
    pathMatch: 'full',
  },
  {
    path: '**',
    redirectTo: 'audio-transcription',
  },
];
