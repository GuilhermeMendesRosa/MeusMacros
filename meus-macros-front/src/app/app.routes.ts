import {Routes} from '@angular/router';
import {AudioTranscriptionComponent} from './components/audio-transcription/audio-transcription.component';
import {GeneratedMacrosComponent} from './components/generated-macros/generated-macros.component';
import {MyMealsComponent} from './components/my-meals/my-meals.component';

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
    path: 'my-meals',
    component: MyMealsComponent,
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
