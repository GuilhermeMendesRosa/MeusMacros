import {Routes} from '@angular/router';
import {
  AudioTransciptionComponent
} from './components/audio-transcription/audio-transciption.component';
import {
  CalculatedMacrosComponent
} from './components/calculated-macros/calculated-macros.component';
import {MyMealsComponent} from './components/my-meals/my-meals.component';
import {ProfileComponent} from './components/profile/profile.component';
import {LoginComponent} from './components/login/login.component';

export const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'audio-transcription', component: AudioTransciptionComponent},
  {path: 'calculated-macros', component: CalculatedMacrosComponent},
  {path: 'my-meals', component: MyMealsComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'login', component: LoginComponent},];
