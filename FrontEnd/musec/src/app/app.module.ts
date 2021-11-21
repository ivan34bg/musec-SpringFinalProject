import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { CookieService } from 'ngx-cookie-service';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { MusicModule } from './music/music.module';
import { SettingsModule } from './user/settings/settings.module';
import { UserModule } from './user/user.module';
import { HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    CoreModule,
    UserModule,
    MusicModule,
    SettingsModule,
    HttpClientModule,
    HttpClientXsrfModule
  ],
  providers: [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
