import { Component, inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SessionStorageService } from '../../../service/session-storage.service';
import { OAuthModule, OAuthService } from 'angular-oauth2-oidc';
import { authConfig } from '../../../authconfig';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule,OAuthModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  sessionStorageAuth = inject(SessionStorageService);

  authenticated = this.sessionStorageAuth.isAuthenticated;

  constructor(private oauthService:OAuthService)
  {
    this.oauthService.configure(authConfig);
    this.oauthService.loadDiscoveryDocumentAndTryLogin();
  }

  login()
  {
    this.oauthService.initCodeFlow();
  }

}
